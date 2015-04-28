package cz.inspire.clubspire_02.list_items;

/**
 * Created by Vukmir on 25.4.2015.
 */
public class ReservationItem {
    private String category;
    private String content;

    public ReservationItem(String category, String content) {
        this.category = category;
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
