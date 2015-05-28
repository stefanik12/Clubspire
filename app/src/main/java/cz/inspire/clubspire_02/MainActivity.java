package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.AccessTokenObject;
import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.RESTconfiq;
import cz.inspire.clubspire_02.APIResources.TokenHolder;


public class MainActivity extends AbstractBaseActivity {

    private Toolbar mToolbar;

    // you should either define client id and secret as constants or in string resources
    private final String username = "fimuni";
    private final String password = "123456";


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
                List<NameValuePair> requestParams = new ArrayList<>();
                requestParams.add(new BasicNameValuePair("grant_type", "password"));
                requestParams.add(new BasicNameValuePair("client_id", RESTconfiq.CLIENT_ID));
                requestParams.add(new BasicNameValuePair("client_secret", RESTconfiq.CLIENT_SECRET));
                requestParams.add(new BasicNameValuePair("username", username));
                requestParams.add(new BasicNameValuePair("password", password));
                requestParams.add(new BasicNameValuePair("scope", "write"));

                new AsyncAuthentization().setParameters(requestParams).execute("/oauth/token", HttpMethod.POST);
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
            Log.d("onPostExecute", "tokenObject loading finished");

            AccessTokenObject tokenObject = null;
            if(resultContent != null){
                try {
                    JSONObject jsonHeader = new JSONObject(resultContent);
                    tokenObject = new AccessTokenObject().setAccessToken(jsonHeader.get("access_token").toString())
                            .setExpires_in(Long.parseLong(jsonHeader.get("expires_in").toString()))
                            .setScope(jsonHeader.get("scope").toString())
                            .setTokenType(jsonHeader.get("token_type").toString());
                } catch (JSONException e) {
                    Log.e("loadToken:", "retrieved tokenObject was not serializable");
                    e.printStackTrace();
                }
            } else {
                Log.e("OnPostExecute", "retrieved content was not valid");
            }

            if (tokenObject != null) {
                Log.d("onPostExecute: token:", tokenObject.getAccessToken());

                TokenHolder.setTokenObject(tokenObject);

                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                //zly tokenObject <= chybne prihlasenie TODO: neskor spracovat
                Toast.makeText(getApplicationContext(), "Prihlasenie zlyhalo", Toast.LENGTH_SHORT).show();
            }

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}
