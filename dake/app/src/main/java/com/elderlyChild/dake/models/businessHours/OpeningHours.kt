package com.elderlyChild.dake.models.businessHours

import android.util.Log
import java.time.*


class OpeningHours {
    var monday: List<OpeningPeriod> = arrayListOf<OpeningPeriod>()
    var tuesday: List<OpeningPeriod> = arrayListOf<OpeningPeriod>()
    var wednesday: List<OpeningPeriod> = arrayListOf<OpeningPeriod>()
    var thursday: List<OpeningPeriod> = arrayListOf<OpeningPeriod>()
    var friday: List<OpeningPeriod> = arrayListOf<OpeningPeriod>()
    var saturday: List<OpeningPeriod> = arrayListOf<OpeningPeriod>()
    var sunday: List<OpeningPeriod> = arrayListOf<OpeningPeriod>()

    var exceptions: List<IrregularOpeningHours> = arrayListOf<IrregularOpeningHours>()

    fun isAvailable(dateTime: LocalDateTime): Boolean{
        for(exception in exceptions){
            //Return true if available on exception date
            if(exception.isAvailable(dateTime)) return true

            //Return false if not available on exception date
            if(exception.isDate(dateTime)) return false
        }

        var result=false

        var dayWorkHours : List<OpeningPeriod> = when(dateTime.dayOfWeek){
            DayOfWeek.MONDAY -> monday
            DayOfWeek.TUESDAY -> tuesday
            DayOfWeek.WEDNESDAY -> wednesday
            DayOfWeek.THURSDAY -> thursday
            DayOfWeek.FRIDAY -> friday
            DayOfWeek.SATURDAY-> saturday
            DayOfWeek.SUNDAY -> sunday
        }

        for(openingPeriod in dayWorkHours){
            if(openingPeriod.isDuringPeriod(dateTime.toLocalTime())){result=true}
        }

        return result
    }

    fun isAvailable(date: LocalDate): Boolean {
        var result = false
        for(exception in exceptions){
            //Return true if available on exception date
            if(exception.isDate(date.atStartOfDay())) {
                return !exception.isVacation()
            }
        }

        var dayWorkHours : List<OpeningPeriod> = when(date.dayOfWeek){
            DayOfWeek.MONDAY -> monday
            DayOfWeek.TUESDAY -> tuesday
            DayOfWeek.WEDNESDAY -> wednesday
            DayOfWeek.THURSDAY -> thursday
            DayOfWeek.FRIDAY -> friday
            DayOfWeek.SATURDAY-> saturday
            DayOfWeek.SUNDAY -> sunday
        }

        if (dayWorkHours.isNotEmpty()){result=true}

        return result
    }

    fun getTimeSlotList(date: LocalDate): List<LocalTime> {
        var timeSlotList = arrayListOf<LocalTime>()
        if (!isAvailable(date)){
            return timeSlotList
        }

        var dayWorkHours : List<OpeningPeriod> = when(date.dayOfWeek){
            DayOfWeek.MONDAY -> monday
            DayOfWeek.TUESDAY -> tuesday
            DayOfWeek.WEDNESDAY -> wednesday
            DayOfWeek.THURSDAY -> thursday
            DayOfWeek.FRIDAY -> friday
            DayOfWeek.SATURDAY-> saturday
            DayOfWeek.SUNDAY -> sunday
        }

        for(openingPeriod in dayWorkHours){
            var difference = Duration.between(openingPeriod.startTime,openingPeriod.endTime).toMinutes().toInt()
            var noTimeSlots=difference/30
            for (timeSlot in 0..noTimeSlots){
                timeSlotList.add(openingPeriod.startTime.plusMinutes((timeSlot*30).toLong()))
            }
        }

        return timeSlotList
    }

    companion object {
        const val TAG = "OpeningHours"

        fun fromMap( map : Map<String, List<String>>): OpeningHours{
            var result = OpeningHours()
            result.monday= toOpeningPeriods(map["monday"])
            result.tuesday= toOpeningPeriods(map["tuesday"])
            result.wednesday= toOpeningPeriods(map["wednesday"])
            result.thursday= toOpeningPeriods(map["thursday"])
            result.friday= toOpeningPeriods(map["friday"])
            result.saturday= toOpeningPeriods(map["saturday"])
            result.sunday= toOpeningPeriods(map["sunday"])
            return result
        }

        fun toOpeningPeriods(list : List<String>? ): List<OpeningPeriod>{
            var result = arrayListOf<OpeningPeriod>()
            var openingPeriod : OpeningPeriod
            for (item in list?: listOf()){
                openingPeriod = OpeningPeriod(
                        LocalTime.of(item.substring(0,2).toInt(), item.substring(2,4).toInt()),
                        LocalTime.of(item.substring(4,6).toInt(), item.substring(6,8).toInt())
                )
                result.add(openingPeriod)
            }
            return result
        }
    }
}