package com.example.acropedia.data.model

import com.google.gson.annotations.SerializedName

data class SearchDto(
    @SerializedName("sf")
    val sf:String,
    @SerializedName("lfs")
    val lfs:List<LongFormListDto>
)

data class LongFormListDto(
    @SerializedName("lf")
    val lf:String,
    @SerializedName("freq")
    val freq:Int,
    @SerializedName("since")
    val since:Int,
    @SerializedName("vars")
    val vars:List<LongFormDto>
)

data class LongFormDto(
    @SerializedName("lf")
    val lf:String,
    @SerializedName("freq")
    val freq:Int,
    @SerializedName("since")
    val since:Int
)
