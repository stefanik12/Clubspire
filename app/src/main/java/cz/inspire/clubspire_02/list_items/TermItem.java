package cz.inspire.clubspire_02.list_items;

import android.text.format.Time;

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

    public TermItem(Date date, Day day, Time start, Time end, int weekNumber, boolean available) {
        this.date = date;
        this.day = day;
        this.start = start;
        this.end = end;
        this.weekNumber = weekNumber;
        this.available = available;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return date.getDay() + "." + date.getMonth();
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
        return start.hour + ":" + start.minute;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public String getEndString() {
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

