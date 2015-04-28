package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.inspire.clubspire_02.list_items.Day;
import cz.inspire.clubspire_02.list_items.ReservationListItem;


public class ListReservationActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private final String listReservationText = "Seznam rezervací";

    //ListView listView;

    private List<ReservationListItem> reservationList = new ArrayList<ReservationListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);


        setupActionBar();
        setTitle(listReservationText);



        populateReservationList();
        populateListView();
        registerRegistrationClickCallback();

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        });


    }


    private void populateReservationList() {

        reservationList.clear();
        Date day;
        Calendar cal = Calendar.getInstance();
        Time start = new Time();
        Time end = new Time();
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

        reservationList.add(new ReservationListItem(R.drawable.a_01_b, "Plavání", day, Day.PO, start, end));
        reservationList.add(new ReservationListItem(R.drawable.a_01_b, "Plavání", day, Day.PO, start, end));
        reservationList.add(new ReservationListItem(R.drawable.a_01_b, "Plavání", day, Day.PO, start, end));
        reservationList.add(new ReservationListItem(R.drawable.a_01_b, "Plavání", day, Day.PO, start, end));
    }

    private void populateListView() {
        ArrayAdapter<ReservationListItem> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listReservationView);
        list.setAdapter(adapter);
    }

    private void registerRegistrationClickCallback() {
        ListView list = (ListView) findViewById(R.id.listReservationView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    final int position, long id) {

                ReservationListItem clickedReservation = reservationList.get(position);
                //String message = "You clicked position " + position;
                Toast.makeText(ListReservationActivity.this, clickedReservation.getActivityName(), Toast.LENGTH_SHORT).show();
                try {
                    Button btn = (Button) viewClicked.findViewById(R.id.btnDeleteReservation);
                    btn.setVisibility(View.VISIBLE);

                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            reservationList.remove(position);
                            populateListView();
                        }
                    });

                    //jak zmizet button při kliku na jiný item?

                }catch(Exception e){
                    Toast.makeText(ListReservationActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }



    private class MyListAdapter extends ArrayAdapter<ReservationListItem> {
        public MyListAdapter() {
            super(ListReservationActivity.this, R.layout.reservation_item, reservationList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.reservation_item, parent, false);
            }

            // Find the ActivityItem to work with.
            ReservationListItem currentReservation = reservationList.get(position);

            // Date:
            TextView dateText = (TextView) itemView.findViewById(R.id.reservation_item_txtDate);
            dateText.setText(currentReservation.getDateString() + "(" + currentReservation.getStartString() + "-" + currentReservation.getEndString() + ")");

            // Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.reservation_item_icon);
            imageView.setImageResource(currentReservation.getIconId());

            // Name:
            TextView nameText = (TextView) itemView.findViewById(R.id.reservation_item_txtName);
            nameText.setText(currentReservation.getActivityName());

            return itemView;
        }
    }









    private void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

}
