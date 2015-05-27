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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        /*
        ArrayAdapter<SpinnerItem> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,list);
        */
        ArrayAdapter<SpinnerItem> dataAdapter = new ArrayAdapter<>
                (this, R.layout.simple_spinner_item,list);

        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

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
        requestParams.add(new BasicNameValuePair("date", "2015-06-20"));
        //atribut date API ignoruje: robilo problemy aj pri testovani/ stale vracia aktualny tyzden
        requestParams.add(new BasicNameValuePair("history", "false"));
        requestParams.add(new BasicNameValuePair("matchesFilter", "true"));
        requestParams.add(new BasicNameValuePair("lessonStarted", "false"));
        requestParams.add(new BasicNameValuePair("clickable", "true"));
        requestParams.add(new BasicNameValuePair("substitute", "false"));
        requestParams.add(new BasicNameValuePair("activityId", ReservationHolder.getReservationActivityId()));


        https://api.clubspire.com/api/timeline/week?
        // date=2015-06-20&
        // history=false&
        // matchesFilter=true&
        // lessonStarted=false&
        // lessonFinished=false&
        // clickable=true&substitute=false&
        // activityId=639b1d5cac1303f00011cd38b40e36c0
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

            //TODO: sparsovat resultContent a nahadzat nove prvky do termList
            //TODO: asi bude treba najskor spravit getActualWeek
            //"2015-06-15T00:00:00.000+0200
        if(!resultContent.equals("")) {
            try {

                //JSONObject json = (JSONObject) JSONSerializer.toJSON(s);

                JsonObject obj = new JsonParser().parse(resultContent).getAsJsonObject();
                System.out.println("objekt = " + obj.toString());


                //JSONObject baseJSON = new JSONObject();
                //JsonObject baseJSON = new JsonParser().parse(resultContent).getAsJsonObject();
                //Object obj=JSONValue.parse(s);

                JSONObject baseJSON = new JSONObject(resultContent);

                Log.d("base","getting base");
                System.out.println("result content = " + resultContent);
                System.out.println("base = " + baseJSON.toString());

                JSONArray dataJSON = baseJSON.getJSONArray("data");
                Log.d("data", "getting data");
                System.out.println("data = " + dataJSON.toString());

                JSONObject dataObj = dataJSON.getJSONObject(0);
                System.out.println("dataObj[0]:" + dataObj.toString());

                JSONArray dayJSON = dataObj.getJSONArray("days");
                Log.d("day","getting day");

                System.out.println("days length = " + dayJSON.length());

                for(int i = 0;i<dayJSON.length(); i++){





                    JSONObject dayObj = (JSONObject)dayJSON.get(i);
                    System.out.println("dayObj[" + i + "]:" + dayObj.toString());

                    JSONArray termJSON = dayObj.getJSONArray("dayTabs");
                    System.out.println(i + " - terms length = " + termJSON.length());
                    for(int j = 0; j<termJSON.length();j++){
                        TermItem t = new TermItem();

                        //date
                        //TODO
                        //JSONObject jo = dayObj.getJSONObject("date");
                        //System.out.println(i + " : date = " + jo.toString());

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
                        //t.setDate(df.parse(new JSONObject(dayJSON.get(i).toString()).getString("date")));
                        Calendar cal = Calendar.getInstance();
                        Date day = cal.getTime();
                        t.setDate(day);


                        //day
                        switch (i){
                            case 0:
                                t.setDay(Day.PO);
                                break;
                            case 1:
                                t.setDay(Day.ÚT);
                                break;
                            case 2:
                                t.setDay(Day.ST);
                                break;
                            case 3:
                                t.setDay(Day.ČT);
                                break;
                            case 4:
                                t.setDay(Day.PÁ);
                                break;
                            case 5:
                                t.setDay(Day.SO);
                                break;
                            case 6:
                                t.setDay(Day.NE);
                                break;
                            default:
                                t.setDay(Day.PO);
                                break;
                        }

                        //available
                        t.setAvailable(true);

                        //start
                        //System.out.println("--------------");
                        //System.out.println(termJSON.get(j).toString());
                        //System.out.println("--------------");

                        String startString = new JSONObject(termJSON.get(j).toString()).getString("startHour");
                        System.out.println("start hour = " + startString);
                        int startInt = Integer.parseInt(startString);
                        Time startTime = new Time();
                        startTime.set(0,0,startInt,0,0,0);
                        t.setStart(startTime);

                        //end

                        String endString = new JSONObject(termJSON.get(j).toString()).getString("endHour");
                        System.out.println("end hour = " + endString);
                        int endInt = Integer.parseInt(endString);
                        Time endTime = new Time();
                        endTime.set(0,0,endInt,0,0,0);
                        t.setEnd(endTime);

                        termList.add(t);

                        if(i<10){
                            System.out.println(t.getDay());
                            //System.out.println(t.getDateString());
                            System.out.println(t.getStartString());
                            System.out.println(t.getEndString());
                        }
                    }


                    /*



                    */




                }

            } catch (JSONException e) {
                Log.e("Reservation02Activity:", "JSON  fail");
                e.printStackTrace();
            }catch (Exception e) {
                Log.e("Reservation02Activity:", "JSON parsing failed");
                e.printStackTrace();
            }Log.d("Reservation01Activity: ", resultContent);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to load a list of terms", Toast.LENGTH_SHORT).show();
            Log.e("Reservation02Activity", "resultContent was empty");
        }

        //termList.clear();
        /*
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
        */

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
