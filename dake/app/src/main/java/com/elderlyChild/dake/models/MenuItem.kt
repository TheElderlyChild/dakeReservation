package com.elderlyChild.dake.models

data class MenuItem(var name : String? = null, var description : String?= null,
                    var category: String? = null, var basePrice: Double?= null,
                    var isAvailable: Boolean?= null): Model() {

    var price : Double = 0.0
    var locationStr : String?= null

    init {
        updatePriceWithBasePrice()
    }

    fun updatePriceWithBasePrice(){
        if (basePrice != null) {
            price += basePrice!!
        }
    }
}