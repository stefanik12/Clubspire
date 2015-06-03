package cz.inspire.clubspire_02.list_items;

import android.widget.ImageView;

public class ActivityItem {
    private int iconID;
    private String id;
    private String description;
    private String name;
    private ImageView icon;
    private String iconUrl;

    public ActivityItem(){}

    public ActivityItem(int iconID, String name) {
        super();
        this.iconID = iconID;
        this.name = name;
    }

    public ActivityItem setIcon(ImageView icon) {
        this.icon = icon;
        return this;
    }

    public ImageView getIcon(){
        return icon;
    }

    public ActivityItem setIconUrl(String url){
        iconUrl = url;
        return this;
    }
    public String getIconUrl(){
        return iconUrl;
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
