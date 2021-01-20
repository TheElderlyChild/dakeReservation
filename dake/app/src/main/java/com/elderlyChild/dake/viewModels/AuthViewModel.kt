package com.elderlyChild.dake.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elderlyChild.dake.repositories.UserRepository
import com.google.firebase.auth.FirebaseUser



class AuthViewModel( private val userRepository: UserRepository) : ViewModel() {
    var userLiveData: MutableLiveData<FirebaseUser> = userRepository.getAuthenticatedUser()
}