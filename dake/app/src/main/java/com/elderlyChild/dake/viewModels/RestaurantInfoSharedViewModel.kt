package com.elderlyChild.dake.viewModels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.models.businessHours.OpeningHours
import com.elderlyChild.dake.repositories.OrderRepository
import java.time.LocalDate

class RestaurantInfoSharedViewModel: ViewModel() {
    val restaurantLiveData = MutableLiveData<Restaurant>()
    var businessHoursLiveData = MutableLiveData<OpeningHours>()
    val selectedPartySizeLiveData = MutableLiveData<Int>(1)
    val selectedDateLiveData = MutableLiveData<LocalDate>(LocalDate.now())
}