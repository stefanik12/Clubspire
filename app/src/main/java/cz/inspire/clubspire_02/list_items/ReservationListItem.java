package cz.inspire.clubspire_02.list_items;

import android.text.format.Time;

import java.util.Date;

/**
 * Created by Vukmir on 27.4.2015.
 */
public class ReservationListItem {
    private int iconId;
    private String activityName;
    private Date date;
    private Day day;
    private Time start;
    private Time end;

    public ReservationListItem(int iconId, String activityName, Date date, Day day, Time start, Time end) {
        this.iconId = iconId;
        this.activityName = activityName;
        this.date = date;
        this.day = day;
        this.start = start;
        this.end = end;
    }



    public String getDateString() {
        return date.getDay() + "." + date.getMonth() + ".";
    }

    public String getStartString() {
        return start.hour + ":" + start.minute;
    }

    public String getEndString() {
        return end.hour + ":" + end.minute;
    }

    public int getIconId() {
        return iconId;
    }

    public String getActivityName() {
        return activityName;
    }

    public Date getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }
}
