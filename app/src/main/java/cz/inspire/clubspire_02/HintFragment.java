package cz.inspire.clubspire_02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by michal on 5/3/15.
 */
public class HintFragment extends Fragment {
    public static final int SLIDE_NUMBER = 0;


    public static HintFragment newInstance(int slide)

    {

        HintFragment f = new HintFragment();

        Bundle bdl = new Bundle(1);

        bdl.putInt("SLIDE_NUMBER", slide);

        f.setArguments(bdl);

        return f;

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        View v;
        int slide = getArguments().getInt("SLIDE_NUMBER");
        switch (slide){
            case 0:
                v = inflater.inflate(R.layout.fragment_hint01, container, false);
                break;
            case 1:
                v = inflater.inflate(R.layout.fragment_hint02, container, false);
                break;
            case 2:
                v = inflater.inflate(R.layout.fragment_hint03, container, false);
                break;
            default:
                throw new IllegalArgumentException("Slide number must be between 0 and 2 inclusively");
        }

        
        return v;

    }
}
