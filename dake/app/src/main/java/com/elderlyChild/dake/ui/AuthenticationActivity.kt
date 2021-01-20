package com.elderlyChild.dake.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.elderlyChild.dake.R
import com.elderlyChild.dake.adapters.IntroSlideFragmentAdapter
import com.elderlyChild.dake.repositories.UserRepository
import com.elderlyChild.dake.viewModels.AuthViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse


private const val TEXT_SEARCH_RESTAURANT= "Search from our collection of wonderful restaurants"
private const val TEXT_SELECT_FOOD="Pick from a variety of tasty food options"
private const val TEXT_MAKE_RESERVATION="Make a Reservation"
private const val IMG_SEARCH_RESTAURANTS="@drawable/search_restaurant_img"
private const val IMG_SELECT_FOOD="@drawable/select_food_img"
private const val IMG_MAKE_RESERVATION="@drawable/make_reservation_img"

class AuthenticationActivity : AppCompatActivity()  {
    private lateinit var introPager: ViewPager2
    private lateinit var authViewModel: AuthViewModel
    private val authUI : AuthUI = AuthUI.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.dakeTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        initAuthViewModel()
        checkUserAuthentication()

        initLoginButton()
        initIntroSlideshow()
    }

    private fun initIntroSlideshow(){
        introPager = findViewById(R.id.restaurantImages)
        val introPagerInfo = listOf(
            Pair(TEXT_SEARCH_RESTAURANT, IMG_SEARCH_RESTAURANTS),
            Pair(TEXT_SELECT_FOOD, IMG_SELECT_FOOD),
            Pair(TEXT_MAKE_RESERVATION, IMG_MAKE_RESERVATION))

        val introPagerAdapter = IntroSlideFragmentAdapter(this,introPagerInfo)
        introPager.adapter = introPagerAdapter
    }

    private fun initAuthViewModel(){
        authViewModel= AuthViewModel(UserRepository())
    }

    private fun checkUserAuthentication(){
        if(authViewModel.userLiveData.value!=null){
            onUserAuthenticated()
        }
    }

    private fun onUserAuthenticated(){
        //start home page activity
        val homeIntent = Intent(this, HomePageActivity::class.java)
        finish()
        startActivity(homeIntent)
    }

    private fun initLoginButton(){
        val btnLogin= findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            launchFirebaseAuthUI()
        }
    }

    private fun launchFirebaseAuthUI(){
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Log.v(TAG,"Login Successful")
                onUserAuthenticated()
            }
            else {
                // Sign in failed.
                if (response != null) {
                    Log.w(TAG,"Login Failed",response.error)
                    handleAuthUIError(response.error?.errorCode)
                }
            }
        }
    }

    private fun handleAuthUIError(errorCode : Int?){
        lateinit var errorMsg : String
        when(errorCode){
            ErrorCodes.UNKNOWN_ERROR -> errorMsg="There seems to be some trouble connecting"
            ErrorCodes.NO_NETWORK -> errorMsg="The internet connection appears to be offline"
            ErrorCodes.PLAY_SERVICES_UPDATE_CANCELLED -> errorMsg=""
            ErrorCodes.DEVELOPER_ERROR -> errorMsg="There is a problem with the app, we will " +
                    "fix it as soon as possible."
            ErrorCodes.PROVIDER_ERROR -> errorMsg="There seems to be some trouble connecting"
            ErrorCodes.ANONYMOUS_UPGRADE_MERGE_CONFLICT -> errorMsg="Failed to link with Anonymous account"
            ErrorCodes.EMAIL_MISMATCH_ERROR -> errorMsg="You are attempting to sign in with a " +
                    "different email than previously provided"
            ErrorCodes.ERROR_USER_DISABLED -> errorMsg="Your account has been disabled"
            ErrorCodes.ERROR_GENERIC_IDP_RECOVERABLE_ERROR -> errorMsg="There seems to " +
                    "be some trouble connecting"
            else ->{"There seems to " +
                    "be some trouble connecting"}
        }
        Toast.makeText(this,errorMsg,Toast.LENGTH_LONG)
    }

    companion object {
        private const val RC_SIGN_IN = 100
        private const val TAG = "AuthenticationActivity"
    }
}