package ws.tilda.anastasia.popularmovies.view.moviedetail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import ws.tilda.anastasia.popularmovies.model.modelobjects.Trailer;
import ws.tilda.anastasia.popularmovies.model.modelobjects.TrailerResponse;
import ws.tilda.anastasia.popularmovies.model.networking.MovieApi;


public class MovieTrailerFragment extends Fragment {

    private static final String MOVIE_ID = "movie_id";
    private static final String TRAILERS = "trailers";

    private OnListFragmentInteractionListener mListener;
    private List<Trailer> mTrailers;
    private int mMovieId;

    @BindView(R.id.trailer_list_recyclerView)
    RecyclerView mTrailerRecyclerView;

    Unbinder unbinder;

    public MovieTrailerFragment() {
        //default constructor
    }

    public static MovieTrailerFragment newInstance(int movieId) {
        MovieTrailerFragment fragment = new MovieTrailerFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIE_ID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mMovieId = getArguments().getInt(MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movietrailer_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        mTrailerRecyclerView.addItemDecoration(new ws.tilda.anastasia.popularmovies.view.moviedetail
                .utils.DividerItemDecoration(mTrailerRecyclerView.getContext()));

        if (savedInstanceState != null) {
            reloadIfThereAreNoTrailers(savedInstanceState);
        } else {
            updateUI();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TRAILERS, (ArrayList<? extends Parcelable>) mTrailers);
        super.onSaveInstanceState(outState);
    }


    private void reloadIfThereAreNoTrailers(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState.getParcelableArrayList(TRAILERS) == null) {
            updateUI();
        } else {
            mTrailers = savedInstanceState.getParcelableArrayList(TRAILERS);
            setupAdapter(mTrailers);
        }
    }

    private void updateUI() {
        if (isOnline()) {
            Call<TrailerResponse> call = makeCallToFetchTrailers(mMovieId);
            showTrailers(call);
        } else {
            Toast.makeText(getContext(), R.string.no_network_connection, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Trailer trailer);
    }

    private Call<TrailerResponse> makeCallToFetchTrailers(int movieId) {
        return MovieApi.provideMovieService().fetchTrailersByMovieId(movieId);
    }

    private void showTrailers(Call<TrailerResponse> call) {
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, retrofit2.Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    mTrailers = response.body().getTrailers();
                }

                if (mTrailers != null) {
                    setupAdapter(mTrailers);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.could_not_load_trailers, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void setupAdapter(List<Trailer> trailers) {
        if (isAdded()) {
            mTrailerRecyclerView.setAdapter(new MovieTrailerRecyclerViewAdapter(trailers, mListener));
        }
    }
}

