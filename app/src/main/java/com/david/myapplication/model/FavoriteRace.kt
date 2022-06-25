package com.david.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavoriteRace (
    @PrimaryKey var id: Int = 0,
    var country: String,
    var name: String,
    var laps: Int,
    var season: Int,
    var urlImage: String,
    var isFavorite: Boolean = true
)