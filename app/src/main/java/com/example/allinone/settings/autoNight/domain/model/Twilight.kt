package com.example.allinone.settings.autoNight.domain.model

import com.google.gson.annotations.SerializedName


data class Twilight (

  @SerializedName("results" ) var results : Results = Results(),
  @SerializedName("status"  ) var status  : String? = null

)