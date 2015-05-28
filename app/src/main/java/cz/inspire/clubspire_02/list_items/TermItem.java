package cz.inspire.clubspire_02.list_items;

import android.text.format.Time;

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
    //SimpleDateFormat sdf = new SimpleDateFormat("dd'.'MM'.'");
    //Calendar cal ;

    public TermItem(){

    };

    public TermItem(Date date, Day day, Time start, Time end, int weekNumber, boolean available) {
        this.date = date;
        this.day = day;
        this.start = start;
        this.end = end;
        this.weekNumber = weekNumber;
        this.available = available;

        /*
        cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.WEEK_OF_MONTH,weekNumber);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        */
    }

    /*
    was just for manual determining of date..soon to be deleted

    public void setCalendar(int weekNumber, int yearNumber){
        cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, yearNumber);
        cal.set(Calendar.WEEK_OF_MONTH,weekNumber);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
    }
    public void setCalendarDay(int dayOfWeek){
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    }

    */
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
        if(start.minute == 0)
            return start.hour + ":00" ;
        else
            return start.hour + ":" + start.minute;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public String getEndString() {
        if(end.minute == 0)
            return end.hour + ":00" ;
        else
            return end.hour + ":" + end.minute;
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

