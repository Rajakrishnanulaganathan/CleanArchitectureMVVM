package com.rk.cleanarchitecture.data.local;

import android.content.Context;

import com.rk.cleanarchitecture.data.local.dao.MovieDao;
import com.rk.cleanarchitecture.data.local.entity.MovieEntity;

import java.util.List;

import javax.inject.Singleton;


public class Moviedatabasehelper {

    private MovieDao movieDao;
    private Appdatabase appdatabase;

    public Moviedatabasehelper(Context context) {
        appdatabase=Appdatabase.getInstance(context);
        movieDao = appdatabase.movieDao();
    }

    public List<MovieEntity>getAllmoviesfromdb() {
        return movieDao.getallmovies();}


    public void savetodb(List<MovieEntity> movies) {
        if(!movies.isEmpty()){
        movieDao.insertmovies(movies);
        }
    }

    public MovieEntity getmovie(int id) {
        return movieDao.getmovie(id) ;
    }
}
