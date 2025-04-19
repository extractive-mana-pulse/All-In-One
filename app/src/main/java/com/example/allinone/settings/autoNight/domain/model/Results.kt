package com.example.allinone.settings.autoNight.domain.model

import com.google.gson.annotations.SerializedName

data class Results (

  @SerializedName("date"        ) var date       : String? = null,
  @SerializedName("sunrise"     ) var sunrise    : String? = null,
  @SerializedName("sunset"      ) var sunset     : String? = null,
  @SerializedName("first_light" ) var firstLight : String? = null,
  @SerializedName("last_light"  ) var lastLight  : String? = null,
  @SerializedName("dawn"        ) var dawn       : String? = null,
  @SerializedName("dusk"        ) var dusk       : String? = null,
  @SerializedName("solar_noon"  ) var solarNoon  : String? = null,
  @SerializedName("golden_hour" ) var goldenHour : String? = null,
  @SerializedName("day_length"  ) var dayLength  : String? = null,
  @SerializedName("timezone"    ) var timezone   : String? = null,
  @SerializedName("utc_offset"  ) var utcOffset  : Int?    = null

)