package ws.tilda.anastasia.popularmovies.view.movie_grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.presenter.MoviePresenter;

public class MovieGridFragment extends Fragment {
    private RecyclerView mMovieRecyclerView;
    private MoviePresenter mMoviePresenter;

    public static MovieGridFragment newInstance() {
        return new MovieGridFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        mMovieRecyclerView = (RecyclerView) v.findViewById(R.id.movie_recycler_view);
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mMoviePresenter = new MoviePresenter();
        mMoviePresenter.loadMovieList();

        setupAdapter();


        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mMovieRecyclerView.setAdapter(new MovieGridAdapter(mMoviePresenter.getMovieList()));
        }
    }
}
