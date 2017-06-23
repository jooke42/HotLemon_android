package france.bosch.estelle.android_hotlemon.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Fragment_CreateArticle extends Fragment{
    private Button add_bt;
    private FloatingActionButton location;
    private Button selectImage;
    private TextView Location;
    private int PLACE_PICKER_REQUEST = 1;
    private Place selectedPlace;
    public Bitmap toretBitmap;
    private static final String TAG = "Fragment_CreateArticle";
    private static final String URL_FEED_POST = "http://82.232.20.224/topics";


    public Fragment_CreateArticle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ajouter_article, container, false);
        add_bt = (Button) root.findViewById(R.id.poster_article);
        location = (FloatingActionButton) root.findViewById(R.id.change_location);
        selectImage = (Button) root.findViewById(R.id.selectimage);
        Location = (TextView) root.findViewById(R.id.newArticle_Location);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_OnClick();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation();
            }
        });
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_OnClick();
            }
        });

    }

    private void Add_OnClick()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending post to server...");
        progressDialog.show();

        EditText Titre = (EditText) getView().findViewById(R.id.newArticle_Title);
        //EditText Category = (EditText) getView().findViewById(R.id.category) ;
        // EditText User = (EditText) getView().findViewById(R.id.user) ;
        EditText editDesc = (EditText) getView().findViewById(R.id.newArticle_Description);
        ImageView selectedImage = (ImageView) getView().findViewById(R.id.imageView2);

        //Topic article = new Topic(Titre.getText().toString(), "http://82.232.20.224/users/2/", editDesc.getText().toString(), ((BitmapDrawable)selectedImage.getDrawable()).getBitmap());
        Topic article = new Topic();
        article.setTitle(Titre.getText().toString());
        article.setAuthor("http://82.232.20.224/users/2/");
        article.setBody(editDesc.getText().toString());

        if (toretBitmap != null)
            article.setImage(((BitmapDrawable)selectedImage.getDrawable()).getBitmap());

        ((MainActivity)(getActivity())).addNews(article);
        HashMap<String, Object> params = new HashMap<>();
        params.put("title", article.getTitle());
        params.put("author", article.getAuthor());
        params.put("body", article.getBody());
        params.put("picture", article.getImage());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL_FEED_POST,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //key = response.getString("key");
                    //userLogin = user.getUsername();
                    //AppController.getInstance().setUserLogin(userLogin);
                    //AppController.getInstance().setKeyToken(key);
                    VolleyLog.v("Response:%n %s", response.toString(4));
                    Log.v(TAG, "Response: " + response.toString());

                    //onLoginSuccess();

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Log.e("Error: ", ""+ error.getMessage());
                //onLoginFailed();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token " + AppController.getInstance().getKeyToken());
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(req, "topics post");

        ((MainActivity)(getActivity())).switchFragment(new TabFragment());
    }

   /* private void Add_OnClick()
    {
        EditText Titre = (EditText) getView().findViewById(R.id.newArticle_Title);
        //EditText Category = (EditText) getView().findViewById(R.id.category) ;
        // EditText User = (EditText) getView().findViewById(R.id.user) ;
        EditText editDesc= (EditText) getView().findViewById(R.id.newArticle_Description);
        Topic article = new Topic(Titre.getText().toString(),editDesc.getText().toString(),selectedPlace);

        ((MainActivity)(getActivity())).addEvent(article);
        ((MainActivity)(getActivity())).switchFragment( new TabFragment());
    }
*/
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

    public String convert_bitmap_to_string(Bitmap bitmap)
    {
        ByteArrayOutputStream full_stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, full_stream);
        byte[] full_bytes = full_stream.toByteArray();
        String Str_image = Base64.encodeToString(full_bytes, Base64.DEFAULT);

        return Str_image;
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            PlacePicker.getLatLngBounds(data);

            Place place = PlacePicker.getPlace(data, getActivity());
            String placeName = String.format("%s", place.getName());
            //Toast.makeText(getActivity(), placeName, Toast.LENGTH_LONG).show();
            Location.setText(placeName);
            selectedPlace = place;
        }
        else {
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    InputStream IS = getContext().getContentResolver().openInputStream(data.getData());
                    Bitmap b = BitmapFactory.decodeStream(IS);
                    toretBitmap = b;
                    ImageView imageView = (ImageView) getView().findViewById(R.id.imageView2);
                    imageView.setImageBitmap(toretBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void Select_OnClick()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);


    }
}