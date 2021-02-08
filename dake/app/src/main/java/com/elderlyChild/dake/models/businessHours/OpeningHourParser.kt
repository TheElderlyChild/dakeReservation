package com.elderlyChild.dake.models.businessHours

import java.time.format.DateTimeFormatter

class OpeningHourParser {
    fun toDisplayString(openingHours: OpeningHours): String{
        var result = ""
        if(openingHours.monday.isNotEmpty()){
            result+="Mon: ${representDay(openingHours.monday)} \n"
        }
        if(openingHours.tuesday.isNotEmpty()){
            result+="Tue: ${representDay(openingHours.tuesday)} \n"
        }
        if(openingHours.wednesday.isNotEmpty()){
            result+="Wed: ${representDay(openingHours.wednesday)} \n"
        }
        if(openingHours.thursday.isNotEmpty()){
            result+="Thu: ${representDay(openingHours.thursday)} \n"
        }
        if(openingHours.friday.isNotEmpty()){
            result+="Fri: ${representDay(openingHours.friday)} \n"
        }
        if(openingHours.saturday.isNotEmpty()){
            result+="Sat: ${representDay(openingHours.saturday)} \n"
        }
        if(openingHours.sunday.isNotEmpty()){
            result+="Sun: ${representDay(openingHours.sunday)} \n"
        }
        return result
    }

    fun representDay(dayPeriods : List<OpeningPeriod>):String{
        var result = ""
        if (dayPeriods.isNotEmpty()){
            for(openingPeriod in dayPeriods){
                result += " ${representPeriod(openingPeriod)}"
            }
        }
        return result
    }

    fun representPeriod(openingPeriod: OpeningPeriod): String{
        var result=""
        var formatter=DateTimeFormatter.ofPattern("hh:mm a")
        result = "${formatter.format(openingPeriod.startTime)} to " +
                "${formatter.format(openingPeriod.endTime)}"
        return result
    }
}