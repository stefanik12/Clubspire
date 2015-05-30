package cz.inspire.clubspire_02.list_items;

import android.text.format.Time;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vukmir on 25.4.2015.
 */
public class TermItem {
    private Date date;
    private Day day;
    private Time start;
    private Time end;
    private int weekNumber;
    private boolean available;



    private Date startTime;
    private Date endTime;
    private String sportId;

    //SimpleDateFormat sdf = new SimpleDateFormat("dd'.'MM'.'");
    //Calendar cal ;

    //activityId
    //sportId
    //startTime
    //endTime

    public TermItem(){

    };

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getSportId() {
        return sportId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        //return sdf.format(cal.getTime());
        return date.getDate() + "." + date.getMonth() + ".";
    }

    public Day getDay() {
        return day;
    }



    public void setDay(Day day) {
        this.day = day;
    }

    public Time getStart() {
        return start;
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

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
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

    public void setEnd(Time end) {
        this.end = end;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

