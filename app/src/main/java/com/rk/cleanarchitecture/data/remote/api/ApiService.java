package com.rk.cleanarchitecture.data.remote.api;

import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.data.remote.model.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovieResults(@Query("api_key") String apikey);


    @GET("/3/movie/{movieId}")
    Observable<MovieEntity> fetchMovieDetail(@Path("movieId") String movieId);
}
