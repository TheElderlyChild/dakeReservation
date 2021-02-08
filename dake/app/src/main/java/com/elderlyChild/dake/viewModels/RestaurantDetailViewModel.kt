package com.elderlyChild.dake.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elderlyChild.dake.models.MenuItem
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.models.businessHours.OpeningHours
import com.elderlyChild.dake.repositories.RestaurantRepository
import com.elderlyChild.dake.repositories.StorageRepository
import com.google.firebase.storage.StorageReference

class RestaurantDetailViewModel(private val restaurantRepository: RestaurantRepository,
                                private val storageRepository: StorageRepository, var id: String
): ViewModel() {

    var menuItem: MutableLiveData<List<MenuItem>> = restaurantRepository.getRestaurantMenu(id)
    var restaurantInfo: MutableLiveData<Map<String, Any>> =
        restaurantRepository.getRestaurantInfo(id)
    var businessHours: MutableLiveData<OpeningHours> = restaurantRepository.getBusinessHours(id)

    fun getRestaurantDetailImgsList(locationStr: String): MutableLiveData<List<StorageReference>> {
        return storageRepository.getRestaurantDetailImgsList(locationStr)
    }

    fun getMenuItemImg(locationStr: String): StorageReference {
        return storageRepository.getMenuItemImg(id, locationStr)
    }
}
