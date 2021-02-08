package com.elderlyChild.dake.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.elderlyChild.dake.models.MenuItem
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.models.businessHours.OpeningHourParser
import com.elderlyChild.dake.models.businessHours.OpeningHours
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

    fun getRestaurantInfo(id: String): MutableLiveData<Map<String, Any>>{
        val infoLiveData = MutableLiveData<Map<String, Any>>()
        var infoMap: HashMap<String, Any> = HashMap<String, Any>()
        db.collection(RESTAURANT_COLLECTION_NAME).document(id).collection(INFO_COLLECTION)
                .document(ABOUT_DOCUMENT).get()
                .addOnSuccessListener {  document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        document.getString("phoneNo")?.let { infoMap.put("phoneNo", it) }
                        document.getString("cancelPolicy")?.let { infoMap.put("cancelPolicy", it) }
                        document.getString("description")?.let { infoMap.put("description", it) }
                        document.getString("currencyCode")?.let { infoMap.put("currencyCode", it) }
                        infoLiveData.value=infoMap
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
        return infoLiveData
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
                    item.updatePriceWithBasePrice()
                    menu.add(item)
                }
                menuLiveData.value=menu
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return menuLiveData
    }

    fun getBusinessHours(id: String): MutableLiveData<OpeningHours>{
        val openingHourLiveData = MutableLiveData<OpeningHours>()
        var openingHours : OpeningHours
        db.collection(RESTAURANT_COLLECTION_NAME).document(id).collection(INFO_COLLECTION)
            .document(BUSINESS_HOURS_DOCUMENT)
                .get()
                .addOnSuccessListener { document ->
                    Log.d(TAG, "${document.id} => ${document.data}")
                    openingHours = OpeningHours.fromMap(document.data as Map<String, List<String>>)
                    Log.d(TAG, "${document.id} => ${OpeningHourParser().toDisplayString(openingHours)}")
                    openingHourLiveData.value=openingHours
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
        return openingHourLiveData
    }

    companion object{
        private const val RESTAURANT_COLLECTION_NAME="restaurants"
        private const val MENU_COLLECTION="menu"
        private const val INFO_COLLECTION="info"
        private const val BUSINESS_HOURS_DOCUMENT="businessHours"
        private const val ABOUT_DOCUMENT="about"
        private const val TAG = "RestaurantRepository"
    }
}