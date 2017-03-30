package ws.tilda.anastasia.popularmovies.view.movie_detail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.SingleFragmentActivity;
import ws.tilda.anastasia.popularmovies.model.model_objects.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE = "movie";
    private Movie mMovie;

    protected Fragment createFragment(Movie movie) {
        return MovieDetailFragment.newInstance(movie);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        mMovie = intent.getParcelableExtra(MOVIE);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment(mMovie);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }


}
