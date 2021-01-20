package com.elderlyChild.dake.viewModels

import androidx.lifecycle.ViewModel
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.repositories.RestaurantRepository

class HomePageViewModel(private val restaurantRepository: RestaurantRepository ) : ViewModel() {
    var restaurantListLiveData = restaurantRepository.getAllRestaurants()

}