package cz.inspire.clubspire_02;

import android.content.Intent;
import android.nfc.FormatException;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.inspire.clubspire_02.APIResources.AuthenticationHolder;
import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.RESTconfiq;
import cz.inspire.clubspire_02.APIResources.ReservationHolder;
import cz.inspire.clubspire_02.POJO.Reservation;
import cz.inspire.clubspire_02.list_items.ReservationItem;


public class Reservation03Activity extends AbstractBaseActivity {

    private Toolbar mToolbar;

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
        setReservationItemsContent(activityName, date, start, end, AuthenticationHolder.getUsername());

        //set CONFIRM button size
        int scrHeight = getWindowManager().getDefaultDisplay().getHeight();
        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setHeight(scrHeight/10);

        //set CONFIRM button listener
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reservation reservation = ReservationHolder.getReservation();
                reservation.setNote(((EditText) findViewById(R.id.editTextNoteContent)).getText().toString());


                //parse information from EditText
                int personCount = Integer.parseInt(((EditText) findViewById(R.id.editPlayersContent)).getText().toString());
                ReservationHolder.getReservation().setPersonCount(personCount);
                Boolean smsNotification = ((CheckBox)findViewById(R.id.checkBox_notifyMobile)).isChecked();
                //TODO get length for notification
                if(smsNotification)
                    ReservationHolder.getReservation().setSmsNotificationBeforeMinutes(30);
                else
                    ReservationHolder.getReservation().setSmsNotificationBeforeMinutes(0);//how to set NULL..as no notification???
                Boolean emailNotification = ((CheckBox)findViewById(R.id.checkBox_notifyEmail)).isChecked();
                //TODO get length for notification
                if(emailNotification)
                    ReservationHolder.getReservation().setEmailNotificationBeforeMinutes(30);
                else
                    ReservationHolder.getReservation().setEmailNotificationBeforeMinutes(0);//how to set NULL..as no notification???

                System.out.println("reservation toStr = " + reservation.toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
                String parsedStartTime = "";
                String parsedEndTime = "";
                try {
                    parsedStartTime = sdf.format(reservation.getStartTime());
                    //System.out.println(parsedStartTime);
                    parsedEndTime = sdf.format(reservation.getEndTime());
                    //System.out.println(parsedEndTime);

                }catch (Exception e){
                    Log.d("startTime", "startTime fail " + e);
                }

                //for now we use manually written format
                String mySentText =
                        "{\"instructorId\":\"" + reservation.getInstructorId() + "\",\n" +
                        "\"sportId\":\"" + reservation.getSportId() +  "\",\n" +
                        "\"objectId\":\"" + reservation.getObjectId() + "\",\n" +
                        "\"note\":\"" + reservation.getNote() +  "\",\n" +
                        "\"personCount\":" + reservation.getPersonCount() + ",\n" +
                        "\"startTime\":\"" + parsedStartTime + "\",\n" +
                        "\"endTime\":\"" + parsedEndTime+ "\",\n" +
                        "\"emailNotificationBeforeMinutes\":" + reservation.getEmailNotificationBeforeMinutes() +  ",\n" +
                        "\"smsNotificationBeforeMinutes\":" + reservation.getSmsNotificationBeforeMinutes() +  "}";


                //TODO try to fix startTime format so we can use Gson
                Gson gson = new Gson();
                //String sentText = gson.toJson(reservation);


                Log.d("MY serialized reg", mySentText);

                //doplnenie infa do Reservation a odoslanie rezervacie sa poriesi v onPostExecute
                new LocalAsyncAPIRequestExtension().setPlainRequest(mySentText).execute("/reservations", HttpMethod.POST);
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

            if(resultContent != null) {
                Log.d("loaded content:", resultContent);

                try {
                    JSONObject baseJSON = new JSONObject(resultContent);
                    int status = baseJSON.getJSONObject("message").getInt("httpStatus");

                    String userMessage = baseJSON.getJSONObject("message").getString("clientMessage");

                    if(status == 200){

                        //next activity initialization
                        Intent intent = new Intent(getApplicationContext(), ListReservationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("lastReservationStatus", userMessage);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e("Reservation03Activity:", "JSON parsing failed");
                    e.printStackTrace();
                }
            } else {
                Log.e("Reservation03Activity", "resultContent was null");
            }



            progressBar.setVisibility(View.INVISIBLE);

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
