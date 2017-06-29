package info.androidhive.retrofit.rest;

import info.androidhive.retrofit.model.Movie;
import info.androidhive.retrofit.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MoviesResponse> searchMovie(@Query("query") String query, @Query("api_key") String apiKey);

}
