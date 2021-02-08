package com.elderlyChild.dake.models.discounts

import com.elderlyChild.dake.models.MenuItem
import java.time.Instant

class ItemDiscount(targetType : String?= null, created : Instant?= null,
                   validFrom : Instant?= null, validTo : Instant?= null,
                   maximumDiscount : Double? = null, minimumAmountReq: Double? = 0.0,
                   discountValue : Double?= null, discountType : String?= null): Discount() {

    var targetProductID : String?= null
    var targetCategoryName : String?= null

    private fun isTargetItem(itemId : String, categoryName: String): Boolean{
        return when(target){
            TARGET_ALL_ITEMS -> true
            TARGET_SPECIFIC_ITEM -> targetProductID.equals(itemId)
            TARGET_CATEGORY -> targetCategoryName.equals(categoryName)
            else -> false
        }
    }

    fun applyTo (item : MenuItem){
        if (!isValid()) return
        if (!isTargetItem(item.id.toString(), item.category.toString())) return
        when(discountType){
            REDUCTION_PERCENT -> item.price = item.price!! * (100- discountValue!!)/100
            REDUCTION_FIXED_AMOUNT -> if(item.price != null)item.price = item.price!!- discountValue!!
        }
    }

}