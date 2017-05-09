package france.bosch.estelle.android_hotlemon.Fragments;


import android.app.Fragment;
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
import android.widget.TextView;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


import france.bosch.estelle.android_hotlemon.R;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.Class.Article;



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
            EditText Titre = (EditText) getView().findViewById(R.id.newArticle_Title);
            //EditText Category = (EditText) getView().findViewById(R.id.category) ;
           // EditText User = (EditText) getView().findViewById(R.id.user) ;
            EditText editDesc= (EditText) getView().findViewById(R.id.newArticle_Description);

            Article article = new Article(Titre.getText().toString(),editDesc.getText().toString(),selectedPlace);
            ((MainActivity)(getActivity())).addArticle(article);
            ((MainActivity)(getActivity())).switchFragment(new ArticleFragment());
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
        isGPSEnable();


       /* Intent intent = new Intent(((MainActivity)(getActivity())), MapsActivity.class);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);*/
    }

    public void isGPSEnable(){
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
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String placeName = String.format("%s", place.getName());
                //Toast.makeText(getActivity(), placeName, Toast.LENGTH_LONG).show();
                Location.setText(placeName);
                selectedPlace = place;
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


