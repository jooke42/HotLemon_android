package france.bosch.estelle.android_hotlemon.Class;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Article {

    private int ID;
    private String title, location, user, description, category, date;
    String UrlImage;

    public Article() {
        this.title = "";
        this.location = "";
        this.user = "";
    }

    public Article(String title, String user, String location) {
        this.title = title;
        this.user = user;
        this.location = location;
    }

    public Article(String title, String user, String location, String description, String category, String date) {
        this.title = title;
        this.user = user;
        this.location = location;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrlimage() {
        return UrlImage;
    }

    public void setUrlimage(String urlimage) {
        UrlImage = urlimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
