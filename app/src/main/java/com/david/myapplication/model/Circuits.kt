package com.david.myapplication.model

import com.google.gson.annotations.SerializedName

data class Circuits(@SerializedName("response") val circuits: Array<CircuitData>)
data class CircuitData(val id: Int,
                val name: String,
                val image: String,
                val competition: Competition,
                val first_grand_prix: Int,
                val laps: Int,
                val length: String,
                val race_distance: String,
                val lap_record: LapRecord,
                val capacity: Int,
                val opened: Int,
                val owner: String)

data class LapRecord(val time: String,
                   val driver: String,
                   val year: String)
