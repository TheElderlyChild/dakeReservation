package com.elderlyChild.dake.models

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class BusinessHours (private var monday: Pair<LocalTime,LocalTime>?,
                     private var tuesday: Pair<LocalTime,LocalTime>?,
                     private var wednesday: Pair<LocalTime,LocalTime>?,
                     private var thursday: Pair<LocalTime,LocalTime>?,
                     private var friday: Pair<LocalTime,LocalTime>?,
                     private var saturday: Pair<LocalTime,LocalTime>?,
                     private var sunday: Pair<LocalTime,LocalTime>?){

    private var exceptions: BusinessHourException? = null

    /*
    fun isAvailable(dateTime: LocalDateTime): Boolean{
        var dayWorkHours : Pair<LocalTime,LocalTime>
        when(dateTime.){}
    }

     */

    companion object {
        fun fromList(list: List<Pair<LocalTime,LocalTime>>) : BusinessHours{
            return BusinessHours(list[0], list[1], list[2], list[3], list[4], list[5], list[6])
        }
    }
}