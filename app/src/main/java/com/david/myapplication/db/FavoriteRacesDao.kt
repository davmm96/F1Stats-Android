package com.david.myapplication.db

import androidx.room.*
import com.david.myapplication.model.FavoriteRace

@Dao
interface FavoriteRacesDao {

    @Query("SELECT * FROM FavoriteRace")
    fun getAll(): List<FavoriteRace>

    @Query("SELECT id FROM FavoriteRace")
    fun getAllIds(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(race : FavoriteRace)

    @Delete
    fun delete(race: FavoriteRace)
}