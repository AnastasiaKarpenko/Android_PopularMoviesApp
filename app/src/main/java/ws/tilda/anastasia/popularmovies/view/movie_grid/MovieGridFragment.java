package ws.tilda.anastasia.popularmovies.view.movie_grid;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.model_objects.Movie;
import ws.tilda.anastasia.popularmovies.model.model_objects.Response;
import ws.tilda.anastasia.popularmovies.model.networking.MovieApi;


public class MovieGridFragment extends Fragment {
    public static final String MOVIES = "movies";
    public static final int SPAN_COUNT = 2;
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    private List<Movie> mMovies;

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

        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));

        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList(MOVIES);
            setupAdapter(mMovies);
        } else {
            Call<Response> call = makeCallToGetDefaultMovieList();
            showMovies(call);
        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                Call<Response> call = makeCallToGetMoviesSortedBy(POPULAR);
                showMovies(call);
                Toast.makeText(getContext(), R.string.most_popular_movies, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.top_rated:
                call = makeCallToGetMoviesSortedBy(TOP_RATED);
                showMovies(call);
                Toast.makeText(getContext(), R.string.top_rated_movies, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES, (ArrayList<? extends Parcelable>) mMovies);
        super.onSaveInstanceState(outState);
    }

    private Call<Response> makeCallToGetDefaultMovieList() {
        return MovieApi.provideMovieService().getDefaultMovieList();
    }

    private Call<Response> makeCallToGetMoviesSortedBy(String string) {
        return MovieApi.provideMovieService().sortMoviesByCriteria(string);
    }

    private void showMovies(Call<Response> call) {
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    mMovies = response.body().getMovies();
                }

                if (mMovies != null) {
                    setupAdapter(mMovies);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getContext(), "Error received", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAdapter(List<Movie> movies) {
        if (isAdded()) {
            mMovieRecyclerView.setAdapter(new MovieGridAdapter(movies, getContext()));
        }
    }
}
