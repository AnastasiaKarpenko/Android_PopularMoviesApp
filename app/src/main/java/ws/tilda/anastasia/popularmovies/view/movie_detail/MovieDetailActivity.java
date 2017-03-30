package ws.tilda.anastasia.popularmovies.view.movie_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.UUID;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.SingleFragmentActivity;
import ws.tilda.anastasia.popularmovies.model.model_objects.Movie;

public class MovieDetailActivity extends SingleFragmentActivity {

    public static final String MOVIE = "movie";

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
        getSupportActionBar().setTitle("Movie Details");

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

//    public void onSaveInstanceState(Bundle outState) {
//        getSupportFragmentManager().putFragment(outState, "MovieDetailFragment", mFragment);
//    }

}
