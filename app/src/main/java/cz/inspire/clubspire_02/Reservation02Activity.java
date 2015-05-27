package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.ReservationHolder;
import cz.inspire.clubspire_02.array_adapter.TermListAdapter;
import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.list_items.Day;
import cz.inspire.clubspire_02.list_items.TermItem;


public class Reservation02Activity extends AbstractBaseActivity {

    private Spinner spinner1;

    private List<TermItem> termList = new ArrayList<>();

    private String activityName;
    private int iconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbarMenuPresent = true;
        parentIntent = new Intent(getApplicationContext(), Reservation01Activity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_02);

        setupActionBar();

        Bundle extras = getIntent().getExtras();

        iconId = ReservationHolder.getIconId();
        activityName = ReservationHolder.getReservationActivityName();

        View selectedActivity = findViewById(R.id.selectedActivity);
        // icon
        ImageView activityIcon = (ImageView)selectedActivity.findViewById(R.id.item_icon);
        activityIcon.setImageResource(iconId);

        // name:
        TextView textViewActivityName = (TextView) selectedActivity.findViewById(R.id.item_txtName);
        textViewActivityName.setText(activityName + "");


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<SpinnerItem> list = populateSpinnerList();

        ArrayAdapter<SpinnerItem> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,list);

        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        spinner1.setAdapter(dataAdapter);
        spinner1.setSelection(1);

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            }
        });

        //GET attribute pairs:
        List<NameValuePair> requestParams = new ArrayList<>();

        //TODO: napojit atribut date na nejaky den z vybraneho tyzdna
        requestParams.add(new BasicNameValuePair("date", "2015-06-04"));
        //atribut date API ignoruje: robilo problemy aj pri testovani/ stale vracia aktualny tyzden
        requestParams.add(new BasicNameValuePair("history", "false"));
        requestParams.add(new BasicNameValuePair("matchesFilter", "true"));
        requestParams.add(new BasicNameValuePair("lessonStarted", "false"));
        requestParams.add(new BasicNameValuePair("clickable", "true"));
        requestParams.add(new BasicNameValuePair("substitute", "false"));
        requestParams.add(new BasicNameValuePair("activityId", ReservationHolder.getReservationActivityId()));

        //API loader initialization
        new LocalAsyncAPIRequestExtension().setParameters(requestParams).execute("/api/timeline/week", HttpMethod.GET);
        //continues in onPostExecute

    }

    protected class LocalAsyncAPIRequestExtension extends AsyncAPIRequest {
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            // Spinner item selection Listener
            addListenerOnSpinnerItemSelection();

            Log.d("onPostExecute", "in LocalAsyncAPIRequestExtension called");
            Log.d("loaded content:", resultContent);

            //TODO Reservation: dostat z jsonobject-u vsetko relevantne pre novovytvorenu rezervaciu:
            //activityId
            //sportId
            //startTime
            //endTime

            if(!resultContent.equals("")) {
                //podobne ako v Reservation01

            }

            populateTermList();
            populateTermListView();
            registerTermClickCallback();

        }
    }



    private void populateTermList() {

        if(!resultContent.equals("")) {
            //TODO: sparsovat resultContent a nahadzat nove prvky do termList
            //TODO: asi bude treba najskor spravit getActualWeek
        } else {
            Toast.makeText(getApplicationContext(), "Failed to load a list of terms", Toast.LENGTH_SHORT).show();
            Log.e("Reservation02Activity", "resultContent was empty");
        }

        termList.clear();
        Date day;
        Calendar cal = Calendar.getInstance();
        Time start = new Time();
        Time end = new Time();

        switch (getWeekFromSelection()){
            case 20:

                cal.set(Calendar.HOUR_OF_DAY,17);
                cal.set(Calendar.MINUTE,30);
                cal.set(Calendar.SECOND,0);
                cal.set(Calendar.MILLISECOND,0);
                cal.set(Calendar.DAY_OF_MONTH,24);
                cal.set(Calendar.MONTH,11);
                cal.set(Calendar.YEAR,2014);
                day = cal.getTime();
                start.set(0,30,15,24,12,2015);
                end.set(0,00,14,24,12,2015);

                termList.add(new TermItem(day, Day.PO, start, end, 20, true));
                termList.add(new TermItem(day, Day.ÚT, start, end, 20, false));
                termList.add(new TermItem(day, Day.ST, start, end, 20, true));
                break;

            default:
                cal.set(Calendar.HOUR_OF_DAY,7);
                cal.set(Calendar.MINUTE,301);
                cal.set(Calendar.SECOND,05);
                cal.set(Calendar.MILLISECOND,50);
                cal.set(Calendar.DAY_OF_MONTH,254);
                cal.set(Calendar.MONTH,1);
                cal.set(Calendar.YEAR,2015);
                day = cal.getTime();
                start.set(0,30,13,24,12,2015);
                end.set(0,30,14,24,12,2015);

                termList.add(new TermItem(day, Day.ST, start, end, 15, false));
                termList.add(new TermItem(day, Day.ČT, start, end, 23, true));
                termList.add(new TermItem(day, Day.PÁ, start, end, 10, true));
                break;


        }

    }

    private void populateTermListView() {
        //ArrayAdapter<TermItem> termAdapter = new MyTermListAdapter();
        TermListAdapter termAdapter = new TermListAdapter(this,termList);
        ListView termListView = (ListView) findViewById(R.id.termListView);
        termListView.setAdapter(termAdapter);
    }

    private void registerTermClickCallback() {
        ListView list = (ListView) findViewById(R.id.termListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                TermItem clickedTerm = termList.get(position);
                //String message = "You clicked position " + position;
                //Toast.makeText(Reservation02Activity.this, message, Toast.LENGTH_SHORT).show();

                if (clickedTerm.isAvailable()) {
                    Intent intent = new Intent(getApplicationContext(), Reservation03Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXTRA_ICON_ID", iconId);
                    intent.putExtra("EXTRA_ACTIVITY_NAME", activityName);
                    intent.putExtra("EXTRA_DATE", clickedTerm.getDateString());
                    intent.putExtra("EXTRA_START", clickedTerm.getStartString());
                    intent.putExtra("EXTRA_END", clickedTerm.getEndString());

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "termín obsazen", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private List<SpinnerItem> populateSpinnerList(){
        List<SpinnerItem> out = new ArrayList<>();

        //TODO: use getActualWeek

        Date from1 = new Date(2015,5,4);
        Date from2 = new Date(2015,5,11);
        Date from3 = new Date(2015,5,18);
        Date from4 = new Date(2015,5,26);
        Date from5 = new Date(2015,6,3);

        Date to1 = new Date(2015,5,11);
        Date to2 = new Date(2015,5,18);
        Date to3 = new Date(2015,5,25);
        Date to4 = new Date(2015,5,3);
        Date to5 = new Date(2015,6,10);

        out.add(new SpinnerItem(20,from1,to1));
        out.add(new SpinnerItem(21,from2,to2));
        out.add(new SpinnerItem(22,from3,to3));
        out.add(new SpinnerItem(23,from4,to4));
        out.add(new SpinnerItem(25,from5,to5));
        out.add(new SpinnerItem(26));
        out.add(new SpinnerItem(27));
        out.add(new SpinnerItem(28));
        out.add(new SpinnerItem(29));
        out.add(new SpinnerItem(29));
        out.add(new SpinnerItem(29));

        return out;
    }

    // Add spinner data

    public void addListenerOnSpinnerItemSelection(){

        spinner1.setOnItemSelectedListener(new SpinnerListener2());
        //TODO setOnItemSelectedListener calls updateTerm()
    }

    private int getActualWeek(){
        //TODO not implemented yet
        return 23;
    }

    private int getWeekFromSelection(){
        return ((SpinnerItem)(spinner1.getSelectedItem())).getWeekNum();
    }

    public void updateTerm(){
        //fill term list
        populateTermList();

        populateTermListView();

        registerTermClickCallback();

    }

    private class SpinnerListener2 implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            //Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
            updateTerm();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            adapterView.setSelection(1);
        }


    }
}
