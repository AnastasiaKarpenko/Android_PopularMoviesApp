package ws.tilda.anastasia.popularmovies.model.networking;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ws.tilda.anastasia.popularmovies.model.model_objects.Response;

public class MovieApi {
    private static final String APIKEY = "putYourApiKeyHere";
    private static final String APIPATH = "https://api.themoviedb.org/3/";
    private static OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

    private static void getLogger() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
    }

    public static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(APIPATH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build())
                .build();
    }

    public static MovieService provideMovieService() {
        getLogger();
        return provideRetrofit().create(MovieService.class);
    }

    public interface MovieService {
        @GET("discover/movie?api_key=" + APIKEY)
        Call<Response> getDefaultMovieList();

        @GET("movie/{criteria}?api_key=" + APIKEY)
        Call<Response> sortMoviesByCriteria(@Path ("criteria") String string);
    }

}
