package com.rk.cleanarchitecture.data.remote.repository;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.rk.cleanarchitecture.data.NetworkBoundResource;
import com.rk.cleanarchitecture.data.Resource;
import com.rk.cleanarchitecture.data.local.Moviedatabasehelper;
import com.rk.cleanarchitecture.data.local.dao.MovieDao;
import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.data.remote.NetworkService;
import com.rk.cleanarchitecture.data.remote.api.ApiService;
import com.rk.cleanarchitecture.data.remote.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class NetworkRepository {
    private static NetworkRepository networkRepository;
    private static Moviedatabasehelper moviedatabasehelper;
    private MutableLiveData<MovieEntity> movieEntityMutableLiveData = new MutableLiveData<>();
    private ApiService apiService;
    private MutableLiveData<List<MovieEntity>> moviesResponseMutableLiveData = new MutableLiveData<List<MovieEntity>>();
    private MovieDao mMovieDao;


     public NetworkRepository(MovieDao movieDao, ApiService apiService) {
         this.mMovieDao=movieDao;
         this.apiService=apiService;
       // this.apiService = NetworkService.cteateService(ApiService.class);
    }

    public static NetworkRepository networkRepositoryGetinstance(Context context) {
        if (networkRepository == null) {
           // networkRepository = new NetworkRepository(movieDao, apiService);
          //  moviedatabasehelper = new Moviedatabasehelper(context);
        }
        return networkRepository;
    }

    public Observable<Resource<List<MovieEntity>>> getMoviesResponseMutableLiveData(final String apikey) {
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>() {
            @Override
            protected void saveCallResult(@NonNull MoviesResponse response) {
                List<MovieEntity> movieEntities = new ArrayList<>();
                if(response!=null){
                    for (MovieEntity movieEntity : response.getResults()) {
                        movieEntity.setPage(Long.valueOf(response.getPage()));
                        if (response.getTotalPages() != null)
                            movieEntity.setTotalPages(Long.valueOf(response.getTotalPages()));
                            movieEntities.add(movieEntity);
                    }
                    Log.d("response",String.valueOf(movieEntities.size()));


            }
                mMovieDao.insertmovies(movieEntities);
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<List<MovieEntity>> loadFromDb() {
                List<MovieEntity> movieEntities = mMovieDao.getallmovies();
                if(movieEntities == null || movieEntities.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(movieEntities);
            }

            @NonNull
            @Override
            protected Observable<Resource<MoviesResponse>> createCall() {
                return apiService.getPopularMovieResults(apikey).flatMap(movieEntities->Observable.just(movieEntities==null
                ?Resource.error("",new MoviesResponse()):Resource.success(movieEntities)));
            }
        }.getAsObservable();

    }

    public Observable<Resource<MovieEntity>> getMoviesDetailsResponseMutableLiveData(String movie_id){
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
                 return Flowable.just(mMovieDao.getmovie(Integer.valueOf(movie_id)));
             }

             @NonNull
             @Override
             protected Observable<Resource<MovieEntity>> createCall() {
                 return apiService.fetchMovieDetail( movie_id)
                         .flatMap(movieEntity -> Observable.just(movieEntity == null
                                 ? Resource.error("", new MovieEntity())
                                 : Resource.success(movieEntity)));
             }
         }.getAsObservable();
    }




}
