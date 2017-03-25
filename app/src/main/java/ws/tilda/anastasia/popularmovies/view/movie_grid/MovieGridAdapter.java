package ws.tilda.anastasia.popularmovies.view.movie_grid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import ws.tilda.anastasia.popularmovies.R;
import ws.tilda.anastasia.popularmovies.model.model_objects.Movie;
import ws.tilda.anastasia.popularmovies.presenter.MoviePresenter;


public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    private MoviePresenter mMoviePresenter= new MoviePresenter();

    public MovieGridAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_grid, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMoviePresenter.getMovieList().get(position);
        Glide.with(holder.mMoviePosterImageView.getContext()).load(movie.getPosterPath())
                .centerCrop()
                .into(holder.mMoviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return mMoviePresenter.getMovieList().size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView mMoviePosterImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }

}
