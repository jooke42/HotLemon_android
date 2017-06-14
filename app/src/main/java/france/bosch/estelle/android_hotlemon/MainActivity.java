package france.bosch.estelle.android_hotlemon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.location.Location;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.Class.Article;

import france.bosch.estelle.android_hotlemon.Fragments.ArticleDetailFragment;
import france.bosch.estelle.android_hotlemon.Fragments.ArticleFragment;
import france.bosch.estelle.android_hotlemon.Fragments.EditProfilFragment;
import france.bosch.estelle.android_hotlemon.Fragments.Fragment_CreateArticle;
import france.bosch.estelle.android_hotlemon.Helper.FragmentUtils;
import france.bosch.estelle.android_hotlemon.Helper.PlaceActivityUtility;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ArticleFragment.ArticleFragmentListener,
        Fragment_CreateArticle.CreateArticleListener,
        FragmentUtils.ActivityForResultStarter
{
    private SparseArray<Bundle> requests;
    private int PLACE_PICKER_REQUEST = 1;
    private RelativeLayout framelayout;
    private Article currentArticle;
    private ArrayList<Article> articles = new ArrayList<Article>();

    public ArrayList<Article> getArticles() {
        return this.articles;
    }

    public Article getCurrentArticle() {
        return currentArticle;
    }

    public void setCurrentArticle(Article currentArticle) {
        this.currentArticle = currentArticle;
    }

    public void addArticle(Article article){
        articles.add(article);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start the Login Activity to log the current user
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        if (savedInstanceState == null)
            this.requests = new SparseArray<Bundle>();
        else
            this.requests = savedInstanceState.getSparseParcelableArray("requests");

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        framelayout = (RelativeLayout) findViewById(R.id.content_main);
        ArticleFragment fr = new ArticleFragment();
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
        //special method for start an activity for result.

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
            switchFragment(new ArticleFragment());

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

    public ArrayList<Article> getListArticle(){
        return articles;
    };

    @Override
    public void onArticleClick(Article article){
        setCurrentArticle(article);
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

    // TODO : Implement fragments interfaces on listener calls
    @Override
    public void onSelectImage() {

    }

    @Override
    public void onAddArticle() {

    }

    @Override
    public void onLocationChanged() {

    }
}