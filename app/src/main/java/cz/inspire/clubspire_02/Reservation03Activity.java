package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.RESTconfiq;
import cz.inspire.clubspire_02.APIResources.ReservationHolder;
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
        toolbarMenuPresent = true;

        parentIntent = new Intent(getApplicationContext(), Reservation02Activity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_03);

        setupActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityName = ReservationHolder.getReservationActivityName();
            date = extras.getString("EXTRA_DATE");
            start = extras.getString("EXTRA_START");
            end = extras.getString("EXTRA_END");
            iconId = extras.getInt("EXTRA_ICON_ID");

        } else {
            Log.e("onCreate", "one step of reservation was skipped");
        }
        //set items text
        setReservationItemsContent(activityName, date, start, end, USER);

        //set CONFIRM button size
        int scrHeight = getWindowManager().getDefaultDisplay().getHeight();
        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setHeight(scrHeight/10);

        //set CONFIRM button listener
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reservation reservation = ReservationHolder.getReservation();
                reservation.setNote(((EditText) findViewById(R.id.editTextNoteContent)).getText().toString());

                //TODO Reservation: z layoutu dostat vsetko relevantne pre novovytvorenu rezervaciu - nejako ako ten note hore
                //upozorneni (ak je zaskrtnuta, tak nejaky default, inak asi =0)
                //pocet ucastniku
                //poznamka
                //este neviem co ma byt objectId


                Gson gson = new Gson();
                String sentText = gson.toJson(reservation);
                /*
                FUNKCNY REQUEST:
                sentText = "{\"instructorId\": \"63a194abac1303f0012bd8989a131fa2\",\n" +
                        "\"sportId\":\"67b55713ac1303f000b4d50b38bf0a91\",\n" +
                        "\"objectId\":\"67bbccf3ac1303f000c13e2f9f3d55d2\",\n" +
                        "\"note\":\"poznamka z RESTu\",\n" +
                        "\"personCount\":1,\n" +
                        "\"startTime\":\"2015-06-19T15:30:00.000+0200\",\n" +
                        "\"endTime\":\"2015-09-19T16:20:00.000+0200\",\n" +
                        "\"emailNotificationBeforeMinutes\":120,\n" +
                        "\"smsNotificationBeforeMinutes\":60}";
                */


                Log.d("serialized registration", sentText);
                //na ziskanie instructorId, sportId, ?objectId treba dalsi request na /api/actviity/{ID}

                //doplnenie infa do Reservation a odoslanie rezervacie sa poriesi v onPostExecute

                new LocalAsyncAPIRequestExtension().execute("/api/activities/" + ReservationHolder.getReservationActivityId(), HttpMethod.GET);;
            }
        });


        //set CANCEL button listener
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation02Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXTRA_ICON_ID", iconId);
                intent.putExtra("EXTRA_ACTIVITY_NAME", MainMenuActivity.class);
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
        private ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar3);
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
            Log.d("loaded content:", resultContent);

            //next activity initialization
            Intent intent = new Intent(getApplicationContext(), Reservation02Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXTRA_ICON_ID", iconId);
            intent.putExtra("EXTRA_ACTIVITY_NAME", activityName);

            progressBar.setVisibility(View.INVISIBLE);
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
