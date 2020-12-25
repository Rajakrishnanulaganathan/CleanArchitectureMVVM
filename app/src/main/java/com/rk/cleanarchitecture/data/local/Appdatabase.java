package com.rk.cleanarchitecture.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rk.cleanarchitecture.data.local.converter.Integertypeconverter;
import com.rk.cleanarchitecture.data.local.dao.MovieDao;
import com.rk.cleanarchitecture.data.local.entity.MovieEntity;

@Database(entities = MovieEntity.class, version = 2, exportSchema = false)
@TypeConverters(Integertypeconverter.class)
public abstract class Appdatabase extends RoomDatabase {


    private static volatile Appdatabase INSTANCE;

    public static Appdatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (Appdatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static Appdatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                Appdatabase.class, "Entertainment.db")
                .allowMainThreadQueries().build();
    }

    public abstract MovieDao movieDao();

}
