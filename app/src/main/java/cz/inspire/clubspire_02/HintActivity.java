package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 5/3/15.
 */
public class HintActivity extends AbstractBaseActivity {
    private static final int SLIDE_STEPS = 3;
    Toolbar mToolbar;

    HelpAdapter helpAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        toolbarMenuPresent = true;
        parentIntent = new Intent(getApplicationContext(), MainMenuActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        setupActionBar();

        List<Fragment> fragments = getFragments();

        helpAdapter = new HelpAdapter(getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);

        pager.setAdapter(helpAdapter);

        //set actionbar button listener
        Toolbar buttonToolbar = (Toolbar) findViewById(R.id.toolbar);
        buttonToolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<>();

        for(int i=0;i<SLIDE_STEPS;i++){
            fList.add(HintFragment.newInstance(i));
        }

        return fList;
    }


}

