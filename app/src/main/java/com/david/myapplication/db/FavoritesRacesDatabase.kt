package com.david.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.david.myapplication.model.RaceItem


@Database(entities = [RaceItem::class], version = 1, exportSchema = false)
abstract class FavoritesRacesDatabase : RoomDatabase() {
    abstract fun favoritesRacesDao(): FavoriteRacesDao
}