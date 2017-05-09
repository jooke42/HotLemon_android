package france.bosch.estelle.android_hotlemon;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;


import java.util.ArrayList;


import france.bosch.estelle.android_hotlemon.Class.Article;

import france.bosch.estelle.android_hotlemon.Fragments.ArticleDetailFragment;
import france.bosch.estelle.android_hotlemon.Fragments.ArticleFragment;
import france.bosch.estelle.android_hotlemon.Fragments.EditProfilFragment;
import france.bosch.estelle.android_hotlemon.Fragments.Fragment_CreateArticle;
import france.bosch.estelle.android_hotlemon.Helper.PlaceActivityUtility;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ArticleFragment.ArticleFragmentListener


{

    private RelativeLayout framelayout;
    private Article currentArticle;
    private Location currentLocation;


    ArrayList<Article> articles = new ArrayList<Article>();
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        framelayout = (RelativeLayout)findViewById(R.id.content_main);
        ArticleFragment fr = new ArticleFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content_main,fr,"tag");
        transaction.commit();



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Article article1 = new Article("article1", "flavinou");
        Article article2 = new Article("article2", "miskinalien");
        Article article3 = new Article("article3", "silvermoon");
        Article article4 = new Article("article4", "martoto");
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);
        articles.add(article4);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment(Fragment f ){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
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
   /* @Override
    public void onArticleClick() {

        switchFragment(new ArticleDetailFragment());
    }*/






}
