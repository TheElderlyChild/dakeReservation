package com.elderlyChild.dake

import com.elderlyChild.dake.repositories.RestaurantRepository
import com.elderlyChild.dake.repositories.StorageRepository
import com.elderlyChild.dake.repositories.UserRepository

class AppContainer {
    val userRepository = UserRepository()
    val restaurantRepository = RestaurantRepository()
    val storageRepository = StorageRepository()
}