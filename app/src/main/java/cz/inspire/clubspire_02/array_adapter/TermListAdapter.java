package cz.inspire.clubspire_02.array_adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cz.inspire.clubspire_02.R;
import cz.inspire.clubspire_02.list_items.ActivityItem;
import cz.inspire.clubspire_02.list_items.TermItem;


public class TermListAdapter extends ArrayAdapter<TermItem> {
    private List<TermItem> termList;
    private Context context;

    public TermListAdapter(Activity activity, List<TermItem> termList) {
        super(activity, R.layout.term_item, termList);
        this.termList = termList;
        this.context = activity.getApplicationContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Make sure we have a view to work with (may have been given null)
        View itemView = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.term_item, parent, false);
        }

        // Find the TermItem to work with.
        TermItem currentTerm = termList.get(position);

        //!!!!!!!!!!!!!!nefachá
        RelativeLayout termItemLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
        //Button termItemBackground = (Button) findViewById(R.id.item_background);
        TextView termAvailable = (TextView) itemView.findViewById(R.id.item_txtAvailable);

        // Day:
        TextView dayText = (TextView) itemView.findViewById(R.id.item_txtDay);
        dayText.setText(currentTerm.getDay().toString() + " " + currentTerm.getDateString());
        if(currentTerm.isAvailable()) {
            termItemLayout.setBackgroundColor(context.getResources().getColor(R.color.available_color));
            termAvailable.setText("volno");

        }
        else {
            termItemLayout.setBackgroundColor(context.getResources().getColor(R.color.unavailable_color));
            termAvailable.setText("obsazeno");

        }

        // Time:
        TextView timeText = (TextView) itemView.findViewById(R.id.item_txtTime);
        timeText.setText(currentTerm.getStartString() + " - " + currentTerm.getEndString());

        return itemView;
    }
}