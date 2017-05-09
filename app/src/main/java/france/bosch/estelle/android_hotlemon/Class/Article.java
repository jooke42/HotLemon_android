package france.bosch.estelle.android_hotlemon.Class;

import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceEntity;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Article {

    private int ID;
    private String title,  user, description, category, date;
    String UrlImage;
    Place location;
    int distance;

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

    public Article(){
        this.title = "";
        this.location = null;
        this.user = "";
    }

    public Article(String title, String user, Place location){
        this.title = title;
        this.user = user;
        this.location = location;
        //this.distance = location.getLatLng() - location.getLatLng();

    }

    public Article(String title, String user){
        this.title = title;
        this.user = user;
    //    this.location = Location.distanceBetween();

        //this.distance = location.getLatLng() - location.getLatLng();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Place getLocation() {
        return location;
    }

    public void setLocation(Place location) {
        this.location = location;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
