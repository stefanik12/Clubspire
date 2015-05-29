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

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.APIResources.RESTconfiq;
import cz.inspire.clubspire_02.APIResources.TokenHolder;

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
            TokenHolder.setTokenObject(null);
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
                if (TokenHolder.getTokenObject() == null) {

                    //token request:

                    Log.d("AsyncAPIRequest", "Token request");
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(RESTconfiq.BASE_URL + suffix);
                    Log.d("onCreate: ", "before httppost: request: " + RESTconfiq.BASE_URL + suffix);

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    ResponseHandler<String> handler = new BasicResponseHandler();
                    resultContent = handler.handleResponse(response);

                    return null;
                }
                if (TokenHolder.getTokenObject().isValid()) {
                    Log.d("token used", TokenHolder.getTokenObject().getAccessToken());

                    if (requestMethod.equals(HttpMethod.POST) && plainRequest != null) {

                        //sending plainRequest content using POSt - when creating new reservation
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(RESTconfiq.BASE_URL + suffix);
                        Log.d("onCreate: ", "before httppost: request: " + RESTconfiq.BASE_URL + suffix);

                        //set auth header:
                        httppost.addHeader("Authorization", "Bearer " + TokenHolder.getTokenObject().getAccessToken());
                        httppost.addHeader("Content-Type", "application/json");

                        httppost.setEntity(new StringEntity(plainRequest));

                        HttpResponse response = httpclient.execute(httppost);
                        ResponseHandler<String> handler = new BasicResponseHandler();

                        responseCode = response.getStatusLine().getStatusCode();
                        if(responseCode != 200) {
                            //TODO now what?
                        }

                        resultContent = handler.handleResponse(response);

                    } else if (requestMethod.equals(HttpMethod.GET)){
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
                            requestURL.delete(requestURL.length() - 2, requestURL.length());
                        }

                        Log.d("requested URL", requestURL.toString());

                        HttpURLConnection urlConnection = (HttpURLConnection) new URL(requestURL.toString()).openConnection();
                        urlConnection.setRequestMethod(requestMethod.name());

                        //set auth header:
                        urlConnection.setRequestProperty("Authorization", "Bearer " + TokenHolder.getTokenObject().getAccessToken());
                        urlConnection.setRequestProperty("Content-Type", "application/json");
                        urlConnection.setRequestMethod(requestMethod.name());

                        responseCode = urlConnection.getResponseCode();

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        resultContent = IOUtils.toString(in, "UTF-8");

                    }
                } else {
                    //TODO: new token should be requested here
                    Log.d("AsyncAPIRequest", "Token is not valid");
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
            Log.d("content", resultContent);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            //TODO: if needed, use static singleton for passing retrieved data between activities
        }
    }
}

