package cz.inspire.clubspire_02.retrofitResources;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*
import org.apache.cxf.rs.security.oauth2.common.AccessToken;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.tokens.bearer.BearerAccessToken;
/*/
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
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
import cz.inspire.clubspire_02.retrofitResources.POJO.AccessToken;


/**
 * Created by michal on 5/15/15.
 */
public class LoginActivity extends Activity {

    // you should either define client id and secret as constants or in string resources
    private final String clientId = "fimuni_app";
    private final String clientSecret = "fimuni";
    private final String username = "fimuni";
    private final String password = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.w("LoginActivity: ", "started");

        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginbutton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    AccessToken token = loadToken();
                    Log.w("onClick:", token.getAccessToken());
                    Log.w("Token.toString:", token.toString());
                } catch (AuthenticationException e) {
                    Log.e("onClick:", "Authentication failed");
                    e.printStackTrace();
                }

            }
        });
    }
    /*
    @Override
    protected void onResume() {
        super.onResume();

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            // use the parameter your API exposes for the code (mostly it's "code")
            String code = uri.getQueryParameter("code");
            if (code != null) {
                // get access token
                LoginService loginService = ServiceGenerator.createService(LoginService.class, LoginService.BASE_URL, clientId, clientSecret);
                AccessToken accessToken = loginService.getAccessToken(code, "authorization_code");
            } else if (uri.getQueryParameter("error") != null) {
                // show an error message here
            }
        }
    }
    */


    private AccessToken loadToken() throws AuthenticationException{
        //TODO remove later:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
        nameValuePairs.add(new BasicNameValuePair("client_id", clientId));
        nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        nameValuePairs.add(new BasicNameValuePair("scope", "write"));

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(LoginService.BASE_URL + "/oauth/token");


        Log.w("onCreate: ", "before httppost");

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
//              Unresolved dependency library template:
/*              Client client = new Client(clientId, clientSecret, true);
                BearerAccessToken token = new BearerAccessToken(client, jsonHeader.get("access_token").toString(),
                    Long.parseLong(jsonHeader.get("expires_in").toString()),
                    System.currentTimeMillis()/1000);
*/
            AccessToken token = new AccessToken().setAccessToken(jsonHeader.get("access_token").toString())
                            .setExpires_in(Long.parseLong(jsonHeader.get("expires_in").toString()))
                            .setScope(jsonHeader.get("scope").toString())
                            .setTokenType(jsonHeader.get("token_type").toString());
            return token;
        } catch (JSONException e) {
            Log.e("loadToken:", "retrieved token was not serializable");
            e.printStackTrace();
        }

        throw new AuthenticationException("Authentication failed");
    }


}