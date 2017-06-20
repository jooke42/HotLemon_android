package france.bosch.estelle.android_hotlemon.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.LoginActivity;
import france.bosch.estelle.android_hotlemon.R;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.Class.News;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Fragment_CreateArticle extends Fragment {

    public interface CreateArticleListener {
        void onSelectImage();
        void onLocationChanged();
        void onAddArticle();
    }

    // Global variables used for HTTP requests
    private static final String TAG = MainActivity.class.getSimpleName();
    private String URL_FEED = "https://perso.esiee.fr/~pereirae/webe3fi/test.json"; // Deprecated
    private String URL_FEED_POST = "http://82.232.20.224/news/"; // Marteaux's RaspberryPI URL
    private int PLACE_PICKER_REQUEST = 1;
    private Bitmap toretBitmap;
    // Internal variables
    private CreateArticleListener listener;
    private Button add_bt;

    private Button selectImage;
    private ImageView selectedImage;
    private TextView Location;
    private Place selectedPlace;

    public Fragment_CreateArticle() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ajouter_article, container, false);
        add_bt = (Button) root.findViewById(R.id.poster_article);

        selectImage = (Button) root.findViewById(R.id.selectimage);
        Location = (TextView) root.findViewById(R.id.newArticle_Location);
        return root;
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_OnClick();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_OnClick();
            }
        });
    }

    public void isGPSEnabled(){
        LocationManager service = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_PICKER_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                selectedImage = (ImageView) getView().findViewById(R.id.imageView2);
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Place place = PlacePicker.getPlace(getActivity(), data);
            String placeName = String.format("%s", place.getName());
            // Toast.makeText(getActivity(), placeName, Toast.LENGTH_LONG).show();
            Location.setText(placeName);
            selectedPlace = place;
        }

        if (resultCode == RESULT_OK){
            try {
                InputStream IS = getContext().getContentResolver().openInputStream(data.getData());
                toretBitmap = BitmapFactory.decodeStream(IS);
                ImageView imageView = (ImageView) getView().findViewById(R.id.imageView2);
                imageView.setImageBitmap(toretBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void Add_OnClick()
    {
        // get posting date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        // fetch data from the application
        EditText titre = (EditText) getView().findViewById(R.id.newArticle_Title);
        EditText editDesc = (EditText) getView().findViewById(R.id.newArticle_Description);
        ImageView selectedImage = (ImageView) getView().findViewById(R.id.imageView2);

        News news = new News();
        news.setTitle(titre.getText().toString());
       // news.setLocation(selectedPlace);
        news.setAuthor(AppController.getInstance().getUserLogin());
        news.setBody(editDesc.getText().toString());
        news.setCreatedDate(formattedDate);
        if(selectedImage != null && selectedImage.getDrawable() != null)
            news.setImage(((BitmapDrawable)selectedImage.getDrawable()).getBitmap());

        // add news to local array then
        ((MainActivity)(getActivity())).addArticle(news);

        // create HashMap to feed a JSONObject
        HashMap<String, Object> params = new HashMap<>();
        params.put("title", news.getTitle());
       // params.put("location", news.getRawLocation());
        params.put("author", news.getAuthor());
        params.put("body", news.getBody());
        //params.put("category", news.getCategory());
        //params.put("date", news.getCreatedDate());
        params.put("picture", news.getImage());

        // try to send a POST HTTP Request with previous JSONObject in parameter
        JsonObjectRequest req = new JsonObjectRequest(URL_FEED_POST, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getStackTrace());
            }
        })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token " + AppController.getInstance().getKeyToken());
                return headers;
            }
        };

        // add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);

        // finally, switch fragment to go back to the main view of the UI
        ((MainActivity)(getActivity())).switchFragment(new TabFragment());
    }

    private void setLocation()  {
        PlacePicker.IntentBuilder placePicker = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(placePicker.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        isGPSEnabled();
    }

    private void Select_OnClick()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CreateArticleListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement CreateArticleListener");
        }
    }
}