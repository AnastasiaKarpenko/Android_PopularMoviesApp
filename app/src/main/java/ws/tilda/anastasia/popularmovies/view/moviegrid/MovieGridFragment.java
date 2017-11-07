package ws.tilda.anastasia.popularmovies.view.moviegrid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import ws.tilda.anastasia.popularmovies.PopularMovies;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.data.FavouriteMoviesContract;
import ws.tilda.anastasia.popularmovies.model.data.FavouriteMoviesDbHelper;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Movie;
import ws.tilda.anastasia.popularmovies.model.modelobjects.MovieResponse;
import ws.tilda.anastasia.popularmovies.model.networking.MovieApi;

import static ws.tilda.anastasia.popularmovies.model.data.FavouriteMoviesContract.*;


public class MovieGridFragment extends Fragment {
    public static final String MOVIES = "movies";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final int WIDTH_DIVIDER = 400;
    public static final int MIN_COLUMN_NUMBER = 2;

    private List<Movie> mMovies;
    private SQLiteDatabase mDatabase;
    private Cursor mCursor;

    @BindView(R.id.movie_recycler_view)
    RecyclerView mMovieRecyclerView;



    Unbinder unbinder;

    public static MovieGridFragment newInstance() {
        return new MovieGridFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        unbinder = ButterKnife.bind(this, v);

        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));

        if (savedInstanceState != null) {
            reloadIfThereAreNoMovies(savedInstanceState);
        } else {
            updateUI();
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavouriteMoviesDbHelper dbHelper = new FavouriteMoviesDbHelper(getActivity().getApplicationContext());
        mDatabase = dbHelper.getWritableDatabase();
//        mCursor = getFavouriteMovies();

    }


    @Override
    public void onDestroyView() {
        mDatabase.close();

        unbinder.unbind();

        super.onDestroyView();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_movie_grid, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                Call<MovieResponse> call = makeCallToGetMoviesSortedBy(POPULAR);
                showMovies(call);
                Toast.makeText(getContext(), R.string.most_popular_movies, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.top_rated:
                call = makeCallToGetMoviesSortedBy(TOP_RATED);
                showMovies(call);
                Toast.makeText(getContext(), R.string.top_rated_movies, Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.favourite_movies:
//                setupFavMoviesAdapter(mCursor);
//                Toast.makeText(getContext(), R.string.favourite_movies, Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES, (ArrayList<? extends Parcelable>) mMovies);
        super.onSaveInstanceState(outState);
    }


    private void reloadIfThereAreNoMovies(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState.getParcelableArrayList(MOVIES) == null) {
            updateUI();
        } else {
            mMovies = savedInstanceState.getParcelableArrayList(MOVIES);
            setupAdapter(mMovies);
        }
    }

    private void updateUI() {
        if(PopularMovies.hasNetwork()) {
            Call<MovieResponse> call = makeCallToGetDefaultMovieList();
            showMovies(call);
        } else {
            Toast.makeText(getContext(), R.string.no_network_connection, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private Call<MovieResponse> makeCallToGetDefaultMovieList() {
        return MovieApi.provideMovieService().getDefaultMovieList();
    }

    private Call<MovieResponse> makeCallToGetMoviesSortedBy(String string) {
        return MovieApi.provideMovieService().sortMoviesByCriteria(string);
    }

    private void showMovies(Call<MovieResponse> call) {
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    mMovies = response.body().getMovies();
                }

                if (mMovies != null) {
                    setupAdapter(mMovies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.could_not_load_movies, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = WIDTH_DIVIDER;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < MIN_COLUMN_NUMBER) {
            return MIN_COLUMN_NUMBER;
        }
        return nColumns;
    }

    private void setupAdapter(List<Movie> movies) {
        if (isAdded()) {
            mMovieRecyclerView.setAdapter(new MovieGridAdapter(movies, getContext()));
        }
    }

//    private void setupFavMoviesAdapter(Cursor cursor) {
//        if (isAdded()) {
//            mMovieRecyclerView.setAdapter(new FavouriteMoviesAdapter(getContext(), cursor));
//        }
//    }

//    private Cursor getFavouriteMovies() {
//        return mDatabase.query(FavouriteMoviesEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                FavouriteMoviesEntry.COLUMN_MOVIE_POSTER_PATH
//        );
//    }
}
