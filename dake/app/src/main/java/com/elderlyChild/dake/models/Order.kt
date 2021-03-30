package com.elderlyChild.dake.models

import android.os.Parcel
import android.os.Parcelable
import com.elderlyChild.dake.models.discounts.OrderDiscount
import java.io.Serializable
import java.time.Instant

data class Order(var customerID: String?=null, var restaurantID: String?=null,
                 var customerName: String?=null, var restaurantName: String?=null,
                 var status: String?=null, var basePrice : Double?=null,
                 var created: Instant?=null, var currencyCode: String?=null,
                 var orderNotes: String?=null, var totalPrice: Double?= 0.0,
                 var hasPayed: Boolean?=false):Model(), Parcelable{

    var completed: Instant?=null

    var items: List<Pair<MenuItem,Int?>>?= listOf<Pair<MenuItem,Int>>()
    var discounts: List<OrderDiscount>?= listOf<OrderDiscount>()

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble()
    ){
        created=Instant.ofEpochMilli(parcel.readLong())
        currencyCode=parcel.readString()
        orderNotes=parcel.readString()
        totalPrice=parcel.readDouble()
        hasPayed = parcel.readInt()==1
    }

    fun updateBasePrice(){
        var price=0.0
        for(item in items!!){
            price+=item.first.price
        }
        basePrice=price
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(customerID)
        parcel.writeString(restaurantID)
        parcel.writeString(customerName)
        parcel.writeString(restaurantName)
        parcel.writeString(status)
        parcel.writeValue(basePrice)
        parcel.writeValue(created?.toEpochMilli())
        parcel.writeString(currencyCode)
        parcel.writeString(orderNotes)
        parcel.writeValue(totalPrice)
        parcel.writeValue(if (hasPayed==true) 1 else 0 )
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        const val STATUS_COMPLETE = "complete"
        const val STATUS_ACTIVE = "active"
        const val STATUS_CANCELLED = "cancelled"
        const val STATUS_ERROR = "error"
        const val STATUS_CREATED="created"

        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}