package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.ReservationHolder;
import cz.inspire.clubspire_02.POJO.Reservation;
import cz.inspire.clubspire_02.array_adapter.TermListAdapter;
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
        Picasso.with(this)
                .load(ReservationHolder.getIconUrl())
                .placeholder(R.drawable.loader_icon)
                .error(R.drawable.a_01_b)
                .resize((int)getResources().getDimension(R.dimen.icon_width),(int)getResources().getDimension(R.dimen.icon_height))
                .into(activityIcon);

        // name:
        TextView textViewActivityName = (TextView) selectedActivity.findViewById(R.id.item_txtName);
        textViewActivityName.setText(activityName + "");


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<SpinnerItem> list = populateSpinnerList(8);


        ArrayAdapter<SpinnerItem> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,list);

        /*
        ArrayAdapter<SpinnerItem> dataAdapter = new ArrayAdapter<>
                (this, R.layout.simple_spinner_item,list);
        */

        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);
        spinner1.setSelection(0);

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            }
        });

        //GET attribute pairs:
        List<NameValuePair> requestParams = new ArrayList<>();

        requestParams.add(new BasicNameValuePair("date", "2015-06-20"));
        requestParams.add(new BasicNameValuePair("activityId", ReservationHolder.getReservationActivityId()));


        //API loader initialization
        new LocalAsyncAPIRequestExtension().setParameters(requestParams).execute("/timeline/week", HttpMethod.GET);
        //continues in onPostExecute

    }

    protected class LocalAsyncAPIRequestExtension extends AsyncAPIRequest {

        private ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar2);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //make loader visible:
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            // Spinner item selection Listener
            addListenerOnSpinnerItemSelection();

            updateTerm();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }



    private void populateTermList() {


        if(!resultContent.equals("")) {
            JSONArray dataJSON = new JSONArray();
            try {
                JSONObject baseJSON = new JSONObject(resultContent);
                System.out.println("baseJSON: " + baseJSON);
                Log.d("base","getting base");
                dataJSON = baseJSON.getJSONArray("data");
                System.out.println("dataJSON: " + dataJSON);

            } catch (JSONException e) {
                Log.e("base:", "baseJSON  fail:" + e);
                //e.printStackTrace();
            }

            for(int d = 0; d<dataJSON.length();d++){
                JSONObject dataObj = new JSONObject();

                try {
                    dataObj = dataJSON.getJSONObject(d);
                    //System.out.println("dataObj = " + dataObj);
                }catch (JSONException e) {
                    Log.e("dataObj:", "dataObj  fail:" + e);
                    //e.printStackTrace();
                }
                JSONArray dayJSON = new JSONArray();
                try {
                    dayJSON = dataObj.getJSONArray("days");
                    //System.out.println("dayJSON = " + dayJSON);
                }catch (JSONException e) {
                    //Log.e("dayJSON:", "dayJSON  fail:" + e);
                    //e.printStackTrace();
                }


                for(int i = 0;i<dayJSON.length(); i++) {

                    JSONObject dayObj = new JSONObject();


                    try {
                        dayObj = (JSONObject) dayJSON.get(i);
                        //System.out.println("dayObj = " + dayObj);
                    }catch (JSONException e) {
                        //Log.e("dayObj:", "dayObj  fail:" + e);
                        ////e.printStackTrace();
                    }

                    Boolean daysHistory = true;
                    try {
                        daysHistory = dayObj.getBoolean("history");
                        //System.out.println("daysHistory = " + daysHistory);
                    }catch (JSONException e) {
                        //Log.e("daysHistory:", "daysHistory  fail:" + e);
                        ////e.printStackTrace();
                    }

                    //if day has history => skip it
                    if(!daysHistory){
                        JSONArray dayTabsJSON = new JSONArray();
                        try {
                            dayTabsJSON = dayObj.getJSONArray("dayTabs");
                            //System.out.println("dayTabsJSON = " + dayTabsJSON);
                        }catch (JSONException e) {
                            //Log.e("dayTabsJSON:", "dayTabsJSON  fail:" + e);
                            //e.printStackTrace();
                        }

                        for (int j = 0; j < dayTabsJSON.length(); j++) {
                            JSONObject dayTabJSON = new JSONObject();
                            try {
                                dayTabJSON = (JSONObject) dayTabsJSON.get(j);
                                //System.out.println("day tab " + j + ": " + dayTabJSON);
                            } catch (JSONException e) {
                                //System.out.println("daytab fail");
                            }
                            Boolean dayTabHistory = true;
                            try {
                                dayTabHistory = dayTabJSON.getBoolean("history");
                                //System.out.println("dayTabHistory = " + dayTabHistory);
                            }catch (JSONException e) {
                                //Log.e("daysHistory:", "daysHistory  fail:" + e);
                                ////e.printStackTrace();
                            }
                            if(!dayTabHistory) {

                                //date - will be set for each sport separately
                                String termDate = "";
                                try {
                                    termDate = dayObj.getString("date");
                                    //System.out.println("termDate = " + termDate);
                                } catch (JSONException e) {
                                    //Log.e("termDate:", "termDate  fail:" + e);
                                    //e.printStackTrace();
                                }
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

                                JSONArray sportsJSON = new JSONArray();
                                try {
                                    sportsJSON = ((JSONObject) dayTabsJSON.get(j)).getJSONArray("sports");
                                    //System.out.println("sportsJSON: " + sportsJSON);
                                } catch (JSONException e) {
                                    //Log.e("sportsJSON:", "sportsJSON  fail:" + e);
                                    //e.printStackTrace();
                                }

                                for (int s = 0; s < sportsJSON.length(); s++) {
                                    JSONObject sportJSON = new JSONObject();
                                    try {
                                        sportJSON = (JSONObject) sportsJSON.get(s);
                                        //System.out.println("sport " + s + ": " + sportJSON);
                                    } catch (JSONException e) {

                                    }
                                    Boolean emptySpace = true;
                                    Boolean matchesFilter = false;
                                    Boolean lessonStarted = true;
                                    Boolean lessonFinished = true;
                                    Boolean clickable = false;
                                    Boolean substitute = true;
                                    try {
                                        emptySpace = sportJSON.getBoolean("emptySpace");
                                        //System.out.println("emptySpace = " + emptySpace);
                                        matchesFilter = sportJSON.getBoolean("matchesFilter");
                                        //System.out.println("matchesFilter = " + matchesFilter);
                                        lessonStarted = sportJSON.getBoolean("lessonStarted");
                                        //System.out.println("lessonStarted = " + lessonStarted);
                                        lessonFinished = sportJSON.getBoolean("lessonFinished");
                                        //System.out.println("lessonFinished = " + lessonFinished);
                                        clickable = sportJSON.getBoolean("clickable");
                                        //System.out.println("clickable = " + clickable);
                                        substitute = sportJSON.getBoolean("substitute");
                                        //System.out.println("substitute = " + substitute);
                                    }catch (JSONException e) {
                                        System.out.println("some parameters are not valid");
                                        //Log.e("daysHistory:", "daysHistory  fail:" + e);
                                        ////e.printStackTrace();
                                    }
                                    //System.out.println("***");
                                    //System.out.println(emptySpace + ","+matchesFilter+ ","+lessonStarted+ ","+lessonFinished+ ","+clickable+ ","+substitute );
                                    if(!emptySpace && matchesFilter && !lessonStarted && !lessonFinished && clickable && !substitute)
                                        Log.d("termParams","term not added, some parameters are not valid");
                                    //System.out.println("***");
                                    if(!emptySpace && matchesFilter && !lessonStarted && !lessonFinished && clickable && !substitute) {
                                        //System.out.println("obj " + s + " params ok");
                                        TermItem t = new TermItem();
                                        Boolean termOk = true; //to check if some attribute is not null

                                        //date
                                        try {
                                            t.setDate(dateFormat.parse(termDate));
                                        } catch (ParseException e) {
                                            //Log.e("setDate:", "setDate parse fail:" + e);
                                            //e.printStackTrace();
                                            termOk = false;
                                        }


                                        //available
                                        //TODO no unavailable terms to check
                                        int freePlaces = 0;
                                        try {
                                            freePlaces = ((JSONObject) sportJSON).getInt("freePlaces");
                                        } catch (JSONException e) {
                                            //Log.e("freePlaces:", "freePlaces  fail:" + e);
                                            //e.printStackTrace();
                                        }
                                        if (freePlaces > 0) {
                                            t.setAvailable(true);
                                        } else {
                                            t.setAvailable(false);
                                        }





                                        String objectId = "";
                                        try {
                                            objectId = (sportJSON).getString("objectId");
                                            System.out.println("objectId: " + objectId);
                                        } catch (JSONException e) {
                                            //Log.e("objectId:", "objectId  fail:" + e);
                                            //e.printStackTrace();
                                        };

                                        ReservationHolder.getReservation().setObjectId(objectId);


                                        ////////////////////////////////////////************************************************////////////////////////////////////////

                                        /*
                                        System.out.println("getting smthing");

                                        //API loader initialization
                                        new LocalAsyncAPIRequestExtension().setParameters(urlRequestParams).execute("/api/1.0/timeline/week", HttpMethod.GET);
                                        */

                                        //TODO how to set GET request
                                        //TODO how to get result emmediately - on 518 :  Log.d("Reservation02Activity: ", resultContent); resultContent is ok
                                        /*
                                        int indexOfAPI10 = newReservationUrl.indexOf("/api/1.0");
                                        String urlRequest = newReservationUrl.substring(indexOfAPI10, newReservationUrl.length());
                                        System.out.println("urlRequest: " + urlRequest);


                                        AsyncAPIRequest ar = new AsyncAPIRequest();
                                        ar.execute(urlRequest, HttpMethod.GET);
                                        System.out.println("content1: " + resultContent);

                                        */





                                        ///////////////////////////
                                        String activityId = "";
                                        try {
                                            activityId = ((JSONObject) sportsJSON.get(s)).getString("activityId");
                                        } catch (JSONException e) {
                                            //Log.e("activityId:", "activityId  fail:" + e);
                                            //e.printStackTrace();
                                        }
                                        //TODO activityId not in holder

                                        //sportiId
                                        String sportId = "";
                                        try {
                                            sportId = ((JSONObject) sportsJSON.get(s)).getString("sportId");
                                            t.setSportId(sportId);
                                        } catch (JSONException e) {
                                            //Log.e("activityId:", "activityId  fail:" + e);
                                            //e.printStackTrace();
                                        }

                                        System.out.println("sportJSON: " + sportJSON);

                                        //instructorId
                                        JSONObject instructorJSON = new JSONObject();
                                        try {
                                            instructorJSON = sportJSON.getJSONObject("instructor");
                                            String instructorId = instructorJSON.getString("id");
                                            ReservationHolder.getReservation().setInstructorId(instructorId);
                                        } catch (JSONException e) {
                                            //Log.e("instructorJSON:", "instructorJSON  fail:" + e);
                                            //e.printStackTrace();
                                        }
                                        //TODO např u Mix Fit je instructorId = -1 a request to nebere


                                        String startTime = "";
                                        try {
                                            startTime = ((JSONObject) sportsJSON.get(s)).getString("startTime");
                                        } catch (JSONException e) {
                                            //Log.e("startTime:", "startTime  fail:" + e);
                                            //e.printStackTrace();
                                        }

                                        Date startTimeDF = new Date();
                                        try {
                                            if (startTime != null) {
                                                startTimeDF = dateFormat.parse(startTime);
                                            } else {
                                                startTimeDF = null;
                                            }
                                        } catch (ParseException e) {
                                            //Log.e("startTimeDF:", "startTimeDF  fail:" + e);
                                            //e.printStackTrace();
                                        }

                                        Time startHour = new Time();
                                        startHour.set(0, startTimeDF.getMinutes(), startTimeDF.getHours(), 0, 0, 0);
                                        t.setStart(startHour);

                                        t.setStartTime(startTimeDF);


                                        //endTime
                                        String endTime = "";
                                        try {
                                            endTime = ((JSONObject) sportsJSON.get(s)).getString("endTime");
                                        } catch (JSONException e) {
                                            //Log.e("endTime:", "endTime  fail:" + e);
                                            //e.printStackTrace();
                                        }
                                        Date endTimeDF = new Date();
                                        try {
                                            if (endTime != null) {
                                                endTimeDF = dateFormat.parse(endTime);
                                            } else {
                                                endTimeDF = null;
                                            }
                                        } catch (ParseException e) {
                                            //Log.e("endTimeDF:", "endTimeDF  fail:" + e);
                                            //e.printStackTrace();
                                        }

                                        Time endHour = new Time();
                                        endHour.set(0, endTimeDF.getMinutes(), endTimeDF.getHours(), 0, 0, 0);
                                        t.setEnd(endHour);

                                        t.setEndTime(endTimeDF);

                                        //setDay for TermItem
                                        switch (i) {
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

                                        if (termOk) {
                                            termList.add(t);
                                        } else {
                                            Log.d("null attribute", "term not added");
                                        }



                                    }
                                }
                            }
                        }
                    }
                }
            }


            Log.d("Reservation02Activity: ", resultContent);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to load a list of terms", Toast.LENGTH_SHORT).show();
            Log.e("Reservation02Activity", "resultContent was empty");
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

                    ReservationHolder.getReservation().setSportId(clickedTerm.getSportId());
                    ReservationHolder.getReservation().setStartTime(clickedTerm.getStartTime());
                    ReservationHolder.getReservation().setEndTime(clickedTerm.getEndTime());


                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "termín obsazen", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void addListenerOnSpinnerItemSelection(){

        spinner1.setOnItemSelectedListener(new SpinnerListener2());
        // setOnItemSelectedListener calls updateTerm()
    }

    private List<SpinnerItem> populateSpinnerList(int noOfWeeks){
        LocalDate today = new LocalDate();
        LocalDate weekStart;
        LocalDate weekEnd;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        List<SpinnerItem> out = new ArrayList<>();
        for(int i=0;i<noOfWeeks;i++){
            LocalDate todayPlusSeven = today.plusDays(i*7);
            weekStart = todayPlusSeven.dayOfWeek().withMinimumValue();
            weekEnd = todayPlusSeven.dayOfWeek().withMaximumValue();

            out.add(new SpinnerItem(weekStart.getWeekOfWeekyear(), weekStart, weekEnd));
        }
        return out;
    }

    public void updateTerm(){
        //fill term list
        termList.clear();

        populateTermList();

        populateTermListView();

        registerTermClickCallback();

    }

    private class SpinnerListener2 implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            List<NameValuePair> requestParams = new ArrayList<>();
            int newWeekNum = ((SpinnerItem)(spinner1.getSelectedItem())).getWeekNum();
            //System.out.println("new week = " + newWeekNum);

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.set(Calendar.WEEK_OF_YEAR, newWeekNum);
            calendar.set(Calendar.YEAR, 2015);

            // Now get the first day of week.
            Date date = calendar.getTime();
            //we dont want months to be counted from 0
            date.setMonth(date.getMonth() + 1);
            //System.out.println("new date = " + date.toString());
            //System.out.println("new date == " + date.getDate());
            //System.out.println("new month == " + date.getMonth());
            //newDateString has to be in speciffic format
            String monthString;
            if((""+date.getMonth()).length() == 1){
                monthString = "0" + date.getMonth();
            }else{
                monthString = "" + date.getMonth();
            }
            String dateString;
            if((""+date.getDate()).length() == 1){
                dateString = "0" + date.getDate();
            }else{
                dateString = "" + date.getDate();
            }

            String newDateString = "2015" + "-" + monthString + "-" + dateString;
            //System.out.println("new date is " + newDateString);

            //TODO: napojit atribut date na nejaky den z vybraneho tyzdna
            requestParams.clear();
            Log.d("clearParams","request params cleared");
            requestParams.add(new BasicNameValuePair("date", newDateString));
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
            new LocalAsyncAPIRequestExtension().setParameters(requestParams).execute("/api/1.0/timeline/week", HttpMethod.GET);
            //continues in onPostExecute

            //Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            adapterView.setSelection(0);
        }


    }
}