package com.example.testapp.model


import android.arch.persistence.room.Ignore
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(
    @SerializedName("data")
    val data: List<Data>?
):Parcelable {
 @Ignore
 constructor():this(
     data = null
 )


}
