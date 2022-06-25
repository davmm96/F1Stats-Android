package com.david.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.david.myapplication.model.FavoriteRace

@Database(entities = [FavoriteRace::class], version = 1, exportSchema = false)
abstract class FavoriteRacesDatabase : RoomDatabase() {
    abstract fun favoriteRacesDao(): FavoriteRacesDao
}