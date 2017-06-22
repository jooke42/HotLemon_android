package france.bosch.estelle.android_hotlemon.App;

import france.bosch.estelle.android_hotlemon.Helper.LruBitmapCache;
import android.text.TextUtils;
import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Flavinou on 26/04/2017.
 */

public class AppController extends Application {
    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    // Global variables, useful for requests (unfortunately it's a bad way of coding)
    private String key;
    private String userLogin;
    private String userURL;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    // Mandatory for request implementation (at the moment...)
    public void setKeyToken(String _key) {
        this.key = _key;
    }

    public String getKeyToken() { return this.key; }

    public void setUserLogin(String userLogin) { this.userLogin = userLogin; }

    public String getUserLogin() { return userLogin; }

    public void setUserURL(String userURL) { this.userURL = userURL; }

    public String getUserURL() { return userURL; }
}
