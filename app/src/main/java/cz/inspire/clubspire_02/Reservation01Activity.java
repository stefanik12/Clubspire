package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.list_items.ActivityItem;


public class Reservation01Activity extends ActionBarActivity {

    private Toolbar mToolbar;
    private final String reservationText = "Rezervace";

    //ListView listView;

    private List<ActivityItem> activityList = new ArrayList<ActivityItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_01);

        setupActionBar();
        setTitle(reservationText);


        populateActivityList();
        populateListView();
        registerClickCallback();

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        });

    }

    private void populateActivityList() {
        activityList.add(new ActivityItem(R.drawable.a_01_b, "Běhání"));
        activityList.add(new ActivityItem(R.drawable.a_02_b, "Posilovna"));
        activityList.add(new ActivityItem(R.drawable.a_01_b, "Tenis"));
        activityList.add(new ActivityItem(R.drawable.a_02_b, "Volejbal"));
    }

    private void populateListView() {
        ArrayAdapter<ActivityItem> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.activityListView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.activityListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                ActivityItem clickedActivity = activityList.get(position);
                //String message = "You clicked position " + position;
                //Toast.makeText(Reservation01Activity.this, message, Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), Reservation02Activity.class));

                //Intent intent = new Intent(this, Reservation02Activity.class);

                Intent intent = new Intent(getApplicationContext(), Reservation02Activity.class);
                intent.putExtra("EXTRA_ICON_ID", clickedActivity.getIconID());
                intent.putExtra("EXTRA_ACTIVITY_NAME", clickedActivity.getName());
                startActivity(intent);

                //startActivity(new Intent(getApplicationContext(), Reservation02Activity.class));
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<ActivityItem> {
        public MyListAdapter() {
            super(Reservation01Activity.this, R.layout.activity_item, activityList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_item, parent, false);
            }

            // Find the ActivityItem to work with.
            ActivityItem currentActivity = activityList.get(position);

            // Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentActivity.getIconID());

            // Condition:
            TextView condionText = (TextView) itemView.findViewById(R.id.item_txtName);
            condionText.setText(currentActivity.getName());

            return itemView;
        }
    }









    private void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

}
