package com.rk.cleanarchitecture.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rk.cleanarchitecture.data.local.entity.MovieEntity;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface MovieDao {

    @Insert(onConflict = IGNORE)
    long[] insertMovies(List<MovieEntity> movieEntity);

    @Query("select * from movies")
    List<MovieEntity> getAllMovies();

    @Query("select * from movies where id=:id")
    MovieEntity getMovie(int id);
}
