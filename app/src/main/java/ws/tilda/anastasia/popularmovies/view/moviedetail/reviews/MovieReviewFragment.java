package ws.tilda.anastasia.popularmovies.view.moviedetail.reviews;

import android.content.Context;
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
import ws.tilda.anastasia.popularmovies.PopularMovies;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Review;
import ws.tilda.anastasia.popularmovies.model.modelobjects.ReviewResponse;
import ws.tilda.anastasia.popularmovies.model.networking.MovieApi;

public class MovieReviewFragment extends Fragment {

    private static final String MOVIE_ID = "movie_id";
    private static final String REVIEWS = "trailers";

    private OnListFragmentInteractionListener mListener;
    private List<Review> mReviews;
    private int mMovieId;

    @BindView(R.id.movie_review_recycler_view)
    RecyclerView mReviewRecyclerView;

    Unbinder unbinder;

    public MovieReviewFragment() {

    }

    public static MovieReviewFragment newInstance(int movieId) {
        MovieReviewFragment fragment = new MovieReviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_moviereview, container, false);

        unbinder = ButterKnife.bind(this, view);

        mReviewRecyclerView.addItemDecoration(new ws.tilda.anastasia.popularmovies.view.moviedetail
                .utils.DividerItemDecoration(mReviewRecyclerView.getContext()));

        if (savedInstanceState != null) {
            reloadIfThereAreNoReviews(savedInstanceState);
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
        outState.putParcelableArrayList(REVIEWS, (ArrayList<? extends Parcelable>) mReviews);
        super.onSaveInstanceState(outState);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Review review);
    }

    private void reloadIfThereAreNoReviews(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState.getParcelableArrayList(REVIEWS) == null) {
            updateUI();
        } else {
            mReviews = savedInstanceState.getParcelableArrayList(REVIEWS);
            setupAdapter(mReviews);
        }
    }

    private void updateUI() {
        if (PopularMovies.hasNetwork()) {
            Call<ReviewResponse> call = makeCallToFetchReviews(mMovieId);
            showTrailers(call);
        } else {
            Toast.makeText(getContext(), R.string.no_network_connection, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private Call<ReviewResponse> makeCallToFetchReviews(int movieId) {
        return MovieApi.provideMovieService().fetchReviewsByMovieId(movieId);
    }

    private void showTrailers(Call<ReviewResponse> call) {
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, retrofit2.Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    mReviews = response.body().getReviews();
                }

                if (mReviews != null) {
                    setupAdapter(mReviews);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.could_not_load_trailers, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void setupAdapter(List<Review> reviews) {
        if (isAdded()) {
            mReviewRecyclerView.setAdapter(new MovieReviewRecyclerViewAdapter(reviews, mListener));
        }
    }
}
