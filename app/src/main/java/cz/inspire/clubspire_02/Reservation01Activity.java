package cz.inspire.clubspire_02;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.ReservationHolder;
import cz.inspire.clubspire_02.POJO.Reservation;
import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.array_adapter.ActivityListAdapter;


public class Reservation01Activity extends AbstractBaseActivity {

    private Toolbar mToolbar;

    //ListView listView;

    private List<ActivityItem> activityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbarMenuPresent = true;
        parentIntent = new Intent(getApplicationContext(), MainMenuActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_01);

        setupActionBar();

        //download and list content from API:
        new LocalAsyncAPIRequestExtension().execute("/activities", HttpMethod.GET);

        //continues on data loading complete (with onPostExecute)
    }

    protected class LocalAsyncAPIRequestExtension extends AsyncAPIRequest {

        private ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar1);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //make loader visible:
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            Log.d("onPostExecute", "in LocalAsyncAPIRequestExtension called");

            populateActivityList();
            populateListView();
            registerClickCallback();

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void populateActivityList() {

        if(resultContent != null) {
            try {
                JSONObject baseJSON = new JSONObject(resultContent);
                JSONArray activityJSON = baseJSON.getJSONArray("data");
                for(int i = 0;i<activityJSON.length(); i++){
                    System.out.println("activityJSON" + i + ": " + activityJSON.get(i));
                    JSONObject icon = new JSONObject();
                    try{
                        icon = ((JSONObject)activityJSON.get(i)).getJSONObject("icon");
                        System.out.println("icon: " + icon);
                    }catch (JSONException ex){
                        Log.d("icon","icon fail");
                    }
                    JSONObject metaInfo = new JSONObject();
                    try{
                        metaInfo = icon.getJSONObject("metaInfo");
                        System.out.println("metaInfo: " + metaInfo);
                    }catch (JSONException ex){
                        Log.d("metaInfo","metaInfo fail");
                    }
                    String href = "";
                    try{
                        href = metaInfo.getString("href");
                        System.out.println("href: " + href);
                    }catch (JSONException ex){
                        Log.d("href","href fail");
                    }

                    /*
                    ImageView tmpIcon = new ImageView(this);

                    Picasso.with(this)
                            .load(href)
                            .placeholder(R.drawable.a_01_b)
                            .error(R.drawable.a_01_b)
                            .into(tmpIcon);

                    System.out.println("tmpIcon: " + tmpIcon.getResources().toString());
                    */

                    activityList.add(new ActivityItem()
                                    .setIconUrl(href)
                                    .setId(new JSONObject(activityJSON.get(i).toString()).getString("id"))
                                    .setName(new JSONObject(activityJSON.get(i).toString()).getString("name"))
                                    .setDescription(new JSONObject(activityJSON.get(i).toString()).getString("description"))
                    );




                }



            } catch (JSONException e) {
                Log.e("Reservation01Activity:", "JSON parsing failed");
                e.printStackTrace();
            }
            Log.d("Reservation01Activity: ", resultContent);


        } else {
            Log.d("Reservation01Activity", "Failed to load a list of activities");
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

                ActivityItem clickedItem = activityList.get(position);

                Intent intent = new Intent(getApplicationContext(), Reservation02Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                //initialize new Rezervation in RezervationHolder
                ReservationHolder.setReservation(new Reservation());
                ReservationHolder.setReservationActivityId(clickedItem.getId());
                ReservationHolder.setReservationActivityName(clickedItem.getName());
                ReservationHolder.setIconId(clickedItem.getIconID());
                ReservationHolder.setIconUrl(clickedItem.getIconUrl());

                System.out.println("content in step 1 = " + resultContent);

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
