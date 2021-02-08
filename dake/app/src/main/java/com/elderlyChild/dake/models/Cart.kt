package com.elderlyChild.dake.models

import com.elderlyChild.dake.models.discounts.OrderDiscount

class Cart{
    var currentRestaurant : Restaurant?=null
    var currencyCode:String?=null
    var items: List<Pair<MenuItem,Int?>>?= listOf<Pair<MenuItem,Int>>()
    var discounts: List<OrderDiscount>?= listOf<OrderDiscount>()

    fun addToCart(){

    }

    fun removeFromCart():Boolean{
        var result = false

        return result
    }
}