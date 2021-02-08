package com.elderlyChild.dake.models

import com.elderlyChild.dake.models.discounts.OrderDiscount
import java.time.Instant

data class Order(var customerID: String?=null, var restaurantID: String?=null,
                 var customerName: String?=null, var restaurantName: String?=null,
                 var status: String?=null, var basePrice : Double?=null,
                 var created: Instant?=null, var currencyCode: String?=null,
                 var orderNotes: String?=null, var totalPrice: Double?= 0.0,
                 var hasPayed: Boolean?=false){

    var completed: Instant?=null

    var items: List<Pair<MenuItem,Int?>>?= listOf<Pair<MenuItem,Int>>()
    var discounts: List<OrderDiscount>?= listOf<OrderDiscount>()

    fun updateBasePrice(){
        var price=0.0
        for(item in items!!){
            price+=item.first.price
        }
        basePrice=price
    }


    companion object{
        const val STATUS_COMPLETE = "complete"
        const val STATUS_ACTIVE = "active"
        const val STATUS_CANCELLED = "cancelled"
        const val STATUS_ERROR = "error"
    }
}