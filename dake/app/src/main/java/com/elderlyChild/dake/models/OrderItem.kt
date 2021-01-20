package com.elderlyChild.dake.models

data class OrderItem(private var name: String?=null,
                     private var price: Double?=null,
                     private var description: String?=null,
                     private var category: String?=null,
                     private var amount: Int?=null
): Model()