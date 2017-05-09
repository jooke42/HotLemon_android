/*
package france.bosch.estelle.android_hotlemon.Class;
import france.bosch.estelle.android_hotlemon.MainActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Locale;




*/
/**
 * Created by ESTEL on 26/04/2017.
 *//*

import static android.content.ContentValues.TAG;

 class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
       // editLocation.setText("");
      //  pb.setVisibility(View.INVISIBLE);
        //Toast.makeText(
        //        getBaseContext(),
        //        "Location changed: Lat: " + loc.getLatitude() + " Lng: "
        //                + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, latitude);




*/
/*------- To get city name from coordinates -------- *//*


       */
/* String cityName = null;
        //Geocoder gcd = new Geocoder ( ((MainActivity)(getActivity())).getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;
        editLocation.setText(s);*//*



    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}

     public void getPosition()
     {
         LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

         Criteria critere = new Criteria();
         critere.setAccuracy(Criteria.ACCURACY_FINE);
         String name = locationManager.getBestProvider(critere, true);
         LocationProvider provider = locationManager.getProvider(name);

         if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED )
         {
             Location location = locationManager.getLastKnownLocation(provider.getName());
             Log.d("GPS", "Latitude " + location.getLatitude() + " et longitude " + location.getLongitude());
         }

     }

}
*/
