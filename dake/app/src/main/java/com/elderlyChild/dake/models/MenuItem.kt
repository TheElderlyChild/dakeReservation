package com.elderlyChild.dake.models

import java.time.Duration
import java.time.LocalTime

data class MenuItem(var name: String?=null,
                    var price: Double?=null,
                    var description: String?=null,
                    var category: String?=null,
                    var isAvailable: Boolean?=null
                    ): Model()