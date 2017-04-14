package ws.tilda.anastasia.popularmovies.view.moviedetail.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Review;

import java.util.List;


public class MovieReviewRecyclerViewAdapter extends RecyclerView
        .Adapter<MovieReviewRecyclerViewAdapter.ViewHolder> {

    private final List<Review> mReviews;
    private final MovieReviewFragment.OnListFragmentInteractionListener mListener;

    public MovieReviewRecyclerViewAdapter(List<Review> reviews, MovieReviewFragment
            .OnListFragmentInteractionListener listener) {
        mReviews = reviews;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_moviereview_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mReview = mReviews.get(position);
        holder.mReviewAuthor.setText("Review by: " + holder.mReview.getAuthor());
        holder.mReviewId.setText(holder.mReview.getId());
        holder.mReviewContent.setText(holder.mReview.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mReview);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mReviews != null) {
            return mReviews.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView mReviewAuthor;
        @BindView(R.id.review_id)
        TextView mReviewId;
        @BindView(R.id.review_content)
        TextView mReviewContent;

        public Review mReview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
