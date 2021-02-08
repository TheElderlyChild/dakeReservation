package com.elderlyChild.dake.models.businessHours

import java.lang.Exception
import java.time.LocalTime

class OpeningPeriod (var startTime: LocalTime, var endTime: LocalTime){

    fun isDuringPeriod(time: LocalTime): Boolean{
        return time in startTime..endTime
    }

    override fun equals(other: Any?): Boolean {
        try{var otherPeriod = other as OpeningPeriod}
        catch(e: Exception){return false}
        return startTime.equals(startTime) && endTime.equals(endTime)
    }
}