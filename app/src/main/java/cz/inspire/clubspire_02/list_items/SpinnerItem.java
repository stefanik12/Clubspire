package cz.inspire.clubspire_02.list_items;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.inspire.clubspire_02.App;
import cz.inspire.clubspire_02.R;
import cz.inspire.clubspire_02.array_adapter.ActivityListAdapter;

public class SpinnerItem{


    private final String prefix = App.getContext().getResources().getString(R.string.weekString);

    private int weekNum;
    private LocalDate from;
    private LocalDate to;

    public SpinnerItem(int weekNum){
        this.weekNum = weekNum;
    }

    public SpinnerItem(int weekNum, LocalDate from, LocalDate to){
        this.weekNum = weekNum;
        this.from = from;
        this.to = to;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public String getFromText(){
        return from.getDayOfMonth() + "." + from.getMonthOfYear() + ".";
    }

    public String getToText(){
        return to.getDayOfMonth() + "." + to.getMonthOfYear() + ".";
    }


    @Override
    public String toString(){
        return prefix + " " + weekNum + "    (" + getFromText() + " - " + getToText() + ")";
    }
}
