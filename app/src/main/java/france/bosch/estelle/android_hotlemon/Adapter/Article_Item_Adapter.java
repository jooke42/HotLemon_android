package france.bosch.estelle.android_hotlemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.ArticleImageView;
import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.R;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Article_Item_Adapter extends ArrayAdapter<Topic> {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public Article_Item_Adapter(Context context, int resource, List<Topic> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View root;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            root = inflater.inflate(R.layout.article_item, null);
        }
        else {
            root = convertView;
        }

        Topic a = getItem(position);
        TextView item_title = (TextView)root.findViewById(R.id.article_title);
        TextView item_user = (TextView)root.findViewById(R.id.article_user);
        TextView item_location = (TextView)root.findViewById(R.id.article_location);
        ArticleImageView feedImageView = (ArticleImageView)root.findViewById(R.id.feedImage1);

        item_title.setText(a.getTitle());
        item_user.setText(a.getAuthor());
        //item_location.setText(a.g());

        // Feed image
        if (a.getUrlImage() != null) {
            feedImageView.setImageUrl(a.getUrlImage(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new ArticleImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return root;
    }
}
