package cz.inspire.clubspire_02;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.AccessTokenObject;
import cz.inspire.clubspire_02.APIResources.AuthenticationHolder;
import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.RESTconfiq;

/**
 * Created by michal on 5/3/15.
 */
public abstract class AbstractBaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private String suffix;
    protected String resultContent;
    public boolean toolbarMenuPresent;
    public Intent parentIntent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (toolbarMenuPresent)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            AuthenticationHolder.setTokenObject(null);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.left_arrow_white);

        mToolbar.setOnClickListener(toolbarListener);
        mToolbar.setNavigationOnClickListener(arrowListener);
    }

    protected void setupActionBarWithoutArrow() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


    }

    private View.OnClickListener toolbarListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            //TODO seems to be clearing cache only when pressing the arrow, not menu - WTF?
            //android.os.Process.killProcess(android.os.Process.myPid());
            finish();
        }
    };

    private View.OnClickListener arrowListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            parentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(parentIntent);

            //TODO seems to be clearing cache only when pressing the arrow, not menu - WTF?
            //android.os.Process.killProcess(android.os.Process.myPid());
            finish();
        }
    };

    //API network services:
    protected class AsyncAPIRequest extends AsyncTask<Void, Void, Void> {
        //now supports GET and POST methods

        //holds name-value pairs to be sent to the API server
        private List<NameValuePair> nameValuePairs = new ArrayList<>();
        private String plainRequest;
        private HttpMethod requestMethod;
        private HttpURLConnection urlConnection = null;

        protected void execute(String s, HttpMethod requestMethod) {
            suffix = s;
            this.requestMethod = requestMethod;
            execute();
        }

        protected AsyncAPIRequest setParameters(List<NameValuePair> pairs) {
            nameValuePairs = pairs;
            return this;
        }

        protected AsyncAPIRequest setPlainRequest(String data) {
            this.plainRequest = data;
            return this;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //HttpURLConnection implementation-universal for all GET/POST/PUT methods:
            int responseCode = 0;
            Log.d("API request", " on "+RESTconfiq.BASE_URL + suffix);

            try {
                if (AuthenticationHolder.getTokenObject() == null || ! AuthenticationHolder.getTokenObject().isValid()) {

                    //token request: requests token from server, and if everything is ok, fills retrieved token to AuthenticationHolder
                    requestToken("fimuni","123456");

                    return null;
                }
                if (AuthenticationHolder.getTokenObject().isValid()) {
                    Log.d("token used", AuthenticationHolder.getTokenObject().getAccessToken());

                    if (requestMethod.equals(HttpMethod.POST) && plainRequest != null) {

                        //sending plainRequest content using POSt - when creating new reservation
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(RESTconfiq.BASE_URL + suffix);
                        Log.d("onCreate: ", "before httppost: request: " + RESTconfiq.BASE_URL + suffix);

                        //set auth header:
                        httppost.addHeader("Authorization", "Bearer " + AuthenticationHolder.getTokenObject().getAccessToken());
                        httppost.addHeader("Content-Type", "application/json");

                        httppost.setEntity(new StringEntity(plainRequest));

                        HttpResponse response = httpclient.execute(httppost);
                        ResponseHandler<String> handler = new BasicResponseHandler();

                        responseCode = response.getStatusLine().getStatusCode();
                        if(responseCode == 401) {
                            // auth token expired
                            requestToken("fimuni","123456");
                        }

                        resultContent = handler.handleResponse(response);

                    } else if (requestMethod.equals(HttpMethod.GET) || requestMethod.equals(HttpMethod.PUT)){
                        //retrieving content

                        //set parameters to GET:
                        StringBuilder requestURL = new StringBuilder();
                        requestURL.append(RESTconfiq.BASE_URL);
                        requestURL.append(suffix);

                        if(nameValuePairs.size()>0){
                            requestURL.append("?");
                        }
                        for (NameValuePair pair : nameValuePairs) {
                            requestURL.append(pair.getName());
                            requestURL.append("=");
                            requestURL.append(pair.getValue());
                            requestURL.append("&&");
                        }
                        if(nameValuePairs.size()>0){
                            requestURL.delete(requestURL.length() - 1, requestURL.length());
                        }

                        Log.d("requested URL", requestURL.toString());

                        HttpURLConnection urlConnection = (HttpURLConnection) new URL(requestURL.toString()).openConnection();
                        urlConnection.setRequestMethod(requestMethod.name());

                        //set auth header:
                        urlConnection.setRequestProperty("Authorization", "Bearer " + AuthenticationHolder.getTokenObject().getAccessToken());
                        urlConnection.setRequestProperty("Content-Type", "application/json");
                        urlConnection.setRequestMethod(requestMethod.name());

                        responseCode = urlConnection.getResponseCode();

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        resultContent = IOUtils.toString(in, "UTF-8");

                    }
                } else {
                    Log.d("AsyncAPIRequest", "Token is not valid - this should never happen");
                }
            } catch (Exception e) {
                Log.e("doInBackground", "error while retrieving content from " + RESTconfiq.BASE_URL + suffix);
                e.printStackTrace();

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            Log.d("response code ",String.valueOf(responseCode));
            if(resultContent != null){
                Log.d("result content", resultContent);
            } else {
                Log.e("result content", "is null");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            //TODO: if needed, use static singleton for passing retrieved data between activities
        }

        private boolean requestToken(String username, String password){
            List<NameValuePair> requestParams = new ArrayList<>();
            requestParams.add(new BasicNameValuePair("grant_type", "password"));
            requestParams.add(new BasicNameValuePair("client_id", RESTconfiq.CLIENT_ID));
            requestParams.add(new BasicNameValuePair("client_secret", RESTconfiq.CLIENT_SECRET));
            requestParams.add(new BasicNameValuePair("username", AuthenticationHolder.getUsername()));
            requestParams.add(new BasicNameValuePair("password", AuthenticationHolder.getPassword()));
            requestParams.add(new BasicNameValuePair("scope", "write"));

            Log.d("AsyncAPIRequest", "Token request");
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(RESTconfiq.BASE_URL + "/oauth/token");

            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                ResponseHandler<String> handler = new BasicResponseHandler();
                resultContent = handler.handleResponse(response);
            } catch (IOException e) {
                Log.e("requestToken", "unable to resolve token content from server");
                e.printStackTrace();
                return false;
            }

            //parse response
            if(resultContent != null){
                try {
                    JSONObject jsonHeader = new JSONObject(resultContent);
                    AccessTokenObject tokenObject = new AccessTokenObject().setAccessToken(jsonHeader.get("access_token").toString())
                            .setExpires_in(Long.parseLong(jsonHeader.get("expires_in").toString()))
                            .setScope(jsonHeader.get("scope").toString())
                            .setTokenType(jsonHeader.get("token_type").toString());

                    AuthenticationHolder.setTokenObject(tokenObject);
                    Log.d("AsyncAPIRequest", "retrieved token: "+ AuthenticationHolder.getTokenObject().getAccessToken());
                } catch (JSONException e) {
                    Log.e("AsyncAPIRequest:", "retrieved tokenObject was not serializable");
                    e.printStackTrace();
                    return false;
                }
            } else {
                Log.e("AsyncAPIRequest", "invalid request for token");
                return false;
            }
            return true;
        }
    }
}

