package ws.tilda.anastasia.popularmovies.view.movie_grid;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.SingleFragmentActivity;

public class MovieGridActivity extends SingleFragmentActivity {
    Fragment mContent;

    @Override
    protected Fragment createFragment() {
        return MovieGridFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "myFragmentName",
                getSupportFragmentManager().findFragmentById(R.id.fragment_container));
    }
}
