package com.rk.cleanarchitecture.data.remote.repository;

import androidx.annotation.NonNull;

import com.rk.cleanarchitecture.data.NetworkBoundResource;
import com.rk.cleanarchitecture.data.Resource;
import com.rk.cleanarchitecture.data.local.dao.MovieDao;
import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.data.remote.api.ApiService;
import com.rk.cleanarchitecture.data.remote.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class NetworkRepository {
    private ApiService apiService;
    private MovieDao mMovieDao;


    public NetworkRepository(MovieDao movieDao, ApiService apiService) {
        this.mMovieDao = movieDao;
        this.apiService = apiService;
    }

    public Observable<Resource<List<MovieEntity>>> getMoviesResponseMutableLiveData(final String apikey) {
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>() {
            @Override
            protected void saveCallResult(@NonNull MoviesResponse response) {
                List<MovieEntity> movieEntities = new ArrayList<>();
                if (response != null) {
                    for (MovieEntity movieEntity : response.getResults()) {
                        movieEntity.setPage(Long.valueOf(response.getPage()));
                        if (response.getTotalPages() != null)
                            movieEntity.setTotalPages(Long.valueOf(response.getTotalPages()));
                        movieEntities.add(movieEntity);
                    }
                }
                mMovieDao.insertMovies(movieEntities);
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<List<MovieEntity>> loadFromDb() {
                List<MovieEntity> movieEntities = mMovieDao.getAllMovies();
                if (movieEntities == null || movieEntities.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(movieEntities);
            }

            @NonNull
            @Override
            protected Observable<Resource<MoviesResponse>> createCall() {
                return apiService.getPopularMovieResults(apikey).flatMap(movieEntities -> Observable.just(movieEntities == null
                        ? Resource.error("", new MoviesResponse()) : Resource.success(movieEntities)));
            }
        }.getAsObservable();

    }

    public Observable<Resource<MovieEntity>> getMoviesDetailsResponseMutableLiveData(String movie_id) {
        return new NetworkBoundResource<MovieEntity, MovieEntity>() {
            @Override
            protected void saveCallResult(@NonNull MovieEntity item) {

            }

            @Override
            protected boolean shouldFetch() {
                return false;
            }

            @NonNull
            @Override
            protected Flowable<MovieEntity> loadFromDb() {
                return Flowable.just(mMovieDao.getMovie(Integer.parseInt(movie_id)));
            }

            @NonNull
            @Override
            protected Observable<Resource<MovieEntity>> createCall() {
                return apiService.fetchMovieDetail(movie_id)
                        .flatMap(movieEntity -> Observable.just(movieEntity == null
                                ? Resource.error("", new MovieEntity())
                                : Resource.success(movieEntity)));
            }
        }.getAsObservable();
    }


}
