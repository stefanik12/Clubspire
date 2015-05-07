package cz.inspire.clubspire_02;

public class SpinnerItem {

    //TODO: will be implemented
    private final String prefix = "tyden ";
    private int weekNum;

    public SpinnerItem(int weekNum){
        this.weekNum = weekNum;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    @Override
    public String toString(){
        return prefix+weekNum;
    }
}
