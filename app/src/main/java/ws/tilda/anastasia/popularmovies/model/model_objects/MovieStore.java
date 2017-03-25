package ws.tilda.anastasia.popularmovies.model.model_objects;

import java.util.ArrayList;
import java.util.List;

public class MovieStore {
    private static List<Movie> movies = new ArrayList<>();

    public static List<Movie> getMovies() {
        return movies;
    }

    public static void setMovies(List<Movie> movies) {
        MovieStore.movies = movies;
    }
}
