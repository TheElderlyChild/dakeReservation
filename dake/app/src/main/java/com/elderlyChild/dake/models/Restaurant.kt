package com.elderlyChild.dake.models

import android.os.Parcel
import android.os.Parcelable


data class Restaurant(var name: String?=null, var phoneNo: String?=null,
                      var address: String?=null, var description: String?=null,
                      var rating: Double?=null, var category: String?=null,
                      var imageLocation: String?=null, var capacity: Int?=null,
                      var ratingsNo: Int?=null): Model(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {withId<Restaurant>(parcel.readString())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phoneNo)
        parcel.writeString(address)
        parcel.writeString(description)
        parcel.writeDouble(rating?:0.0)
        parcel.writeString(category)
        parcel.writeString(imageLocation)
        parcel.writeInt(capacity?:0)
        parcel.writeInt(ratingsNo?:0)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }

}