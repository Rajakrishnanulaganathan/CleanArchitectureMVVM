package com.rk.cleanarchitecture.di.module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.rk.cleanarchitecture.MovieApplication;
import com.rk.cleanarchitecture.data.local.Appdatabase;
import com.rk.cleanarchitecture.data.local.dao.MovieDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {
    @Provides
    @Singleton
    Appdatabase providedb(@NonNull Application application){
        return Room.databaseBuilder(application,
                Appdatabase.class, "Entertainment.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    MovieDao providemoviedao(@NonNull Appdatabase appdatabase){
     return appdatabase.movieDao();
    }
}
