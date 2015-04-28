package cz.inspire.clubspire_02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.list_items.ReservationItem;

/**
 * Created by Michal on 27. 4. 2015.
 */
public class ViewReservationActivity  extends ActionBarActivity {

    private Toolbar mToolbar;
    private final String reservationText = "Rezervace";
    private final String USER = "Vukmir";

    private List<ReservationItem> reservationList = new ArrayList<>();
    private String activityName;
    private int iconId;
    private String date;
    private String start;
    private String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_03);

        setupActionBar();
        setTitle(reservationText);




        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Toast.makeText(getApplicationContext(), "no extra", Toast.LENGTH_SHORT).show();
        }

        else {
            activityName = extras.getString("EXTRA_ACTIVITY_NAME");
            date = extras.getString("EXTRA_DATE");
            start = extras.getString("EXTRA_START");
            end = extras.getString("EXTRA_END");
            iconId = extras.getInt("EXTRA_ICON_ID");

            //fill reservation list
            populateReservationList();
            populateReservationListView();
            registerReservationClickCallback();
        }

        //set CONFIRM button listener
        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Rezervace vytvořena", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Reservation01Activity.class);
                startActivity(intent);
            }
        });
        //set CONFIRM button size
        int scrWidth  = getWindowManager().getDefaultDisplay().getWidth();
        int scrHeight = getWindowManager().getDefaultDisplay().getHeight();
        //btnConfirm.setWidth(scrWidth/2);
        btnConfirm.setHeight(scrHeight/10);

        //set CANCEL button listener
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation02Activity.class);
                intent.putExtra("EXTRA_ICON_ID", iconId);
                intent.putExtra("EXTRA_ACTIVITY_NAME", activityName);
                startActivity(intent);
            }
        });
        //set CANCEL button size
        btnCancel.setHeight(scrHeight/10);

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
        reservationList.add(new ReservationItem("Aktivita", activityName));
        reservationList.add(new ReservationItem("Datum", date));
        reservationList.add(new ReservationItem("Čas", start + " - " + end));
        reservationList.add(new ReservationItem("Uživatel", USER));
        //Toast.makeText(getApplicationContext(), activityName + date + start + end, Toast.LENGTH_LONG).show();

    }

    private void populateReservationListView() {
        ArrayAdapter<ReservationItem> reservationAdapter = new MyReservationListAdapter();
        ListView reservationListView = (ListView) findViewById(R.id.reservationListView);
        reservationListView.setAdapter(reservationAdapter);
    }

    private void registerReservationClickCallback() {
        ListView list = (ListView) findViewById(R.id.reservationListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                //TermItem clickedTerm = termList.get(position);
                //String message = "You clicked position " + position;
                //Toast.makeText(Reservation02Activity.this, message, Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            }
        });
    }

    private class MyReservationListAdapter extends ArrayAdapter<ReservationItem> {
        public MyReservationListAdapter() {
            super(ViewReservationActivity.this, R.layout.reservation_item_uneditable, reservationList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.reservation_item_uneditable, parent, false);
            }

            // Find the ReservationItem to work with.
            ReservationItem currentReservationItem = reservationList.get(position);

            TextView categoryText = (TextView) itemView.findViewById(R.id.item_category);
            try {
                categoryText.setText(currentReservationItem.getCategory());
            }catch (NullPointerException e){
                //Toast.makeText(getApplicationContext(),currentReservationItem.getCategory(),Toast.LENGTH_SHORT).show();
            }

            TextView contentText = (TextView) itemView.findViewById(R.id.item_content);
            try {
                contentText.setText(currentReservationItem.getContent());
            }catch (NullPointerException e){
                //Toast.makeText(getApplicationContext(),currentReservationItem.getContent(),Toast.LENGTH_SHORT).show();
            }



            return itemView;
        }
    }




    private void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

}
