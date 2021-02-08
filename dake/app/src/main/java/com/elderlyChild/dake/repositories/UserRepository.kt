package com.elderlyChild.dake.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserRepository{
    private val firebaseAuth= FirebaseAuth.getInstance()

    fun getAuthenticatedUser(): MutableLiveData<FirebaseUser>{
        val user: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            user.value = firebaseUser
        }
        return user
    }
}