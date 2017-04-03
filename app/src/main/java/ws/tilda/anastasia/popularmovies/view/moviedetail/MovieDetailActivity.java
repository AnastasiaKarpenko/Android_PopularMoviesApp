package ws.tilda.anastasia.popularmovies.view.moviedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.SingleFragmentActivity;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Movie;

public class MovieDetailActivity extends SingleFragmentActivity {

    public static final String MOVIE = "movie";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected Fragment createFragment() {
        Movie movie = getIntent()
                .getParcelableExtra(MOVIE);
        return MovieDetailFragment.newInstance(movie);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.movie_details_toolbar);
    }

}
