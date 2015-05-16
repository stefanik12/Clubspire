package cz.inspire.clubspire_02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by michal on 5/7/15.
 */
public class SpinnerAdapter {
        //extends ArrayAdapter<String> {
    /* not ready yet, subject of discussion

    private final ActivityObject context;
    private final String[] names;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public SpinnerAdapter(ActivityObject context, String[] names) {
        super(context, R.layout.activity_reservation_02, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.activity_reservation_02, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.text = (TextView) rowView.findViewById(R.id.TextView01);
//            viewHolder.image = (ImageView) rowView
//                    .findViewById(R.id.ImageView01);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = names[position];
        holder.text.setText(s);
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {
            holder.image.setImageResource(R.drawable.no);
        } else {
            holder.image.setImageResource(R.drawable.ok);
        }

        return rowView;
    }
    */
}
