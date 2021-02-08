package com.elderlyChild.dake.models.discounts

import com.elderlyChild.dake.models.Model
import java.time.Instant

abstract class Discount(var target : String?= null, var created : Instant?= null,
                        var validFrom : Instant?= null, var validTo : Instant?= null,
                        var maximumDiscount : Double? = null, var minimumAmountReq: Double? = 0.0,
                        var discountValue : Double?= null, var discountType : String?= null): Model() {

    fun isValid():Boolean{
        val currentInstant=Instant.now()
        return (validFrom!! <=currentInstant) && (currentInstant<=validTo!!)
    }

    companion object{
        //Specific target types
        const val TARGET_SPECIFIC_ITEM = "specificItem"
        const val TARGET_ALL_ITEMS = "allItems"
        const val TARGET_CATEGORY = "category"

        //Order target types
        const val TARGET_DISCOUNT_CODE = "discountCode"
        const val TARGET_ORDER = "order"

        //Supported methods of reduction
        const val REDUCTION_PERCENT = "percent"
        const val REDUCTION_FIXED_AMOUNT = "fixedAmount"
        const val REDUCTION_BUY_GET_FREE = "buyGetFree" //Where discountValue is how many items required for 1 free
    }
}