package ws.tilda.anastasia.popularmovies.view.moviedetail.moviedetailtabs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Movie;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Review;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Trailer;
import ws.tilda.anastasia.popularmovies.view.moviedetail.reviews.MovieReviewFragment;
import ws.tilda.anastasia.popularmovies.view.moviedetail.reviews.ReviewDetailsActivity;
import ws.tilda.anastasia.popularmovies.view.moviedetail.trailers.MovieTrailerFragment;


public class MovieDetailTabbedActivity extends AppCompatActivity
        implements MovieTrailerFragment.OnListFragmentInteractionListener,
        MovieReviewFragment.OnListFragmentInteractionListener {

    public static final String MOVIE = "movie";
    public static final String REVIEW_EXTRA = "review";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Movie mMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_movie_detail_tabbed);

        mMovie = getIntent().getParcelableExtra(MOVIE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.movie_details_toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mMovie);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        setFloatingButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Trailer trailer) {
        String key = trailer.getKey();
        watchYoutubeVideo(key);
    }

    public void watchYoutubeVideo(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    @Override
    public void onListFragmentInteraction(Review review) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(REVIEW_EXTRA, review);
        ReviewDetailsActivity.launch(this, review);

    }

//    private void setFloatingButton() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }

}
