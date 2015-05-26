package cz.inspire.clubspire_02;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.list_items.ReservationItem;

/**
 * Created by Michal on 27. 4. 2015.
 */
public class ViewReservationActivity  extends AbstractBaseActivity {

    private Toolbar mToolbar;
    private final String USER = "Vukmir";
    final Context context = this;
    final int CUSTOM_DIALOG = 0;
    final int DEFAULT_DIALOG = 1;

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
        parentIntent = new Intent(getApplicationContext(), ListReservationActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation);

        setupActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityName = extras.getString("EXTRA_ACTIVITY_NAME");
            date = extras.getString("EXTRA_DATE");
            start = extras.getString("EXTRA_START");
            end = extras.getString("EXTRA_END");
            iconId = extras.getInt("EXTRA_ICON_ID");

        }else{
            activityName = "EXTRA_ACTIVITY_NAME";
            date = "EXTRA_DATE";
            start = "EXTRA_START";
            end = "EXTRA_END";
            iconId = 1;
        }
        //set items text
        setReservationItemsContent(activityName, date, start, end, USER);


        //set BACK button listener
        Button btnConfirm = (Button) findViewById(R.id.btnViewReservationConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Rezervace upravena", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListReservationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //set BACK button size
        int scrWidth  = getWindowManager().getDefaultDisplay().getWidth();
        int scrHeight = getWindowManager().getDefaultDisplay().getHeight();
        //btnConfirm.setWidth(scrWidth/2);
        btnConfirm.setHeight(scrHeight/10);

        //set CANCEL button listener


        Button btnDelete = (Button) findViewById(R.id.btnViewReservationCancel);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Dialog d = onCreateDialog(0);
                Dialog d = CustomDialog.createDialog(0, context, ListReservationActivity.class, getString(R.string.text_cancel_reservation),getString(R.string.text_cancel_reservation_question),
                        getString(R.string.text_no),getString(R.string.text_yes),getString(R.string.text_reservation_canceled));
                d.show();
            }
        });
        //set DELETE button size
        btnDelete.setHeight(scrHeight / 10);


    }

    private void setReservationItemsContent(String activityName, String date, String start, String end, String user )
    {
        this.activityText = (TextView) findViewById(R.id.txtViewReservationActivityContent);
        activityText.setText(activityName);

        this.dateText = (TextView) findViewById(R.id.txtViewReservationDateContent);
        dateText.setText(date);

        this.timeText = (TextView) findViewById(R.id.txtViewReservationTimeContent);
        timeText.setText(start + " - " + end);

        this.userText = (TextView) findViewById(R.id.txtViewReservationUserContent);
        userText.setText(user);

    }


}