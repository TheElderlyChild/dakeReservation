package com.elderlyChild.dake.models

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

data class Restaurant (var name : String? = null, var address: String?=null,
                       var cuisine : String? = null, var noRatings: Int?= null,
                       var avgRating : Double?= null, var imgLocation : String?= null) : Model(), Parcelable{

    var description : String?= null
    var cancelPolicy : String?= null
    var phoneNo : String?= null
    var currencyCode : String?= null
    var dateAdded : String?=null

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readString()) {
        withId<Restaurant>(parcel.readString())
        description = parcel.readString()
        cancelPolicy = parcel.readString()
        phoneNo = parcel.readString()
        currencyCode = parcel.readString()
        dateAdded = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //Constructor values
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(cuisine)
        parcel.writeInt(noRatings?:0)
        parcel.writeDouble(avgRating?:0.0)
        parcel.writeString(imgLocation)

        //Initialisation Values
        parcel.writeString(id)
        parcel.writeString(description)
        parcel.writeString(cancelPolicy)
        parcel.writeString(phoneNo)
        parcel.writeString(currencyCode)
        parcel.writeString(dateAdded)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun addInfoMap(map : Map<String, Any>){
        cancelPolicy=map["cancelPolicy"].toString()
        currencyCode=map["currencyCode"].toString()
        description=map["description"].toString()
        phoneNo=map["phoneNo"].toString()
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