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
        interstellar.setOverview("Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.");
        interstellar.setVoteAverage(8.0);
        interstellar.setReleaseDate("2014-11-05");

        mMovies.add(interstellar);

        Movie arrival = new Movie();
        arrival.setPosterPath("http://image.tmdb.org/t/p/w185//yIZ1xendyqKvY3FGeeUYUd5X9Mm.jpg");
        arrival.setTitle("Arrival");
        arrival.setOverview("Taking place after alien crafts land around the world, an expert linguist is recruited by the military to determine whether they come in peace or are a threat.");
        arrival.setVoteAverage(6.9);
        arrival.setReleaseDate("2016-11-10");
        mMovies.add(arrival);

        Movie doctorStrange = new Movie();
        doctorStrange.setPosterPath("http://image.tmdb.org/t/p/w185//tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg");
        doctorStrange.setTitle("Doctor Strange");
        doctorStrange.setOverview("After his career is destroyed, a brilliant but arrogant surgeon gets a new lease on life when a sorcerer takes him under his wing and trains him to defend the world against evil.");
        doctorStrange.setVoteAverage(6.9);
        doctorStrange.setReleaseDate("2016-10-25");
        mMovies.add(doctorStrange);

        Movie rings = new Movie();
        rings.setPosterPath("http://image.tmdb.org/t/p/w185//biN2sqExViEh8IYSJrXlNKjpjxx.jpg");
        rings.setTitle("Rings");
        rings.setOverview("Julia becomes worried about her boyfriend, Holt when he explores the dark urban legend of a mysterious videotape said to kill the watcher seven days after viewing. She sacrifices herself to save her boyfriend and in doing so makes a horrifying discovery: there is a movie within the movie that no one has ever seen before.");
        rings.setVoteAverage(4.7);
        rings.setReleaseDate("2017-02-01");
        mMovies.add(rings);

        MovieStore.setMovies(mMovies);
    }

    public List<Movie> getMovieList() {
        return MovieStore.getMovies();

    }
}
