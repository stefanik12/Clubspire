package cz.inspire.clubspire_02.list_items;

public class ActivityItem {
    private int iconID;
    private String name;

    public ActivityItem(){}



    public ActivityItem(int iconID, String name) {
        super();
        this.iconID = iconID;
        this.name = name;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconID() {
        return iconID;
    }

    public String getName() {
        return name;
    }
}
