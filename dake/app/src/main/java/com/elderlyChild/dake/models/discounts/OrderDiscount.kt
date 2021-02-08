package com.elderlyChild.dake.models.discounts

import com.elderlyChild.dake.models.Order
import java.time.Instant

class OrderDiscount(target : String?= null, created : Instant?= null,
                      validFrom : Instant?= null, validTo : Instant?= null,
                      maximumDiscount : Double? = null, minimumAmountReq: Double? = 0.0,
                      discountValue : Double?= null, discountType : String?= null): Discount() {

    var targetDiscountCode : String?= null

    fun isDiscountCode(discountCode : String): Boolean{
        return targetDiscountCode.equals(discountCode)
    }

    private fun meetsAmountRequirements(basePrice: Double?, finalPrice : Double?): Boolean{
        var result = true
        if(minimumAmountReq!=null){
            result = result && basePrice!!>= minimumAmountReq!!
        }
        return result
    }

    private fun aboveDiscountLimit(basePrice: Double?, finalPrice : Double?): Boolean{
        var result = true

        if(maximumDiscount!=null){
            result = result && (finalPrice!! - basePrice!!)<= maximumDiscount!!
        }
        return result
    }

    fun applyTo (order: Order, discountCode:String?=null){
        if (!isValid()) return
        if (!meetsAmountRequirements(order.basePrice,order.totalPrice)) return
        if (!(target == TARGET_DISCOUNT_CODE && isDiscountCode(discountCode!!))) return

        //Applies reduction
        when(discountType){
            REDUCTION_PERCENT -> order.totalPrice = order.totalPrice!! * (100- discountValue!!)/100
            REDUCTION_FIXED_AMOUNT -> if(order.totalPrice != null)order.totalPrice =
                    order.totalPrice!!- discountValue!!
        }

        //Ensures discount doesn't exceed limit
        if (aboveDiscountLimit(order.basePrice,order.totalPrice)){
            order.totalPrice = order.totalPrice!!- maximumDiscount!!
        }
    }

}