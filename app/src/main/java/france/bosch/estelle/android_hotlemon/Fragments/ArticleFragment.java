package france.bosch.estelle.android_hotlemon.Fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.places.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import france.bosch.estelle.android_hotlemon.Adapter.Article_Item_Adapter;
import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.R;

public class ArticleFragment extends Fragment {

    public interface ArticleFragmentListener {
        void onArticleClick(Topic news);
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private String URL_FEED = "https://perso.esiee.fr/~pereirae/webe3fi/test.json";
    private String URL_FEED_GET = "http://82.232.20.224/topics";
    private GridView gridView;
    private Article_Item_Adapter adapter;
    private ArticleFragmentListener listener;
    private List<Topic> topics;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.fragment_article, container, false);

        gridView = (GridView) root.findViewById(R.id.grid_article);
        topics = ((MainActivity)(getActivity())).getNews();
        adapter = new Article_Item_Adapter(getActivity(),topics);
        gridView.setAdapter(null);
        gridView.setAdapter(adapter);
        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.fab_create_article);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)(getActivity())).switchFragment(new Fragment_CreateArticle());
            }
        });

        /// Code added to test JSON requests and article feed (and it works mf !) ///

        // We first check for cached request

        /*Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED_GET);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {*/
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED_GET, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    Log.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Log.d(TAG, "Error: " + error.getMessage());
                }
            }) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    try {
                        Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                        if (cacheEntry == null) {
                            cacheEntry = new Cache.Entry();
                        }
                        final long cacheHitButRefreshed = 2 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                        final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                        long now = System.currentTimeMillis();
                        final long softExpire = now + cacheHitButRefreshed;
                        final long ttl = now + cacheExpired;
                        cacheEntry.data = response.data;
                        cacheEntry.softTtl = softExpire;
                        cacheEntry.ttl = ttl;
                        String headerValue;
                        headerValue = response.headers.get("Date");

                        if (headerValue != null) {
                            cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                        }

                        headerValue = response.headers.get("Last-Modified");
                        if (headerValue != null) {
                            cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                        }

                        cacheEntry.responseHeaders = response.headers;
                        final String jsonString = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers));

                        return Response.success(new JSONObject(jsonString), cacheEntry);
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    } catch (JSONException e) {
                        return Response.error(new ParseError(e));
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Token " + AppController.getInstance().getKeyToken());
                    return headers;
                }
            };

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
            // }
       // }

            return root;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //list item click
       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Topic item = (Topic) parent.getItemAtPosition(position);
                listener.onArticleClick(item);
            }
        });*/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Topic topic = topics.get(position);
                listener.onArticleClick(topic);
                //booksAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (ArticleFragmentListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement ArticleFragmentListener");
        }
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("results");

            for (int i = 0; i < feedArray.length(); i++) {
                final JSONObject feedObj = (JSONObject) feedArray.get(i);

                final Topic item = new Topic();

                item.setTitle(feedObj.getString("title"));
                item.setBody(feedObj.getString("body"));
                item.setVoteFor(feedObj.getInt("vote_for"));
                item.setVoteAgainst(feedObj.getInt("vote_against"));
                if (feedObj.getString("longitude") != "null")
                    item.setlongitude(Double.parseDouble(feedObj.getString("longitude")));
                if (feedObj.getString("latitude") != "null")
                    item.setLatitude(Double.parseDouble(feedObj.getString("latitude")));
                Geocoder geocoder;
                List<Address> addresses = new ArrayList<>();
                if (item.getLatitude() != null && item.getlongitude() != null) {
                    geocoder = new Geocoder(((getActivity())), Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(item.getLatitude(), item.getlongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    } catch (Exception e) {

                    }

                    if (addresses.size() != 0) {
                        Address address = addresses.get(0);
                        item.setAddress(address);
                    }
                }

                //TODO change
                //item.setCreatedDate(feedObj.getString("date"));
                // Image might be null sometimes
                String image = feedObj.isNull("picture") ? null : feedObj
                        .getString("picture");
                item.setUrlImage(image);

                // Request username from post
                // making fresh volley request and getting json
                JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                        feedObj.getString("author"), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d(TAG, "Response: " + response.toString());
                        Log.d(TAG, "Response: " + response.toString());

                        if (response != null) {
                            item.setAuthor(parseJsonUsername(response));
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getStackTrace());
                        Log.d(TAG, "Error: " + error.getMessage());
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


                // Adding request to volley request queue
                AppController.getInstance().addToRequestQueue(jsonReq);

                //// Check ID of News Item to avoid duplication in gridView when switching fragment
                if( !((MainActivity)(getActivity())).getNews().contains(item))
                 ((MainActivity)(getActivity())).addNews(item);



            }

            // notify data changes to list adapter
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String parseJsonUsername(JSONObject response) {
        String feedUser = "";

        try {
            feedUser = response.getString("username");
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        if (feedUser != null || feedUser != "") {
            return feedUser;
        }

        return feedUser;
    }

}
