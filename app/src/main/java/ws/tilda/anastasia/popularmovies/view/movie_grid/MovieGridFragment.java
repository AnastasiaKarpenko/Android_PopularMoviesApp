package ws.tilda.anastasia.popularmovies.view.movie_grid;

import android.os.Bundle;
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
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        Call<Response> call = makeCallToDefaultMovieList();
        showMovies(call);

        return v;
    }

    private Call<Response> makeCallToDefaultMovieList() {
        return MovieApi.provideMovieService().getMovies();
    }

    private Call<Response> makeCallToPopularMovieList() {
        return MovieApi.provideMovieService().sortMoviesByCriteria("popular");
    }

    private Call<Response> makeCallToTopRatedMovieList() {
        return MovieApi.provideMovieService().sortMoviesByCriteria("top_rated");
    }

    private void showMovies(Call<Response> call) {
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                mMovies = response.body().getMovies();
                setupAdapter(mMovies);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(getContext(), "Error received", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupAdapter(List<Movie> movies) {
        if (isAdded()) {
            mMovieRecyclerView.setAdapter(new MovieGridAdapter(movies,
                    getContext()));
        }
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
                // write code here
                Call<Response> call = makeCallToPopularMovieList();
                showMovies(call);
                return true;
            case R.id.top_rated:
                // write code here
                call = makeCallToTopRatedMovieList();
                showMovies(call);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
