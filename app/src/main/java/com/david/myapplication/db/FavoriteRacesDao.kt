package com.david.myapplication.db

import androidx.room.*
import com.david.myapplication.model.RaceItem

@Dao
interface FavoriteRacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(race : RaceItem)

    @Delete
    fun delete(race: RaceItem)

    @Query("SELECT * FROM RaceItem")
    fun getAll(): List<RaceItem>
}