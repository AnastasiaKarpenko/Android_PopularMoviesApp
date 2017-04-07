package ws.tilda.anastasia.popularmovies.view.moviegrid;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.modelobjects.Movie;
import ws.tilda.anastasia.popularmovies.view.moviedetail.MovieDetailActivity;
import ws.tilda.anastasia.popularmovies.view.moviedetail.MovieDetailTabbedActivity;


public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    public static final int NUMBER_OF_VIEWS = 2;
    private List<Movie> mMovies;
    private Context context;
    private static final String IMAGE_PATH = "http://image.tmdb.org/t/p/w185/";

    public MovieGridAdapter(List<Movie> movies, Context context) {
        mMovies = movies;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_grid, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Glide.with(holder.mMoviePosterImageView.getContext()).load(IMAGE_PATH + movie.getPosterPath())
                .centerCrop()
                .into(holder.mMoviePosterImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starMovieDetailActivity(v, holder.getAdapterPosition());
                starMovieDetailTabbedActivity(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        } else {
            return 0;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView mMoviePosterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            adaptHeightToScreenSize(mMoviePosterImageView);

        }

        private void adaptHeightToScreenSize(ImageView view) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            int toolbarHeight = getToolBarHeight();
            int displayHeight = display.getHeight();
            int viewHeight = (displayHeight - toolbarHeight) / NUMBER_OF_VIEWS;

            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = viewHeight;

            view.setLayoutParams(layoutParams);

        }

        public int getToolBarHeight() {
            int[] attrs = new int[]{R.attr.actionBarSize};
            TypedArray typedArray = itemView.getContext().obtainStyledAttributes(attrs);
            int toolBarHeight = typedArray.getDimensionPixelSize(0, -1);
            typedArray.recycle();
            return toolBarHeight;
        }
    }

    private void starMovieDetailActivity(View view, int index) {
        Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE, mMovies.get(index));
        view.getContext().startActivity(intent);
    }

    private void starMovieDetailTabbedActivity(View view, int index) {
        Intent intent = new Intent(view.getContext(), MovieDetailTabbedActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE, mMovies.get(index));
        view.getContext().startActivity(intent);
    }




}
