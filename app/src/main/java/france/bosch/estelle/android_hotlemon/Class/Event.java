package france.bosch.estelle.android_hotlemon.Class;


import com.google.android.gms.location.places.Place;

/**
 * Created by ESTEL on 09/06/2017.
 */

public class Event extends Topic {
Place location;
    public Event(String title, String user, Place location){
        this.title = title;
        this.Author = user;
        this.location = location;
        //this.distance = location.getLatLng() - location.getLatLng();
    }

}
