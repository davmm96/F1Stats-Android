package com.david.myapplication.model

import com.google.gson.annotations.SerializedName

data class Races(@SerializedName("response") val races: Array<Race>)
data class Race(val id: Int,
                val competition: Competition,
                val circuit: Circuit,
                val season: Int,
                val type: String,
                val laps: Laps,
                val fastest_lap: FastestLap,
                val distance: String,
                val timezone: String,
                val date: String,
                val weather: String,
                val status: String)

data class Competition(val id: Int,
                       val name: String,
                       val location: Location)

data class Location(val country: String,
                    val city: String)

data class Circuit(val id: Int,
                   val name: String,
                   val image: String)

data class Laps(val current: Int,
                val total: Int)

data class FastestLap(val driver: Driver,
                      val time: String)

data class Driver(val id: Int)