package com.kelompokd.pbp_uas_a_keld.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kelompokd.pbp_uas_a_keld.model.Car;

import java.util.List;

@Dao
public interface CarDao {

    @Query("SELECT * FROM Car")
    List<Car> getAll();

    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);

}


