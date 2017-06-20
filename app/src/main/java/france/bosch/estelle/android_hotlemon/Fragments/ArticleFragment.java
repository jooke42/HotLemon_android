package france.bosch.estelle.android_hotlemon.Fragments;

import android.content.Context;
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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import france.bosch.estelle.android_hotlemon.Adapter.Article_Item_Adapter;
import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.Class.News;
import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.Dialog.ChooseTypeDialog;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.R;

public class ArticleFragment extends Fragment {

    public interface ArticleFragmentListener {
        void onArticleClick(Topic news);
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private String URL_FEED = "https://perso.esiee.fr/~pereirae/webe3fi/test.json";
    private String URL_FEED_GET = "http://82.232.20.224/news/";
    private GridView gridView;
    private Article_Item_Adapter adapter;
    private ArticleFragmentListener listener;

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
        adapter = new Article_Item_Adapter(getActivity(), R.layout.article_item,  ((MainActivity)(getActivity())).getNews());
        gridView.setAdapter(adapter);
        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.fab_create_article);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)(getActivity())).showChooseTypeDialog();
            }
        });

        /// Code added to test JSON requests and article feed (and it works mf !) ///

        // We first check for cached request
        adapter.clear();
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
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

        } else {
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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Token " + AppController.getInstance().getKeyToken());
                    return headers;
                }
            };

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //list item click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                News item = (News) parent.getItemAtPosition(position);
                listener.onArticleClick(item);
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
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                Topic item = new Topic();

                /*item.setTitle(feedObj.getString("title"));
                //item.setRawLocation(feedObj.getString("location"));
                item.setAuthor(feedObj.getString("user"));
                item.setBody(feedObj.getString("description"));
                //item.setCategory(feedObj.getString("category"));
                item.setCreatedDate(feedObj.getString("date"));

                // Image might be null sometimes
                String image = feedObj.isNull("Urlimage") ? null : feedObj
                        .getString("Urlimage");
                item.setUrlimage(image);*/

                item.setTitle(feedObj.getString("title"));
                //item.setRawLocation(feedObj.getString("location"));
                item.setAuthor(feedObj.getString("author"));
                item.setBody(feedObj.getString("body"));
                //item.setCategory(feedObj.getString("category"));
                //item.setCreatedDate(feedObj.getString("date"));

                // Image might be null sometimes
                String image = feedObj.isNull("picture") ? null : feedObj
                        .getString("picture");
                item.setUrlImage(image);

                //// Check ID of News Item to avoid duplication in gridView when switching fragment
               // ((MainActivity)(getActivity())).addArticle(item);
            }

            // notify data changes to list adapter
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
