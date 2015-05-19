package cz.inspire.clubspire_02.list_items;

public class ActivityItem {
    private int iconID;
    private String id;
    private String description;
    private String name;

    public ActivityItem(){}

    public ActivityItem(int iconID, String name) {
        super();
        this.iconID = iconID;
        this.name = name;
    }

    public int getIconID() {
        return iconID;
    }

    public ActivityItem setIconID(int iconID) {
        this.iconID = iconID;
        return this;
    }

    public String getId() {
        return id;
    }

    public ActivityItem setId(String id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ActivityItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public ActivityItem setName(String name) {
        this.name = name;
        return this;
    }
}
