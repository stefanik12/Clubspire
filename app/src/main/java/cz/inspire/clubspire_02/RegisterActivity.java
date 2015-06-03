package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.inspire.clubspire_02.APIResources.AuthenticationHolder;
import cz.inspire.clubspire_02.APIResources.HttpMethod;
import cz.inspire.clubspire_02.POJO.NewUser;


public class RegisterActivity extends AbstractBaseActivity {

    private NewUser newUser = new NewUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        toolbarMenuPresent = false;
        super.onCreate(savedInstanceState);
        parentIntent = new Intent(getApplicationContext(), MainActivity.class);

        setContentView(R.layout.activity_register);

        setupActionBar();

        Button btnConfirmRegistration = (Button) findViewById(R.id.btn_confirm_register);
        btnConfirmRegistration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: passwordAgain and termsAccepted are not set corrently - modifications in UI will be required
                newUser.setName(((EditText) findViewById(R.id.textfield_name)).getText().toString())
                        .setSurname(((EditText) findViewById(R.id.textfield_surname)).getText().toString())
                        .setPassword(((EditText) findViewById(R.id.textfield_password)).getText().toString())
                        .setPasswordAgain(newUser.getPassword())
                        .setLogin(((EditText) findViewById(R.id.textfield_new_login)).getText().toString())
                        .setTermsAccepted(true)
                        .setEmail(((EditText) findViewById(R.id.textfield_email)).getText().toString());

                Gson gson = new Gson();
                String sentText = gson.toJson(newUser);

                Log.d("RegisterActivity", "serialized user: "+sentText);

                new LocalAsyncAPIRequestExtension().isUserRegistration(true).setPlainRequest(sentText).execute("/users", HttpMethod.POST);
            }
        });
    }

    private class LocalAsyncAPIRequestExtension extends AsyncAPIRequest {

        private ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarRegistration);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //make loader visible:
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void v) {
            progressBar.setVisibility(View.INVISIBLE);

            if (resultContent != null) {
                try {
                    JSONObject baseJSON = new JSONObject(resultContent);
                    int status = baseJSON.getJSONObject("message").getInt("httpStatus");

                    String userMessage = baseJSON.getJSONObject("message").getString("clientMessage");

                    if (status == 200) {
                        //initialize AuthHolder with credentials
                        AuthenticationHolder.setUsername(((EditText) findViewById(R.id.textfield_new_login)).getText().toString());
                        AuthenticationHolder.setPassword(((EditText) findViewById(R.id.textfield_password)).getText().toString());

                        //throw success message
                        Toast.makeText(getApplicationContext(), userMessage, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //next activity initialization
                    } else {
                        //unsuccessful registration on server - stay in RegisterActivity, throw a Toast
                        JSONArray errorParams;
                        try {
                            //should throw JSONException, if no fieldErrors found
                            errorParams = baseJSON.getJSONObject("message").getJSONArray("fieldErrors");

                            //invalid params section
                            StringBuilder appendedParams = new StringBuilder();
                            for (int i = 0; i < errorParams.length(); i++) {
                                appendedParams.append(errorParams.getJSONObject(i).getString("field"))
                                        .append(" : ")
                                        .append(errorParams.getJSONObject(i).getString("message"));

                                //Always write only one error message
                                Toast.makeText(getApplicationContext(), appendedParams.toString(), Toast.LENGTH_LONG).show();
                                break;
                            }

                        } catch (JSONException e2) {
                            Log.e("RegisterActivity", "all attributes correct but response code is: " + status);
                            e2.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    Log.e("Reservation03Activity:", "JSON parsing failed");
                    e.printStackTrace();
                }
            } else {
                Log.e("Reservation03Activity", "resultContent was null");
            }
        }
    }
}
