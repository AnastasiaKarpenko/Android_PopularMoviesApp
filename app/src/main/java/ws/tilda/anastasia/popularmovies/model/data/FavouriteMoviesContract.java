package ws.tilda.anastasia.popularmovies.model.data;

import android.provider.BaseColumns;

public class FavouriteMoviesContract {

    private FavouriteMoviesContract() {}

    public static final class FavouriteMoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "favouriteMovies";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_OVERVIEW = "movieOverview";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movieVoteAverage";
        public static final String COLUMN_MOVIE_POSTER_PATH = "moviePosterPath";

    }
}
