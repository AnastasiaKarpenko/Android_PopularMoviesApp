package ws.tilda.anastasia.popularmovies.view.movie_grid;

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
import ws.tilda.anastasia.popularmovies.model.model_objects.Movie;
import ws.tilda.anastasia.popularmovies.presenter.MoviePresenter;
import ws.tilda.anastasia.popularmovies.view.movie_detail.MovieDetailActivity;

import static java.security.AccessController.getContext;


public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    private Context context;
    private MoviePresenter mMoviePresenter= new MoviePresenter();

    public MovieGridAdapter(List<Movie> movies, Context context) {
        mMovies = movies;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_grid, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Movie movie = mMoviePresenter.getMovieList().get(position);
        Glide.with(holder.mMoviePosterImageView.getContext()).load(movie.getPosterPath())
                .centerCrop()
                .into(holder.mMoviePosterImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starMovieDetailActivity(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMoviePresenter.getMovieList().size();
    }

    public  class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster) ImageView mMoviePosterImageView;

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
            int viewHeight = (displayHeight - toolbarHeight) / 2;

            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = viewHeight;

            view.setLayoutParams(layoutParams);

        }

        public int getToolBarHeight() {
            int[] attrs = new int[] {R.attr.actionBarSize};
            TypedArray typedArray = itemView.getContext().obtainStyledAttributes(attrs);
            int toolBarHeight = typedArray.getDimensionPixelSize(0, -1);
            typedArray.recycle();
            return toolBarHeight;
        }
    }
    private void starMovieDetailActivity(View view, int index) {
        Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE, mMoviePresenter.getMovieList().get(index));
        view.getContext().startActivity(intent);
    }


}
