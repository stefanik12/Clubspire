package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainMenuActivity extends AbstractReservationActivity {

    private Toolbar mToolbar;
    private final String mainMenuActionBarText = "Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupActionBar();
        setTitle(mainMenuActionBarText);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //mToolbar.setNavigationIcon(R.drawable.ic_action);

        //set reservation button listener
        Button buttonReservation = (Button) findViewById(R.id.btn_reservation);
        buttonReservation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation01Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //set list reservation button listener
        Button buttonListReservation = (Button) findViewById(R.id.btn_reservation_list);
        buttonListReservation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListReservationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //set hint button listener
        Button buttonhint= (Button) findViewById(R.id.btn_hint);
        buttonhint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HintActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void setupActionBar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }
}
