package france.bosch.estelle.android_hotlemon.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;

import france.bosch.estelle.android_hotlemon.App.AppController;
import france.bosch.estelle.android_hotlemon.ArticleImageView;
import france.bosch.estelle.android_hotlemon.Class.Topic;
import france.bosch.estelle.android_hotlemon.MainActivity;
import france.bosch.estelle.android_hotlemon.R;

public class ArticleDetailFragment extends Fragment {
    private ArticleImageView ArticleImageView;
    private TextView Date;

    private TextView User;
    private TextView Title;
    private TextView Description;
    private ImageLoader imageLoader;
    private FloatingActionButton voteFor;
    private FloatingActionButton voteAgainst;
    private TextView vote;
    private Topic currentTopic;
    private TextView place;

    public ArticleDetailFragment() {}

    public static ArticleDetailFragment newInstance(Topic news) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_article_detail, container, false);

        ArticleImageView = (ArticleImageView) root.findViewById(R.id.article_image_view);
        Date = (TextView) root.findViewById(R.id.date);

        User = (TextView) root.findViewById(R.id.user);
        Title = (TextView) root.findViewById(R.id.titre);
        Description = (TextView) root.findViewById(R.id.article_description);
        voteFor = (FloatingActionButton) root.findViewById(R.id.voteFor);
        voteAgainst  = (FloatingActionButton) root.findViewById(R.id.voteAgainst);
        vote  = (TextView) root.findViewById(R.id.vote);
        currentTopic =  ((MainActivity)(getActivity())).getCurrentNews();
        place = (TextView) root.findViewById(R.id.article_place);
        setData();

        return root;
    }

    private void setData(){
        Topic currentNews = ((MainActivity)(getActivity())).getCurrentNews();

        // used to load the feed image
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        ArticleImageView.setImageUrl(currentNews.getUrlImage(), imageLoader);
        //Date.setText(currentNews.getCreatedDate().toString());

        User.setText(currentNews.getAuthor());
        Title.setText(currentNews.getTitle());
        Description.setText(currentNews.getBody());
        if(currentNews.getLocation() != null)
        place.setText(currentNews.getLocation().getName());

        updateVote();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        voteFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTopic.updateVoteFor();
                updateVote();
            }
        });
        voteAgainst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               currentTopic.updateVoteAgainst();
                updateVote();
            }
        });

    }

    private void updateVote(){
        vote.setText(String.valueOf(currentTopic.getVote()));

    }





}
