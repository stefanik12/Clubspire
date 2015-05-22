package cz.inspire.clubspire_02;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.inspire.clubspire_02.APIResources.RESTconfiq;
import cz.inspire.clubspire_02.APIResources.TokenHolder;

/**
 * Created by michal on 5/3/15.
 */
public abstract class AbstractBaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private String suffix;

    protected StringBuffer resultContent = new StringBuffer();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    protected void setupActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.left_arrow_white);

        mToolbar.setOnClickListener(toolbarListener);
        mToolbar.setNavigationOnClickListener(toolbarListener);
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

    //API network services:
    protected class AsyncAPIRequest extends AsyncTask<Void, Void, Void> {


        protected void execute(String s){
            suffix = s;
            execute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ResponseHandler<String> handler = new BasicResponseHandler();
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(RESTconfiq.BASE_URL + suffix);
            Log.d("HttpGet address: ", RESTconfiq.BASE_URL + suffix);

            // add request header
            request.addHeader("Authorization", "Bearer " + TokenHolder.getTokenObject().getAccessToken());

            HttpResponse response = null;
            try {
                response = httpclient.execute(request);


                Log.d("Response Code : ", String.valueOf(response.getStatusLine().getStatusCode()));

                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    resultContent.append(line);
                }
                Log.d("content", resultContent.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            //TODO: if needed, use static singleton for passing retrieved data between activities
        }
    }
}

