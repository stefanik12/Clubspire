package cz.inspire.clubspire_02.list_items;

import android.text.format.Time;
import android.util.Log;

import java.util.Date;

/**
 * Created by Vukmir on 27.4.2015.
 */
public class ReservationListItem {
    private String id;
    private int iconId;
    private String activityName;
    private Date startDate;
    private Day day;
    private Time start;
    private Time end;
    private String activityId;
    private String iconUrl;

    public String getId() {
        return id;
    }

    public ReservationListItem setId(String id) {
        this.id = id;
        return this;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public ReservationListItem setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public String getActivityId() {
        return activityId;
    }

    public ReservationListItem setActivityId(String activityId) {
        this.activityId = activityId;
        return this;
    }

    public ReservationListItem setIconId(int iconId) {
        this.iconId = iconId;
        return this;
    }

    public ReservationListItem setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public ReservationListItem setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public ReservationListItem setDay(Day day) {
        this.day = day;
        return this;
    }

    public ReservationListItem setStart(Time start) {
        this.start = start;
        return this;
    }

    public ReservationListItem setEnd(Time end) {
        this.end = end;
        return this;
    }

    public String getDateString() {
        return startDate.getDate() + "." + (startDate.getMonth()+1) + ".";
    }

    public String getStartString() {
        try {
            if (start.minute == 0)
                return start.hour + ":00";
            else
                return start.hour + ":" + start.minute;
        }catch (NullPointerException e){
            Log.d("start", "start hour is null");
            return "XX:XX";
        }
    }

    public String getEndString() {
        try {
            if (end.minute == 0)
                return end.hour + ":00";
            else
                return end.hour + ":" + end.minute;
        }catch (NullPointerException e){
            Log.d("end", "end hour is null");
            return "YY:YY";
        }
    }

    public int getIconId() {
        return iconId;
    }

    public String getActivityName() {
        return activityName;
    }

    public Date getStartDate() {
        return startDate;
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

    public String getFormedDateTime(){
        StringBuilder builder = new StringBuilder();
        builder.append(day.toString()).append(" ").append(getDateString()).append("  (").append(getStartString()).append("-").append(getEndString()).append(")");
        return builder.toString();
    }
}
