package cz.inspire.clubspire_02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_hint, container, false);
        int slide = getArguments().getInt("SLIDE_NUMBER");
        //TextView hintText = (TextView) getView().findViewById(R.id.hint_text);

        /*
        TextView hintText = (TextView) view.findViewById(R.id.hint_text);
        hintText.setText("Nová NNN");
        hintText.setHeight(200);
        */


        switch (slide){
            case 0:
                view = inflater.inflate(R.layout.fragment_hint00, container, false);
                break;
            case 1:
                view = inflater.inflate(R.layout.fragment_hint01, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_hint02, container, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_hint03, container, false);
                break;
            case 4:
                view = inflater.inflate(R.layout.fragment_hint04, container, false);
                break;
            case 5:
                view = inflater.inflate(R.layout.fragment_hint05, container, false);
                break;
            case 6:
                view = inflater.inflate(R.layout.fragment_hint06, container, false);
                break;
            case 7:
                view = inflater.inflate(R.layout.fragment_hint07, container, false);
                break;
            case 8:
                view = inflater.inflate(R.layout.fragment_hint08, container, false);
                break;
            default:
                throw new IllegalArgumentException("Slide number must be between 0 and 8 inclusively");
        }

        
        return view;

    }
}
