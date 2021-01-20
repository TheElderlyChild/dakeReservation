package com.elderlyChild.dake.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elderlyChild.dake.models.MenuItem
import com.elderlyChild.dake.repositories.RestaurantRepository
import com.elderlyChild.dake.repositories.StorageRepository
import com.google.firebase.storage.StorageReference

class RestaurantDetailViewModel(private val restaurantRepository: RestaurantRepository,
                                private val storageRepository: StorageRepository, var id: String): ViewModel(){
    var menuItem : MutableLiveData<List<MenuItem>> = restaurantRepository.getRestaurantMenu(id)

    fun getRestaurantDetailImgsList(locationStr : String): MutableLiveData<List<StorageReference>>{
        return storageRepository.getRestaurantDetailImgsList(locationStr)
    }
}
