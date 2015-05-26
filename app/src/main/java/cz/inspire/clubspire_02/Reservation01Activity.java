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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.array_adapter.ActivityListAdapter;


public class Reservation01Activity extends AbstractBaseActivity {

    private Toolbar mToolbar;

    //ListView listView;

    private List<ActivityItem> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parentIntent = new Intent(getApplicationContext(), MainMenuActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_01);

        setupActionBar();

        //download and list content from API:
        new LocalAsyncAPIRequestExtension().execute("/api/activities", HttpMethod.GET);

        //continues on data loading complete (with onPostExecute)
    }

    protected class LocalAsyncAPIRequestExtension extends AsyncAPIRequest {
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            Log.d("onPostExecute", "in LocalAsyncAPIRequestExtension called");

            populateActivityList();
            populateListView();
            registerClickCallback();
        }
    }

    private void populateActivityList() {
        //TODO: get images, cache them

        String JSONResponse = resultContent;
        if(!JSONResponse.equals("")) {
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

            } catch (JSONException e) {
                Log.e("Reservation01Activity:", "JSON parsing failed");
                e.printStackTrace();
            }
            Log.d("Reservation01Activity: ", JSONResponse);


        } else {
            Toast.makeText(getApplicationContext(), "Failed to load a list of activities", Toast.LENGTH_SHORT).show();
            Log.e("Reservation01Activity", "was empty");
        }
    }

    private void populateListView() {
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
