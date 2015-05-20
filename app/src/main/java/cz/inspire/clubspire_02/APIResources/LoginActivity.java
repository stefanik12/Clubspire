package cz.inspire.clubspire_02.APIResources;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.R;
import cz.inspire.clubspire_02.RegisterActivity;


/**
 * Created by michal on 5/15/15.
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w("LoginActivity: ", "started");

        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginbutton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncAuthentization().execute();
            }
        });
    }

    private class AsyncAuthentization extends AsyncTask<Void, Void, AccessTokenObject> {

        private final String username = "fimuni";
        private final String password = "123456";

        private AccessTokenObject token;

        @Override
        protected AccessTokenObject doInBackground(Void... voids) {

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
            nameValuePairs.add(new BasicNameValuePair("client_id", RESTconfiq.CLIENT_ID));
            nameValuePairs.add(new BasicNameValuePair("client_secret", RESTconfiq.CLIENT_SECRET));
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("scope", "write"));

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RESTconfiq.BASE_URL + "/oauth/token");


            Log.d("onCreate: ", "before httppost");

            String resultContent = null;
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                ResponseHandler<String> handler = new BasicResponseHandler();

                resultContent = handler.handleResponse(response);

            } catch (Exception e) {
                Log.e("loadToken:", "app was unable to request auth token from server");
                e.printStackTrace();
            }

            try {
                JSONObject jsonHeader = new JSONObject(resultContent);

                token = new AccessTokenObject().setAccessToken(jsonHeader.get("access_token").toString())
                        .setExpires_in(Long.parseLong(jsonHeader.get("expires_in").toString()))
                        .setScope(jsonHeader.get("scope").toString())
                        .setTokenType(jsonHeader.get("token_type").toString());

                return token;
            } catch (JSONException e) {
                Log.e("loadToken:", "retrieved token was not serializable");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(AccessTokenObject token) {
            super.onPostExecute(token);

            if (token != null) {
                Log.w("onPostExecute: token:", token.getAccessToken());

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                //zly token TODO spracuj chybne prihlasenie
                Toast.makeText(getApplicationContext(), "Prihlasenie zlyhalo", Toast.LENGTH_SHORT).show();
            }
        }
    }

}