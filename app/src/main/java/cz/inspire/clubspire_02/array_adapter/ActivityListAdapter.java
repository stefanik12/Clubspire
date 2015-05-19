package cz.inspire.clubspire_02.array_adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import cz.inspire.clubspire_02.R;
import cz.inspire.clubspire_02.list_items.ActivityItem;


public class ActivityListAdapter extends ArrayAdapter<ActivityItem> {
    private List<ActivityItem> activityList;
    private Context context;

    public ActivityListAdapter(Activity activity, List<ActivityItem> activityList) {
        super(activity, R.layout.activity_item, activityList);
        this.activityList = activityList;
        this.context = activity.getApplicationContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Make sure we have a view to work with (may have been given null)
        View itemView = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.activity_item, parent, false);
        }

        // Find the ActivityItem to work with.
        ActivityItem currentActivity = activityList.get(position);

        // Fill the view
        ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
        imageView.setImageResource(currentActivity.getIconID());

        // Condition:
        TextView condionText = (TextView) itemView.findViewById(R.id.item_txtName);
        condionText.setText(currentActivity.getName());

        return itemView;
    }

}