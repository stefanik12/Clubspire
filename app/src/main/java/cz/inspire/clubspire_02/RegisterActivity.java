package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v7.widget.Toolbar;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class RegisterActivity extends AbstractBaseActivity {

    private Toolbar mToolbar;

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
                Toast.makeText(getApplicationContext(), "Registrace hotová", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }





}
