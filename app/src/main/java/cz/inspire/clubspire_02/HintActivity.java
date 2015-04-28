package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class HintActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private final String hintActionBarText = "Nápověda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        setupActionBar();
        setTitle(hintActionBarText);

////--//
        //
        ///dfgsf/gf/sdrg/df/gdf/g/d

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        });


    }
    private void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }


}
