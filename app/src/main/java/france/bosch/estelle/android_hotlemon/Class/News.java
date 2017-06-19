package france.bosch.estelle.android_hotlemon.Class;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class News extends Topic{

    private int ID;

    String UrlImage;
    Place location;
    Bitmap image;





    public String getUrlimage() {
        return UrlImage;
    }

    public void setUrlimage(String urlimage) {
        UrlImage = urlimage;
    }

    public Bitmap getImage() { return image; }

    public void setImage(Bitmap image) { this.image = image; }

    public News(){

        this.location = null;

    }

    public News(String title, String user, Place location){
       this.title = title;
        this.Author = user;
        this.location = location;
        //this.distance = location.getLatLng() - location.getLatLng();
    }

    public News(String title, String user, String location){
        this.title = title;
        this.Author = user;

    }







}