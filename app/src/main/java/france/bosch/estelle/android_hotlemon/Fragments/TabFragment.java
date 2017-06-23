package france.bosch.estelle.android_hotlemon.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import france.bosch.estelle.android_hotlemon.Adapter.ViewPagerAdapter;
import france.bosch.estelle.android_hotlemon.R;

/**
 * Created by ESTEL on 19/05/2017.
 */

public class TabFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    ViewPagerAdapter adapter;



    public TabFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

    private  void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new HotArticleFragment(), "HOT");
        adapter.addFragment(new ArticleFragment(), "News");
        adapter.addFragment(new MyArticleFragment(), "My Article");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout.setupWithViewPager(viewPager);

    }



}

