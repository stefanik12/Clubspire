package cz.inspire.clubspire_02;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.ReservationHolder;
import cz.inspire.clubspire_02.POJO.Reservation;
import cz.inspire.clubspire_02.list_items.Day;
import cz.inspire.clubspire_02.list_items.ReservationListItem;


public class ListReservationActivity extends AbstractBaseActivity {

    private List<ReservationListItem> reservationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //process intent from last created reservation
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.getString("lastReservationStatus") != null){
                Toast.makeText(getApplicationContext(), extras.getString("lastReservationStatus"), Toast.LENGTH_LONG).show();
            }
        }

        //will be used to edit reservation
        ReservationHolder.setReservation(null);
        ReservationHolder.setReservationActivityId(null);
        ReservationHolder.setReservationActivityName(null);
        ReservationHolder.setIconId(null);

        toolbarMenuPresent = true;
        parentIntent = new Intent(getApplicationContext(), MainMenuActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);

        setupActionBar();

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
            }
        });

        new LocalAsyncAPIRequestExtension().execute("/api/1.0/reservations/my/actual", HttpMethod.GET);
    }

    private class LocalAsyncAPIRequestExtension extends AsyncAPIRequest {

        private ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarViewActivity);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //make loader visible:
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void v) {

            if (resultContent != null) {
                try {
                    JSONObject baseJSON = new JSONObject(resultContent);

                    if (baseJSON.getJSONArray("data").length() < 1) {
                        Log.d("data attribute array","is empty");

                        String shownText = baseJSON.getJSONObject("message").getString("clientMessage");

                        //TODO: shownText in ListReservationuserMessage is not well-formed
                        ((TextView) findViewById(R.id.ListReservationuserMessage)).setText(shownText);
                    } else {
                        populateReservationList();
                        populateListView();
                        registerRegistrationClickCallback();
                    }


                } catch (JSONException e) {
                    Log.e("ListReservationActivity", "JSON parsing failed");
                    e.printStackTrace();
                }
            }

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void populateReservationList() {

        reservationList.clear();

        try {
            JSONObject baseJSON = new JSONObject(resultContent);
            JSONArray reservations = baseJSON.getJSONArray("data");
            for(int i = 0;i<reservations.length();i++){
                JSONObject iteratedItem = reservations.getJSONObject(i);

                //Filter results - show only ones with no state = canceled
                boolean showItem = true;
                JSONArray states = iteratedItem.getJSONArray("states");
                for(int j = 0;j<states.length();j++){
                    Log.d(this.getLocalClassName(), "found state: "+states.getString(j));

                    if(states.getString(j).equals("CANCELED")){
                        showItem = false;
                    }
                }

                if(showItem){
                    String activityName = iteratedItem.getJSONObject("sport").getJSONObject("activity").getString("name");

                    String iconUrl = iteratedItem.getJSONObject("sport").getJSONObject("activity").getJSONObject("icon").getJSONObject("metaInfo").getString("href");
                    System.out.println("iconUrl: " + iconUrl);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

                    Date startDate = dateFormat.parse(iteratedItem.getString("startTime"));
                    Time startDateAsTime = new Time();
                    startDateAsTime.set(startDate.getTime());

                    Date endDate = dateFormat.parse(iteratedItem.getString("endTime"));
                    Time endDateAsTime = new Time();
                    endDateAsTime.set(endDate.getTime());

                    Calendar c = Calendar.getInstance();
                    c.setTime(startDate);
                    Integer weekDay = c.get(Calendar.DAY_OF_WEEK);

                    String activityId = iteratedItem.getJSONObject("sport").getJSONObject("activity").getString("id");

                    String reservationId = iteratedItem.getString("id");

                    ReservationListItem newItem = new ReservationListItem();
                    newItem.setStartDate(startDate)
                            .setIconUrl(iconUrl)
                            .setIconId(R.drawable.a_01_b)
                            .setActivityName(activityName)
                            .setDay(Day.values()[weekDay-2])
                            .setStart(startDateAsTime)
                            .setEnd(endDateAsTime)
                            .setActivityId(activityId)
                            .setId(reservationId);

                    //System.out.println("iteratedItem: " + iteratedItem);
                    Log.d("ListReservations", "Activity name: " + newItem.getActivityName());

                    reservationList.add(newItem);
                }
            }
        } catch (JSONException je) {
            Log.e("ListReservation", "parsing failed");
            je.printStackTrace();
        } catch (ParseException pe) {
            Log.e("LisReservation", "parsing date into object failed");
            pe.printStackTrace();
        }
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

                Intent intent = new Intent(getApplicationContext(), ViewReservationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                ReservationListItem clickedReservation = reservationList.get(position);

                //initialize ReservationHolder
                ReservationHolder.setIconId(clickedReservation.getIconId());
                ReservationHolder.setReservationActivityName(clickedReservation.getActivityName());
                ReservationHolder.setReservationActivityId(clickedReservation.getActivityId());
                ReservationHolder.setReservationId(clickedReservation.getId());

                intent.putExtra("EXTRA_ICON_ID", 1);
                intent.putExtra("EXTRA_ACTIVITY_NAME", clickedReservation.getActivityName());
                intent.putExtra("EXTRA_DATE", clickedReservation.getDateString());
                intent.putExtra("EXTRA_START", clickedReservation.getStartString());
                intent.putExtra("EXTRA_END", clickedReservation.getEndString());

                startActivity(intent);

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
            dateText.setText(currentReservation.getFormedDateTime());
            // set icon
            ImageView activityIcon = (ImageView)itemView.findViewById(R.id.reservation_item_icon);
            Picasso.with(getContext())
                    .load(currentReservation.getIconUrl())//TODO fix
                    .placeholder(R.drawable.loader_icon)
                    .error(R.drawable.error_icon)
                    .resize((int) App.getContext().getResources().getDimension(R.dimen.icon_width),(int)App.getContext().getResources().getDimension(R.dimen.icon_height))
                    .into(activityIcon);
            // Name:
            TextView nameText = (TextView) itemView.findViewById(R.id.reservation_item_txtName);
            nameText.setText(currentReservation.getActivityName());
            return itemView;
        }
    }
}
