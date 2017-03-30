package ws.tilda.anastasia.popularmovies.view.movie_grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.presenter.MoviePresenter;

public class MovieGridFragment extends Fragment {
    @BindView(R.id.movie_recycler_view)
    RecyclerView mMovieRecyclerView;
    private MoviePresenter mMoviePresenter;
    Unbinder unbinder;
    public static final String MOVIE_LIST = "movie_list";

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

        mMoviePresenter = new MoviePresenter();
        mMoviePresenter.loadMovieList();

        setupAdapter();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupAdapter() {
        if (isAdded()) {
            mMovieRecyclerView.setAdapter(new MovieGridAdapter(mMoviePresenter.getMovieList(),
                    getContext()));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_movie_grid, menu);
    }


}
