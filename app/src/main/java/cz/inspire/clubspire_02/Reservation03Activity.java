package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.RESTconfiq;
import cz.inspire.clubspire_02.POJO.Reservation;
import cz.inspire.clubspire_02.list_items.ReservationItem;


public class Reservation03Activity extends AbstractBaseActivity {

    private Toolbar mToolbar;
    private final String USER = "Vukmir";

    private List<ReservationItem> reservationList = new ArrayList<>();
    private String activityName;
    private int iconId;
    private String date;
    private String start;
    private String end;

    private TextView activityText;
    private TextView dateText;
    private TextView timeText;
    private TextView userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parentIntent = new Intent(getApplicationContext(), Reservation02Activity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_03);

        setupActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityName = extras.getString("EXTRA_ACTIVITY_NAME");
            date = extras.getString("EXTRA_DATE");
            start = extras.getString("EXTRA_START");
            end = extras.getString("EXTRA_END");
            iconId = extras.getInt("EXTRA_ICON_ID");

        } else {
            Log.e("onCreate", "one step of reservation was skipped");
        }
        //set items text
        setReservationItemsContent(activityName, date, start, end, USER);

        //set CONFIRM button listener
        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getString(R.string.text_reservation_complete), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListReservationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        //set CONFIRM button size
        int scrHeight = getWindowManager().getDefaultDisplay().getHeight();

        btnConfirm.setHeight(scrHeight/10);

        //!!!!!!!!!!!!TODO dvojí deklarace
        /*
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reservation reservation = new Reservation();
                reservation.setNote("seihgdgf");

                GsonBuilder gson = new GsonBuilder();
                Object json = JSONObject.wrap(reservation);

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
                //nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));

                //new LocalAsyncAPIRequestExtension().execute("/api/reservations", HttpMethod.POST);
            }
        });
        */

        //set CANCEL button listener
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation02Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    protected class LocalAsyncAPIRequestExtension extends AsyncAPIRequest {
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            Log.d("onPostExecute", "in LocalAsyncAPIRequestExtension called");
            Log.d("loaded content:", resultContent.toString());

            //next activity initialization
            Intent intent = new Intent(getApplicationContext(), Reservation02Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXTRA_ICON_ID", iconId);
            intent.putExtra("EXTRA_ACTIVITY_NAME", activityName);
            startActivity(intent);
        }
    }


    private void setReservationItemsContent(String activityName, String date, String start, String end, String user )
    {
        this.activityText = (TextView) findViewById(R.id.txtActivityContent);
        activityText.setText(activityName);

        this.dateText = (TextView) findViewById(R.id.txtDateContent);
        dateText.setText(date);

        this.timeText = (TextView) findViewById(R.id.txtTimeContent);
        timeText.setText(start + " - " + end);

        this.userText = (TextView) findViewById(R.id.txtUserContent);
        userText.setText(user);

    }

}
