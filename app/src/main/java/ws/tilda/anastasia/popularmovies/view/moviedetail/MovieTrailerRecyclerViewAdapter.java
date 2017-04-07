package ws.tilda.anastasia.popularmovies.view.moviedetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Trailer;
import ws.tilda.anastasia.popularmovies.view.moviedetail.MovieTrailerFragment.OnListFragmentInteractionListener;

public class MovieTrailerRecyclerViewAdapter
        extends RecyclerView.Adapter<MovieTrailerRecyclerViewAdapter.ViewHolder> {

    private final List<Trailer> mTrailers;
    private final OnListFragmentInteractionListener mListener;

    public MovieTrailerRecyclerViewAdapter(List<Trailer> trailers,
                                           OnListFragmentInteractionListener listener) {
        mTrailers = trailers;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movietrailer_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTrailer = mTrailers.get(position);
        holder.mTrailerName.setText(mTrailers.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mTrailer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTrailers != null) {
            return mTrailers.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.trailer_textView)
        TextView mTrailerName;

        public Trailer mTrailer;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
