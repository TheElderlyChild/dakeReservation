package com.elderlyChild.dake.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2

class StorageRepository {
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun getRestaurantProfileImgRef(locationStr : String): StorageReference{
        val storageRef=firebaseStorage.reference
        var imgRef = storageRef.child(RESTAURANT_PREVIEW_COLLECTION).child(locationStr)
        Log.v(TAG, imgRef.toString())
        return imgRef
    }

    fun getImgRef(locationStr : String): StorageReference{
        val storageRef=firebaseStorage.reference
        var imgRef = storageRef.child(locationStr)
        Log.v(TAG, imgRef.toString())
        return imgRef
    }

    fun getRestaurantDetailImgsList(locationStr : String): MutableLiveData<List<StorageReference>> {
        var result = MutableLiveData<List<StorageReference>>()
        val storageRef=firebaseStorage.reference
        val restaurantDetailImagesRef=storageRef.child(RESTAURANT_DETAIL_COLLECTION).child(locationStr)
        restaurantDetailImagesRef.listAll()
            .addOnSuccessListener { (items, prefixes) ->
                result.value=items
                Log.v(TAG, "Success, references at ${locationStr}: ${result.toString()}")
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
                Log.v(TAG, "Failed to retrieve storage references for $locationStr",it)
            }
        return result
    }

    companion object{
        private const val RESTAURANT_PREVIEW_COLLECTION="restaurant_profile_img"
        private const val RESTAURANT_DETAIL_COLLECTION="restaurant_detail_imgs"
        private const val TAG = "StorageRepository"
    }
}