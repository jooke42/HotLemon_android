package france.bosch.estelle.android_hotlemon.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Calendar;

import france.bosch.estelle.android_hotlemon.Adapter.Article_Item_Adapter;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.R;
import france.bosch.estelle.android_hotlemon.Class.Article;

public class ArticleDetailFragment extends Fragment {



    private TextView Date;
    private TextView Location;
    private TextView User;
    private TextView Category;
    private TextView Title;

    public ArticleDetailFragment() {

    }

    public static ArticleDetailFragment newInstance(Article article) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_article_detail, container, false);

        Date = (TextView) root.findViewById(R.id.date);
        Location = (TextView) root.findViewById(R.id.distance);
        User = (TextView) root.findViewById(R.id.user);
        Category = (TextView) root.findViewById(R.id.category);
        Title = (TextView) root.findViewById(R.id.title);

        setData();

        return root;

    }

    private void setData(){
        Article currentArcticle = ((MainActivity)(getActivity())).getCurrentArticle();
        Date.setText(currentArcticle.getDate());
        Location.setText(currentArcticle.getLocation().getName());
        User.setText(currentArcticle.getUser());
        Category.setText(currentArcticle.getCategory());



    }






}
