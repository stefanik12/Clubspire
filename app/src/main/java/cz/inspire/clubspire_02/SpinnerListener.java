package cz.inspire.clubspire_02;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by michal on 5/6/15.
 */
public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        //Toast.makeText(parent.getContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.setSelection(1);
    }


}
