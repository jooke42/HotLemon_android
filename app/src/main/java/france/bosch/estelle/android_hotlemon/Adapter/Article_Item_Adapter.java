package france.bosch.estelle.android_hotlemon.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import france.bosch.estelle.android_hotlemon.Class.Article;
import france.bosch.estelle.android_hotlemon.R;
import france.bosch.estelle.android_hotlemon.Helper.ImageUtile;

/**
 * Created by ESTEL on 20/04/2017.
 */

public class Article_Item_Adapter extends ArrayAdapter<Article> {
    public Article_Item_Adapter(Context context, int resource, List<Article> objects) {
        super(context, resource, objects);
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View root = inflater.inflate(R.layout.article_item, null);
        Article a = getItem(position);
        TextView item_title = (TextView)root.findViewById(R.id.article_title);
        TextView item_user = (TextView)root.findViewById(R.id.article_user);
        TextView item_location = (TextView)root.findViewById(R.id.article_location);
        item_title.setText(a.getTitle());
        item_user.setText(a.getUser());
        if(a.getLocation() != null)
         item_location.setText(a.getLocation().getName());

        ImageView voyage_image = (ImageView)root.findViewById(R.id.voyage_image);
       /* if(a.getImage() != null)
            voyage_image.setImageBitmap(ImageUtile.convertByteArrayToBitmap(a.getImage()));*/
        return root;
    }

}
