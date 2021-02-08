package com.elderlyChild.dake.models.businessHours

import java.time.LocalDate
import java.time.LocalDateTime

class IrregularOpeningHours (var date : LocalDate) {
    var reason: String?=null
    var openings: List<OpeningPeriod> = arrayListOf()

    fun isDate(dateTime : LocalDateTime): Boolean {
        return date == dateTime.toLocalDate()
    }


    fun isAvailable(dateTime: LocalDateTime): Boolean {
        if (!isDate(dateTime)) {
            return false
        }
        var result = false
        for (openingPeriod in openings) {
            if (openingPeriod.isDuringPeriod(dateTime.toLocalTime())) result = true
        }
        return result
    }

    fun isVacation(): Boolean{
        return openings.isEmpty()
    }
}