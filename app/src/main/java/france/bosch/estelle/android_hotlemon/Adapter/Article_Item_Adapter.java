package france.bosch.estelle.android_hotlemon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.ArticleImageView;
import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.R;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Article_Item_Adapter extends BaseAdapter {
    private final Context mContext;
    private final List<Topic> topics;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public Article_Item_Adapter(Context context, List<Topic> topics) {
        this.mContext = context;
        this.topics = topics;
    }
    @Override
    public int getCount() {
        return topics.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Topic getItem(int position) {
        return topics.get(position);
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       LinearLayout imageView;
        final Topic topic = getItem(position);

        // 2
        if (convertView == null) {
            imageView = new LinearLayout(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setPadding(8, 8, 8, 8);
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.article_item, null);
        }
        else{
            imageView = (LinearLayout) convertView;
        }

        // 3
        final ArticleImageView feedImageView = (ArticleImageView)convertView.findViewById(R.id.feedImage1);
        final TextView title = (TextView)convertView.findViewById(R.id.article_title);
        final TextView author = (TextView)convertView.findViewById(R.id.article_user);
        final TextView location = (TextView)convertView.findViewById(R.id.article_location);

        // 4
        title.setText(topic.getTitle());
        author.setText(topic.getAuthor());
        if(topic.getLocation() != null)
            location.setText(topic.getLocation().getName());

        // Feed image
        if (topic.getUrlImage() != null) {
            feedImageView.setImageUrl(topic.getUrlImage(), imageLoader);
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

        return convertView;
    }


}
