package ws.tilda.anastasia.popularmovies.view.moviedetail.moviedetails;

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
import ws.tilda.anastasia.popularmovies.model.modelobjects.Movie;


public class MovieDetailFragment extends Fragment {
    public static final String MOVIE = "movie";
    public static final String IMAGE_PATH = "http://image.tmdb.org/t/p/w185/";
    public static final int NUMBER_DIGITS_YEAR = 4;

    private Movie mMovie;

    @BindView(R.id.detail_movie_title)
    TextView mMovieDetailTitle;
    @BindView(R.id.detail_movie_poster)
    ImageView mMovieDetailPoster;
    @BindView(R.id.detail_release_date)
    TextView mMovieDetailReleaseDate;
    @BindView(R.id.detail_movie_raiting)
    TextView mMovieDetailRating;
    @BindView(R.id.detail_movie_overview)
    TextView mMovieDetailOverview;

    Unbinder unbinder;


    public static MovieDetailFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(MOVIE, movie);

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(args);

        return movieDetailFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = getArguments().getParcelable(MOVIE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        unbinder = ButterKnife.bind(this, v);

        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE)) {
            mMovie = savedInstanceState.getParcelable(MOVIE);
        }

        updateUi(mMovie);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }

    private void updateUi(Movie movie) {
        mMovieDetailTitle.setText(movie.getTitle());
        Glide.with(mMovieDetailPoster.getContext()).load(IMAGE_PATH + movie.getPosterPath())
                .centerCrop()
                .into(mMovieDetailPoster);
        mMovieDetailReleaseDate.setText(formatReleaseDateString(movie.getReleaseDate()));
        mMovieDetailRating.setText(String.format("%s/10", movie.getVoteAverage().toString()));
        mMovieDetailOverview.setText(movie.getOverview());
    }

    private String formatReleaseDateString(String string) {
        String year = "";

        if (string == null) {
            return "0";
        } else {
            for (int i = 0; i < NUMBER_DIGITS_YEAR; i++) {
                year += string.charAt(i);
            }
            return year;
        }

    }

}
