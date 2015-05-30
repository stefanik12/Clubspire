package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import cz.inspire.clubspire_02.APIResources.AuthenticationHolder;
import cz.inspire.clubspire_02.APIResources.HttpMethod;


public class MainActivity extends AbstractBaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbarMenuPresent = false;

        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.string_login));

        setContentView(R.layout.activity_main);
        setupActionBarWithoutArrow();

        Button buttonLogin = (Button) findViewById(R.id.btn_MenuLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AuthenticationHolder.setPassword("123456");
                AuthenticationHolder.setUsername("fimuni");

                new AsyncAuthentization().execute("/oauth/token", HttpMethod.POST);
            }
        });

        //set register button listener
        Button buttonRegister = (Button) findViewById(R.id.btn_MenuRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    private class AsyncAuthentization extends AsyncAPIRequest {

        private ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //make loader visible:
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            progressBar.setVisibility(View.INVISIBLE);

            if (AuthenticationHolder.getTokenObject() != null) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                //zly tokenObject <= chybne prihlasenie
                Toast.makeText(getApplicationContext(), "Prihlasenie zlyhalo. Skontrolujte meno a heslo", Toast.LENGTH_LONG).show();
            }
        }
    }

}
