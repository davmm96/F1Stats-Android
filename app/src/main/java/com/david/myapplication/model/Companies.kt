package com.david.myapplication.model

import com.google.gson.annotations.SerializedName

data class Companies(@SerializedName("empresas") val companies: Array<Company>)
data class Company(val title: String, val visibleUrl: String, val icon: String)