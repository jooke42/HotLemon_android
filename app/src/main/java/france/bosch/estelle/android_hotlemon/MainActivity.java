package france.bosch.estelle.android_hotlemon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.Dialog.ChooseTypeDialog;
import france.bosch.estelle.android_hotlemon.Fragments.ArticleDetailFragment;
import france.bosch.estelle.android_hotlemon.Fragments.ArticleFragment;
import france.bosch.estelle.android_hotlemon.Fragments.EditProfilFragment;
import france.bosch.estelle.android_hotlemon.Fragments.Fragment_CreateArticle;
import france.bosch.estelle.android_hotlemon.Fragments.Fragment_CreateEvent;
import france.bosch.estelle.android_hotlemon.Fragments.HotArticleFragment;
import france.bosch.estelle.android_hotlemon.Fragments.EventFragment;
import france.bosch.estelle.android_hotlemon.Fragments.TabFragment;
import france.bosch.estelle.android_hotlemon.Helper.FragmentUtils;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ArticleFragment.ArticleFragmentListener,
        FragmentUtils.ActivityForResultStarter,
        HotArticleFragment.HotArticleFragmentListener,
        ChooseTypeDialog.ChooseTypeListener,
        EventFragment.NewsFragmentListener
{
    private SparseArray<Bundle> requests;
    private RelativeLayout framelayout;
    private Topic currentNews;
    private ArrayList<Topic> newsList = new ArrayList<Topic>();
    private ArrayList<Topic> HotList = new ArrayList<Topic>();

    private ArrayList<Topic> eventList = new ArrayList<Topic>();


    public ArrayList<Topic> getNews() {
        return this.newsList;
    }
    public ArrayList<Topic> getEvent() {
        return this.eventList;
    }

    public ArrayList<Topic> getHot() {

        return this.HotList;
    }

    public void addHotList(Topic t){
                HotList.add(t);
    }






    public Topic getCurrentNews() {
        return currentNews;
    }

    public void setCurrentNews(Topic currentNews) {
        this.currentNews = currentNews;
    }

    public void addNews(Topic news){
      //  if(!newsList.contains(news))
                newsList.add(news);

    }
    public void addEvent(Topic news){
        // if(!eventList.contains(news))
        eventList.add(news);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start the Login Activity to log the current user
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        if (savedInstanceState == null)
            this.requests = new SparseArray<Bundle>();
        else
            this.requests = savedInstanceState.getSparseParcelableArray("requests");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        framelayout = (RelativeLayout) findViewById(R.id.content_main);
        TabFragment fr = new TabFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_main, fr, "tag");
        transaction.commit();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSparseParcelableArray("requests", this.requests);
    }

    @Override
    public void startActivityForResultWhileSavingOrigin(int requestCode, Intent intent, int[] indices)
    {
        //special method to start an activity for result.

        //we save the information about where this request comes from in a bundle and store it based on requestCode
        final Bundle bundle = new Bundle();
        bundle.putInt("code", requestCode);
        bundle.putParcelable("intent", intent);
        bundle.putIntArray("indices", indices);

        this.requests.put(requestCode, bundle);
        this.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            switchFragment(new TabFragment());

        } else if (id == R.id.nav_slideshow) {
            switchFragment(new EditProfilFragment());

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showChooseTypeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ChooseTypeDialog chooseTypeDialog = ChooseTypeDialog.newInstance("Choose your type of article.");
        chooseTypeDialog.show(fm, "dialog_choose_type");
    }

    @Override
    public void onArticleClick(Topic news){
        setCurrentNews(news);
        switchFragment(new ArticleDetailFragment());
    }



    public void isGPSEnable(){
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // first check if we saved any data about the origin of this request (which fragment)
        final Bundle request = this.requests.get(requestCode, null);

        if (request != null)
        {
            // find the indices-array
            final int[] indices = request.getIntArray("indices");

            FragmentManager fm = this.getSupportFragmentManager();
            Fragment f = null;

            // loop backwards
            for(int i = indices.length - 1 ; i >= 0 ; i--)
            {
                if (fm != null)
                {
                    //find a list of active fragments
                    List<Fragment> flist = fm.getFragments();
                    if (flist != null &&  indices[i] < flist.size())
                    {
                        f = flist.get(indices[i]);
                        fm = f.getChildFragmentManager();
                    }
                }
            }

            // we found our fragment that initiated the request to startActivityForResult, give it the callback!
            if (f != null)
            {
                f.onActivityResult(requestCode, resultCode, data);
                return ;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onChooseValidation(String type){
        if(type=="article")
            switchFragment(new Fragment_CreateArticle());
        if(type=="event")
            switchFragment(new Fragment_CreateEvent());
    }
}
