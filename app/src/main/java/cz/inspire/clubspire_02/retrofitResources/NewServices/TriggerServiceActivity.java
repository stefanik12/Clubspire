package cz.inspire.clubspire_02.retrofitResources.NewServices;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.inspire.clubspire_02.AbstractReservationActivity;
import cz.inspire.clubspire_02.retrofitResources.TokenHolder;

/**
 * Created by michal on 5/17/15.
 */
public abstract class TriggerServiceActivity extends AbstractReservationActivity {
    public static final String BASE_URL = "http://netspire.net:6868";
    private String suffix = "";
    private Intent intent;

    //no constructor

    public void initializeTrigger(String suffix, Intent intent) {
        this.suffix = suffix;
        this.intent = intent;

        Log.w("TriggerServiceActivity ","New network trigger initialized");
    }

    public void ContinueOnLoad() {
        new AsyncAuthentization().execute();
    }


    private class AsyncAuthentization extends AsyncTask<Void, Void, Void> {
        private StringBuffer resultContent = new StringBuffer();

        @Override
        protected Void doInBackground(Void... voids) {

            ResponseHandler<String> handler = new BasicResponseHandler();
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(BASE_URL + suffix);
            Log.d("HttpGet address: ", BASE_URL + suffix);

            // add request header
            request.addHeader("Authorization", "Bearer " + TokenHolder.getTokenObject().getAccessToken());
            HttpResponse response = null;
            try {
                response = httpclient.execute(request);


                Log.d("Response Code : ", String.valueOf(response.getStatusLine().getStatusCode()));

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    resultContent.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            intent.putExtra("Reservation01Activity", resultContent.toString());

            startActivity(intent);

        }
    }


}
