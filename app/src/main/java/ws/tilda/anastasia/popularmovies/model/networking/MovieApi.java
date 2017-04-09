package ws.tilda.anastasia.popularmovies.model.networking;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import timber.log.Timber;
import ws.tilda.anastasia.popularmovies.PopularMovies;
import ws.tilda.anastasia.popularmovies.model.modelobjects.MovieResponse;
import ws.tilda.anastasia.popularmovies.model.modelobjects.ReviewResponse;
import ws.tilda.anastasia.popularmovies.model.modelobjects.TrailerResponse;

public class MovieApi {
    private static final String APIKEY = "putYourApiKey";
    private static final String APIPATH = "https://api.themoviedb.org/3/";
    private static final String CACHE_CONTROL = "Cache-Control";

    private static OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();


    public static MovieService provideMovieService() {
        addLoggingInterceptor();
        return provideRetrofit().create(MovieService.class);
    }

    private static OkHttpClient provideOkHttpClient() {
        return okhttpClientBuilder
                .addInterceptor(provideOfflineCacheInterceptor())
                .cache(provideCache())
                .addNetworkInterceptor(provideCacheInterceptor())
                .build();
    }

    private static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(APIPATH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    private static void addLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
    }

    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(PopularMovies.getInstance().getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Timber.e(e, "Could not create Cache!");
        }
        return cache;
    }

    public static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    public static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!PopularMovies.hasNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    public interface MovieService {
        @GET("discover/movie?api_key=" + APIKEY)
        Call<MovieResponse> getDefaultMovieList();

        @GET("movie/{criteria}?api_key=" + APIKEY)
        Call<MovieResponse> sortMoviesByCriteria(@Path("criteria") String string);

        @GET("movie/{id}/videos?api_key=" + APIKEY)
        Call<TrailerResponse> fetchTrailersByMovieId(@Path("id") int id);

        @GET("movie/{id}/reviews?api_key=" + APIKEY)
        Call<ReviewResponse> fetchReviewsByMovieId(@Path("id") int id);
    }

}
