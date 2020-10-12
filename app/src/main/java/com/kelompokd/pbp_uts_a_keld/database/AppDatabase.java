package com.kelompokd.pbp_uts_a_keld.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kelompokd.pbp_uts_a_keld.model.Car;

@Database(entities = {Car.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CarDao carDao();
}



