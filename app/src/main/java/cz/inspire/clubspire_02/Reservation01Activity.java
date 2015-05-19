package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.array_adapter.ActivityListAdapter;


public class Reservation01Activity extends AbstractReservationActivity {

    private Toolbar mToolbar;

    //ListView listView;

    private List<ActivityItem> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_01);

        setupActionBar();

        populateActivityList();
        populateListView();
        registerClickCallback();
    }

    private void populateActivityList() {
        //TODO: get images, cache them

        Intent intent = getIntent();
        String JSONResponse = intent.getStringExtra("Reservation01Activity");
        if(JSONResponse != null) {
            try {
                JSONObject baseJSON = new JSONObject(JSONResponse);
                JSONArray activityJSON = baseJSON.getJSONArray("data");
                for(int i = 0;i<activityJSON.length(); i++){
                    activityList.add(new ActivityItem()
                                    .setIconID(R.drawable.a_01_b)
                                    .setId(new JSONObject(activityJSON.get(i).toString()).getString("id"))
                                    .setName(new JSONObject(activityJSON.get(i).toString()).getString("name"))
                                    .setDescription(new JSONObject(activityJSON.get(i).toString()).getString("description"))
                    );
                }
                //JsonPath query = new JsonPath(JSON);
                //query.read(JSONResponse);

            } catch (JSONException e) {
                Log.e("Reservation01Activity:", "JSON parsing failed");
                e.printStackTrace();
            }
            Log.d("Reservation01Activity: ", JSONResponse);


        } else {
            Toast.makeText(getApplicationContext(), "Failed to load a list of activities", Toast.LENGTH_SHORT).show();
            Log.w("Reservation01Activity", "null");
        }


        /*
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
        */
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
