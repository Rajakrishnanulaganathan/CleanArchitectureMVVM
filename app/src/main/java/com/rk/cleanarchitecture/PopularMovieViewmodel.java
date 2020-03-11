package com.rk.cleanarchitecture;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rk.cleanarchitecture.data.local.dao.MovieDao;
import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.data.remote.NetworkService;
import com.rk.cleanarchitecture.data.remote.api.ApiService;
import com.rk.cleanarchitecture.data.remote.repository.NetworkRepository;
import com.rk.cleanarchitecture.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class PopularMovieViewmodel extends ViewModel {
    private MutableLiveData<List<MovieEntity>> moviesResponseMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<MovieEntity> moviesResponseDetailsMutableLiveData=new MutableLiveData<>();
    private NetworkRepository networkRepository;

    @Inject
    public PopularMovieViewmodel(MovieDao movieDao, ApiService apiService) {
        networkRepository=new NetworkRepository(movieDao,apiService);
       // networkRepository= NetworkRepository.networkRepositoryGetinstance();
    }

    public void init(){
           networkRepository.getMoviesResponseMutableLiveData(Constants.API_KEY).subscribe(resource -> {
                if(resource.isLoaded()) getMoviesResponseMutableLiveData().postValue(resource.data);
            });

    }

    public MutableLiveData<List<MovieEntity>> getMoviesResponseMutableLiveData(){
        Log.d("getMoviesResponse", "called");

        return moviesResponseMutableLiveData;
    }

    public MutableLiveData<MovieEntity> getMoviesResponseDetailsMutableLiveData(){
        Log.d("getMoviesResponse", "called");

        return moviesResponseDetailsMutableLiveData;
    }

    public void getmoviedetails(String key){
         networkRepository.getMoviesDetailsResponseMutableLiveData(key).subscribe(resource->{
             if(resource.isLoaded()) getMoviesResponseDetailsMutableLiveData().postValue(resource.data);
         });
    }




}

