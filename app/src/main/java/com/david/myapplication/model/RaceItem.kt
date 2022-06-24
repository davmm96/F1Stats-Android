package com.david.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RaceItem (
    @PrimaryKey var id: Int = 0,
    var name: String,
    var city: String,
    var laps: Int
)