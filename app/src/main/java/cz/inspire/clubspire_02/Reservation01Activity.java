package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.array_adapter.ActivityListAdapter;


public class Reservation01Activity extends AbstractReservationActivity {

    private Toolbar mToolbar;
    private final String reservationText = "Rezervace";

    //ListView listView;

    private List<ActivityItem> activityList = new ArrayList<>();

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
        activityList.add(new ActivityItem(R.drawable.a_01_b, "Běhání"));
        activityList.add(new ActivityItem(R.drawable.a_02_b, "Posilovna"));
        activityList.add(new ActivityItem(R.drawable.a_01_b, "Tenis"));
        activityList.add(new ActivityItem(R.drawable.a_02_b, "Volejbal"));
        activityList.add(new ActivityItem(R.drawable.a_01_b, "Běhání"));
        activityList.add(new ActivityItem(R.drawable.a_02_b, "Posilovna"));
        activityList.add(new ActivityItem(R.drawable.a_01_b, "Tenis"));
        activityList.add(new ActivityItem(R.drawable.a_02_b, "Volejbal"));
    }

    private void populateListView() {
        //ArrayAdapter<ActivityItem> adapter = new MyListAdapter();
        ActivityListAdapter adapter = new ActivityListAdapter(this,activityList);
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

}
