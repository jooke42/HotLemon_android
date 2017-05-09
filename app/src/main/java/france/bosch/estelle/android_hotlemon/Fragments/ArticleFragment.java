package france.bosch.estelle.android_hotlemon.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import france.bosch.estelle.android_hotlemon.Adapter.Article_Item_Adapter;
import france.bosch.estelle.android_hotlemon.Class.Article;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.R;

public class ArticleFragment extends Fragment {

    public interface ArticleFragmentListener {
        void onArticleClick(Article article);
    }
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

        View root =  inflater.inflate(R.layout.fragment_article, container, false);
        //View main =  inflater.inflate(R.layout.activity_main, container, false);
        gridView = (GridView) root.findViewById(R.id.grid_article);
        adapter = new Article_Item_Adapter(getActivity(),R.layout.article_item,  ((MainActivity)(getActivity())).getListArticle());
        gridView.setAdapter(adapter);
        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.fab_create_article);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)(getActivity())).switchFragment(new Fragment_CreateArticle());
            }
        });


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
                Article item = (Article) parent.getItemAtPosition(position);
                listener.onArticleClick(item);
            }
        });

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
}
