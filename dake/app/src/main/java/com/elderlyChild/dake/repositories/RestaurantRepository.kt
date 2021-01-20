package com.elderlyChild.dake.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.elderlyChild.dake.models.MenuItem
import com.elderlyChild.dake.models.Restaurant
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class RestaurantRepository {
    private val db= FirebaseFirestore.getInstance()

    fun getAllRestaurants(): MutableLiveData<List<Restaurant>> {
        val restaurantListLiveData = MutableLiveData<List<Restaurant>>()
        var restaurantList = ArrayList<Restaurant>()
        db.collection(RESTAURANT_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val restaurant = document.toObject<Restaurant>().withId<Restaurant>(document.id)
                    restaurantList.add(restaurant)
                }
                restaurantListLiveData.value=restaurantList
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return restaurantListLiveData
    }

    fun getRestaurantMenu(id: String): MutableLiveData<List<MenuItem>>{
        val menuLiveData = MutableLiveData<List<MenuItem>>()
        val menu = ArrayList<MenuItem>()
        db.collection(RESTAURANT_COLLECTION_NAME).document(id).collection(MENU_COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val item=document.toObject<MenuItem>().withId<MenuItem>(document.id)
                    menu.add(item)
                }
                menuLiveData.value=menu
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return menuLiveData
    }

    companion object{
        private const val RESTAURANT_COLLECTION_NAME="restaurants"
        private const val MENU_COLLECTION="menu"
        private const val TAG = "RestaurantRepository"
    }
}