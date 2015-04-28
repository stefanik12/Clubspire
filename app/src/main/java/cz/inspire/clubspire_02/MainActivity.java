package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private final String loginActionBarText = "Přihlášení";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();
        setTitle(loginActionBarText);


        //set login button listener (for now to main menu)
        Button buttonLogin = (Button) findViewById(R.id.btn_MenuLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        });

        //set register button listener
        Button buttonRegister = (Button) findViewById(R.id.btn_MenuRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

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
