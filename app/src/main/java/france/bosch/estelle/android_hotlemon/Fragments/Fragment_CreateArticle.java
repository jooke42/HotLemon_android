package france.bosch.estelle.android_hotlemon.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import france.bosch.estelle.android_hotlemon.MapsActivity;
import france.bosch.estelle.android_hotlemon.R;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.Class.Article;
import france.bosch.estelle.android_hotlemon.Helper.DateUtility;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Fragment_CreateArticle extends Fragment{
    private Button add_bt;
    private Button location;
    private Button selectImage;
        public Fragment_CreateArticle() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
           View root = inflater.inflate(R.layout.fragment_ajouter_article, container, false);
            add_bt = (Button) root.findViewById(R.id.poster_article);
            location = (Button) root.findViewById(R.id.change_location);
            selectImage = (Button) root.findViewById(R.id.selectimage);

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
            EditText Titre = (EditText) getView().findViewById(R.id.newArticle_Title) ;
            // EditText Category = (EditText) getView().findViewById(R.id.category) ;
            // EditText User = (EditText) getView().findViewById(R.id.user) ;
            EditText editDesc = (EditText) getView().findViewById(R.id.newArticle_Description) ;
            EditText Location = (EditText) getView().findViewById(R.id.newArticle_Location);
            Article article = new Article(Titre.getText().toString(),editDesc.getText().toString(), DateUtility.getDate(System.currentTimeMillis(), "EEEE dd MMM yyyy"));
            ((MainActivity)(getActivity())).addArticle(article);
            ((MainActivity)(getActivity())).switchFragment(new ArticleFragment());
        }

    private void setLocation(){
        Intent intent = new Intent(((MainActivity)(getActivity())), MapsActivity.class);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

       /* Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/

    }

    private void Select_OnClick()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }
}


