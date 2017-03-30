package ws.tilda.anastasia.popularmovies.view.movie_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.model_objects.Movie;


public class MovieDetailFragment extends Fragment {
    public static final String MOVIE = "movie";
    @BindView(R.id.detail_movie_title) TextView mMovieDetailTitle;
    @BindView(R.id.detail_movie_poster) ImageView mMovieDetailPoster;
    @BindView(R.id.detail_release_date) TextView mMovieDetailReleaseDate;
    @BindView(R.id.detail_movie_raiting) TextView mMovieDetailRating;
    @BindView(R.id.detail_movie_overview) TextView mMovieDetailOverview;
    Unbinder unbinder;

    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(MOVIE, movie);

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(args);

        return movieDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail,container,false);
        unbinder = ButterKnife.bind(this, v);
        Movie movie = getArguments().getParcelable(MOVIE);

        mMovieDetailTitle.setText(movie.getTitle());
        Glide.with(mMovieDetailPoster.getContext()).load(movie.getPosterPath())
                .centerCrop()
                .into(mMovieDetailPoster);
        mMovieDetailReleaseDate.setText(movie.getReleaseDate());
        mMovieDetailRating.setText(movie.getVoteAverage().toString());
        mMovieDetailOverview.setText(movie.getOverview());

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
