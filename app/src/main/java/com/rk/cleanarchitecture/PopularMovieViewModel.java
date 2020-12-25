package com.rk.cleanarchitecture;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rk.cleanarchitecture.data.local.dao.MovieDao;
import com.rk.cleanarchitecture.data.local.entity.MovieEntity;
import com.rk.cleanarchitecture.data.remote.api.ApiService;
import com.rk.cleanarchitecture.data.remote.repository.NetworkRepository;
import com.rk.cleanarchitecture.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class PopularMovieViewModel extends BaseViewModel {
    private MutableLiveData<List<MovieEntity>> moviesResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<MovieEntity> moviesResponseDetailsMutableLiveData = new MutableLiveData<>();
    private NetworkRepository networkRepository;

    @Inject
    public PopularMovieViewModel(MovieDao movieDao, ApiService apiService) {
        networkRepository = new NetworkRepository(movieDao, apiService);
        // networkRepository= NetworkRepository.networkRepositoryGetinstance();
    }

    @SuppressLint("CheckResult")
    public void init() {
        networkRepository.getMoviesResponseMutableLiveData(Constants.API_KEY).subscribe(resource -> {
            if (resource.isLoaded()) getMoviesResponseMutableLiveData().postValue(resource.data);
        });

    }

    public MutableLiveData<List<MovieEntity>> getMoviesResponseMutableLiveData() {
        return moviesResponseMutableLiveData;
    }

    public MutableLiveData<MovieEntity> getMoviesResponseDetailsMutableLiveData() {
        return moviesResponseDetailsMutableLiveData;
    }

    @SuppressLint("CheckResult")
    public void getmoviedetails(String key) {
        networkRepository.getMoviesDetailsResponseMutableLiveData(key).subscribe(resource -> {
            if (resource.isLoaded())
                getMoviesResponseDetailsMutableLiveData().postValue(resource.data);
        });
    }


}

