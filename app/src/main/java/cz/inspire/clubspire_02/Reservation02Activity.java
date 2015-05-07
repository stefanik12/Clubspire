package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

import cz.inspire.clubspire_02.array_adapter.TermListAdapter;
import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.list_items.Day;
import cz.inspire.clubspire_02.list_items.TermItem;


public class Reservation02Activity extends AbstractReservationActivity {
    //TODO clear out commented code

    private Toolbar mToolbar;

    //spinner components
    private Spinner spinner1;

    //ListView listView;

    private List<ActivityItem> activityList;
    private View selectedActivity;


    private List<TermItem> termList = new ArrayList<>();
    //private TextView weekNumber;
    private String activityName;
    private int iconId;

    //private TextView textWeekNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_02);

        setupActionBar();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            iconId = extras.getInt("EXTRA_ICON_ID");
            activityName = extras.getString("EXTRA_ACTIVITY_NAME");

            View selectedActivity = findViewById(R.id.selectedActivity);
            // icon
            ImageView activityIcon = (ImageView)selectedActivity.findViewById(R.id.item_icon);
            activityIcon.setImageResource(iconId);

            // name:
            TextView textViewActivityName = (TextView) selectedActivity.findViewById(R.id.item_txtName);
            textViewActivityName .setText(activityName+"");

        }

        //week number
        //weekNumber = (TextView) findViewById(R.id.spinner1);
        //Calendar cal = Calendar.getInstance();
       // int weekNum = cal.get(Calendar.WEEK_OF_YEAR);

        //weekNumber.setText("Týden " + weekNum );

        //Spinner settings:
        //
        //
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<SpinnerItem> list = populateSpinnerList();

        ArrayAdapter<SpinnerItem> dataAdapter = new ArrayAdapter<SpinnerItem>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);
        spinner1.setSelection(1);

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        //fill term list
        populateTermList();
        populateTermListView();
        registerTermClickCallback();


        //set week SWIPE
        //textWeekNum = (TextView) findViewById(R.id.textWeekNumber);

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateTermList() {
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

        //TODO: make use of getActualWeek

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
        //TODO cotinue here: nothing selected on Create !
        return ((SpinnerItem)(spinner1.getSelectedItem())).getWeekNum();
    }

    public void updateTerm(){
        //TODO not sure here if we ll do it like this

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
