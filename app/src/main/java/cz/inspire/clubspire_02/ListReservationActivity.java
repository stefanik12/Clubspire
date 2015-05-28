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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.list_items.Day;
import cz.inspire.clubspire_02.list_items.ReservationListItem;


public class ListReservationActivity extends AbstractBaseActivity {

    private List<ReservationListItem> reservationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        new LocalAsyncAPIRequestExtension().execute("/api/reservations/my/actual", HttpMethod.GET);
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
                        Log.d("data attibute arrray","is empty");

                        String shownText = baseJSON.getJSONObject("message").getString("clientMessage");
                        
                        //TODO: shownText in ListReservationuserMessage is not well-formed
                        ((TextView) findViewById(R.id.ListReservationuserMessage)).setText(shownText);
                    } else {
                        populateReservationList();
                        //TODO:
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

        private void populateReservationList() {
            //TODO: sparsovat resultContent do zoznamu aktivit:

            reservationList.clear();
            Date day;
            Calendar cal = Calendar.getInstance();
            Time start = new Time();
            Time end = new Time();
            cal.set(Calendar.HOUR_OF_DAY, 17);
            cal.set(Calendar.MINUTE, 30);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.DAY_OF_MONTH, 24);
            cal.set(Calendar.MONTH, 11);
            cal.set(Calendar.YEAR, 2014);
            day = cal.getTime();
            start.set(0, 30, 15, 24, 12, 2015);
            end.set(0, 00, 14, 24, 12, 2015);

            reservationList.add(new ReservationListItem(R.drawable.a_01_b, "Plavání", day, Day.PO, start, end));
            reservationList.add(new ReservationListItem(R.drawable.a_02_b, "Volejbal", day, Day.PO, start, end));
            reservationList.add(new ReservationListItem(R.drawable.a_01_b, "Posilovna", day, Day.PO, start, end));
            reservationList.add(new ReservationListItem(R.drawable.a_02_b, "Plavání", day, Day.PO, start, end));
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

                    intent.putExtra("EXTRA_ICON_ID", 1);
                    intent.putExtra("EXTRA_ACTIVITY_NAME", clickedReservation.getActivityName());
                    intent.putExtra("EXTRA_DATE", clickedReservation.getDateString());
                    intent.putExtra("EXTRA_START", clickedReservation.getStartString());
                    intent.putExtra("EXTRA_END", clickedReservation.getEndString());

                    startActivity(intent);

                }

            });
        }


        //TODO:  ListView, resp. adaptery by měly implementovat ViewHolder pattern viz
        //  http://developer.android.com/training/improving-layouts/smooth-scrolling.html
        //  nebo http://www.vogella.com/tutorials/AndroidListView/article.html (8.4)

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

                //View holder implementation:
                ViewHolder holder = new ViewHolder();

                // Find the ActivityItem to work with.
                ReservationListItem currentReservation = reservationList.get(position);

                // Date:
                holder.dateText = (TextView) itemView.findViewById(R.id.reservation_item_txtDate);
                //dateText.setText(currentReservation.getDateString() + "(" + currentReservation.getStartString() + "-" + currentReservation.getEndString() + ")");

                // Fill the view
                holder.imageView = (ImageView) itemView.findViewById(R.id.reservation_item_icon);
                //imageView.setImageResource(currentReservation.getIconId());

                // Name:
                holder.nameText = (TextView) itemView.findViewById(R.id.reservation_item_txtName);
                //nameText.setText(currentReservation.getActivityName());

                itemView.setTag(holder);

                //task will be used to populate data from REST (one day)
                // Using an AsyncTask to load the slow images in a background thread
                //see http://developer.android.com/training/improving-layouts/smooth-scrolling.html
                new AsyncTask<ViewHolder, Void, ReservationListItem>() {
                    private ViewHolder v;

                    @Override
                    protected ReservationListItem doInBackground(ViewHolder... params) {
                        //TODO: spracovat REST, vratit novy objekt ReservationListItem
                        return null;
                    }

                    @Override
                    protected void onPostExecute(ReservationListItem result) {
                        super.onPostExecute(result);
                    /*
                    if (v.position == position) {
                        // If this item hasn't been recycled already, hide the
                        // progress and set and show the image
                        v.progress.setVisibility(View.GONE);
                        v.imageView.setVisibility(View.VISIBLE);
                        v.imageView.setImageBitmap(result);
                    }
                    */
                    }
                }.execute(holder);

                return itemView;
            }

            private class ViewHolder {
                public TextView dateText;
                public ImageView imageView;
                public TextView nameText;
                public ProgressBar progress;
                public final int position = 0;

            }
        }
    }
}
