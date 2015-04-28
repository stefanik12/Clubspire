package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.text.Layout;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.list_items.Day;
import cz.inspire.clubspire_02.list_items.TermItem;


public class Reservation02Activity extends ActionBarActivity {

    private Toolbar mToolbar;
    private final String reservationText = "Rezervace";
    private int weekNum = 22;

    //ListView listView;

    private List<ActivityItem> activityList;
    private View selectedActivity;


    private List<TermItem> termList = new ArrayList<TermItem>();
    private TextView weekNumber;
    private String activityName;
    private int iconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_02);

        setupActionBar();
        setTitle(reservationText);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            //SET DEFAULT FOR NOW
            iconId = R.drawable.a_01_b;
            activityName = "DEFAULT";

            View selectedActivity = findViewById(R.id.selectedActivity);
            // icon
            ImageView activityIcon = (ImageView)selectedActivity.findViewById(R.id.item_icon);
            activityIcon.setImageResource(iconId);

            // name:
            TextView textViewActivityName = (TextView) selectedActivity.findViewById(R.id.item_txtName);
            textViewActivityName .setText(activityName+"");
        }

        else {
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
        weekNumber = (TextView) findViewById(R.id.textWeekNumber);
        //Calendar cal = Calendar.getInstance();
       // int weekNum = cal.get(Calendar.WEEK_OF_YEAR);

        weekNumber.setText("Týden " + weekNum );

        //fill term list
        populateTermList();
        populateTermListView();
        registerTermClickCallback();

        //set NEXT button listener
        Button buttonNext = (Button) findViewById(R.id.btnNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                weekNum++;
                weekNumber.setText("Týden " + weekNum);
                populateTermList();
                populateTermListView();
            }
        });

        //set PREV button listener
        Button buttonPrev = (Button) findViewById(R.id.btnPrev);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                weekNum--;
                weekNumber.setText("Týden " + weekNum);
                populateTermList();
                populateTermListView();
            }
        });

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        });


    }




    private void populateTermList() {
        termList.clear();
        Date day;
        Calendar cal = Calendar.getInstance();
        Time start = new Time();
        Time end = new Time();
        switch (weekNum){
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
        ArrayAdapter<TermItem> termAdapter = new MyTermListAdapter();
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

                Intent intent = new Intent(getApplicationContext(), Reservation03Activity.class);
                intent.putExtra("EXTRA_ICON_ID", iconId);
                intent.putExtra("EXTRA_ACTIVITY_NAME", activityName);
                intent.putExtra("EXTRA_DATE", clickedTerm.getDateString());
                intent.putExtra("EXTRA_START", clickedTerm.getStartString());
                intent.putExtra("EXTRA_END", clickedTerm.getEndString());

                startActivity(intent);

            }
        });
    }

    private class MyTermListAdapter extends ArrayAdapter<TermItem> {
        public MyTermListAdapter() {
            super(Reservation02Activity.this, R.layout.term_item, termList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.term_item, parent, false);
            }

            // Find the TermItem to work with.
            TermItem currentTerm = termList.get(position);

            //!!!!!!!!!!!!!!nefachá
            RelativeLayout termItemLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            //Button termItemBackground = (Button) findViewById(R.id.item_background);
            TextView termAvailable = (TextView) itemView.findViewById(R.id.item_txtAvailable);

            // Day:
            TextView dayText = (TextView) itemView.findViewById(R.id.item_txtDay);
            dayText.setText(currentTerm.getDay().toString() + " " + currentTerm.getDateString());
            if(currentTerm.isAvailable()) {
                //dayText.setBackgroundColor(getResources().getColor(R.color.available_color));
                termItemLayout.setBackgroundColor(getResources().getColor(R.color.available_color));
                termAvailable.setText("volno");

            }
            else {
                termItemLayout.setBackgroundColor(getResources().getColor(R.color.unavailable_color));
                termAvailable.setText("obsazeno");

            }

            // Time:
            TextView timeText = (TextView) itemView.findViewById(R.id.item_txtTime);
            timeText.setText(currentTerm.getStartString() + " - " + currentTerm.getEndString());

            return itemView;
        }
    }













    private void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

}
