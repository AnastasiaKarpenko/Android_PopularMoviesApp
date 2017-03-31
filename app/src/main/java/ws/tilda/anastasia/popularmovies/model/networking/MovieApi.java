package ws.tilda.anastasia.popularmovies.model.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ws.tilda.anastasia.popularmovies.model.model_objects.Response;

public class MovieApi {
    private static final String APIKEY = "PutYourApiKey";
    private static final String APIPATH = " https://api.themoviedb.org/3/";


    public static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(APIPATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MovieService provideMovieService() {
        return provideRetrofit().create(MovieService.class);
    }

    public interface MovieService {
        @GET("discover/movie?api_key=" + APIKEY)
        Call<Response> getMovies();

        @GET("movie/{criteria}?api_key=" + APIKEY)
        Call<Response> sortMoviesByCriteria(@Path ("criteria") String string);
    }

}
