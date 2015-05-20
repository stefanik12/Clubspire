package cz.inspire.clubspire_02;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SpinnerItem {


    private final String prefix = "Tyden";
    private int weekNum;
    private Date from;
    private Date to;

    public SpinnerItem(int weekNum){
        this.weekNum = weekNum;
        this.from = new Date(666,6,6);
        this.to = new Date(666,6,6);
    }

    public SpinnerItem(int weekNum, Date from, Date to){
        this.weekNum = weekNum;
        this.from = from;
        this.to = to;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }


    public String getFromText(){
        return from.getDate() + "." + from.getMonth() + ".";
    }

    public String getToText(){
        return to.getDate() + "." + to.getMonth() + ".";
    }


    @Override
    public String toString(){
        return prefix + " " + weekNum + "    (" + getFromText() + " - " + getToText() + ")";
    }
}
