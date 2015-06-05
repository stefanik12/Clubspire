package cz.inspire.clubspire_02;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal on 5/3/15.
 */
public class HintActivity extends AbstractBaseActivity {
    private static final int SLIDE_STEPS = 9;
    Toolbar mToolbar;
    private TextView hintText;

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

        hintText = (TextView) findViewById(R.id.hint_text);

        //pager.setOnPageChangeListener(new PageListener());

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hintText.setText(position + "!");
                switch (position){
                    case 0 :
                        hintText.setText(getResources().getString(R.string.hint00));
                        break;
                    case 1 :
                        hintText.setText(getResources().getString(R.string.hint01));
                        break;
                    case 2 :
                        hintText.setText(getResources().getString(R.string.hint02));
                        break;
                    case 3 :
                        hintText.setText(getResources().getString(R.string.hint03));
                        break;
                    case 4 :
                        hintText.setText(getResources().getString(R.string.hint04));
                        break;
                    case 5 :
                        hintText.setText(getResources().getString(R.string.hint05));
                        break;
                    case 6 :
                        hintText.setText(getResources().getString(R.string.hint06));
                        break;
                    case 7 :
                        hintText.setText(getResources().getString(R.string.hint07));
                        break;
                    case 8 :
                        hintText.setText(getResources().getString(R.string.hint08));
                        break;
                    default:
                        hintText.setText(getResources().getString(R.string.hint00));
                        break;




                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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

