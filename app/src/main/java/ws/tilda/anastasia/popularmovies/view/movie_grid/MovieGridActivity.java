package ws.tilda.anastasia.popularmovies.view.movie_grid;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.SingleFragmentActivity;

public class MovieGridActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MovieGridFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }
}
