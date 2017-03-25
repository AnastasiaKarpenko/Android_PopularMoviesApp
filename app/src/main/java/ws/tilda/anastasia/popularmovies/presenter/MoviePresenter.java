package ws.tilda.anastasia.popularmovies.presenter;

import java.util.ArrayList;
import java.util.List;

import ws.tilda.anastasia.popularmovies.model.model_objects.Movie;
import ws.tilda.anastasia.popularmovies.model.model_objects.MovieStore;

public class MoviePresenter {

    private List<Movie> mMovies;

    public void loadMovieList() {
        mMovies = new ArrayList<>();

        Movie interstellar = new Movie();
        interstellar.setPosterPath("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg");
        interstellar.setTitle("Interstellar");
        mMovies.add(interstellar);

        Movie arrival = new Movie();
        arrival.setPosterPath("http://image.tmdb.org/t/p/w185//yIZ1xendyqKvY3FGeeUYUd5X9Mm.jpg");
        arrival.setTitle("Arrival");
        mMovies.add(arrival);

        Movie doctorStrange = new Movie();
        doctorStrange.setPosterPath("http://image.tmdb.org/t/p/w185//tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg");
        doctorStrange.setTitle("Doctor Strange");
        mMovies.add(doctorStrange);

        Movie rings = new Movie();
        rings.setPosterPath("http://image.tmdb.org/t/p/w185//biN2sqExViEh8IYSJrXlNKjpjxx.jpg");
        rings.setTitle("Rings");
        mMovies.add(rings);

        MovieStore.setMovies(mMovies);
    }

    public List<Movie> getMovieList() {
        return MovieStore.getMovies();

    }
}
