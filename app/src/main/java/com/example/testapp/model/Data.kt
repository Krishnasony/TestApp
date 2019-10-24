package com.example.testapp.model

import android.arch.persistence.room.Ignore
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("place")
    val place: String?,
    @SerializedName("url")
    val url: String?
): Parcelable{
    @Ignore
     constructor():this(
        place = "",
        url = ""
    )

}