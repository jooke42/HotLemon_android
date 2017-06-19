package france.bosch.estelle.android_hotlemon.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import france.bosch.estelle.android_hotlemon.Adapter.Article_Item_Adapter;
import france.bosch.estelle.android_hotlemon.BDD.News;
import france.bosch.estelle.android_hotlemon.BDD.DataBaseHelper;
import france.bosch.estelle.android_hotlemon.BDD.Topic;
import france.bosch.estelle.android_hotlemon.Class.Category;
import france.bosch.estelle.android_hotlemon.Dialog.ChooseTypeDialog;
import france.bosch.estelle.android_hotlemon.R;

public class HotArticleFragment extends Fragment {

    public interface ArticleFragmentListener {
        void onArticleClick(Topic article);
    }



    private GridView gridView;
    private static Article_Item_Adapter adapter;
    private ArticleFragmentListener listener;
    private static DataBaseHelper DB;

    public HotArticleFragment() {

    }



    public static HotArticleFragment newInstance(Category category) {
        HotArticleFragment fragment = new HotArticleFragment();


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
        adapter = new Article_Item_Adapter(getActivity(), R.layout.article_item, DataBaseHelper.getTopicList());
        gridView = (GridView) root.findViewById(R.id.grid_article);
        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.fab_create_article);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseTypeDialog myDialogFragment = new ChooseTypeDialog();
                myDialogFragment.show(getFragmentManager(), "dialog_fragment");
            }
        });

        return root;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView.setAdapter(adapter);
        //list item click
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Topic item = (Topic) parent.getItemAtPosition(position);
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
