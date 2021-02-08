package com.elderlyChild.dake.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elderlyChild.dake.R
import com.elderlyChild.dake.adapters.RestaurantPreviewAdapter
import com.elderlyChild.dake.repositories.RestaurantRepository
import com.elderlyChild.dake.ui.orders.RestaurantDetailsActivity
import com.elderlyChild.dake.viewModels.HomePageViewModel
import androidx.lifecycle.Observer
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.ui.accounts.AccountActivity

class HomePageActivity : AppCompatActivity() , RestaurantPreviewAdapter.PreviewSelectionListener {
    private lateinit var restaurantShowcase : RecyclerView
    private lateinit var btnAccount : ImageButton
    private lateinit var homePageViewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initHomePageViewModel()
        initRestaurantShowcase()
        addAllRestaurantsToShowcase()
        initAccountButton()

    }

    private fun initHomePageViewModel(){
        homePageViewModel=HomePageViewModel(RestaurantRepository())
    }

    private fun initRestaurantShowcase(){
        restaurantShowcase = findViewById<RecyclerView>(R.id.restaurantShowcase)
    }

    private fun initAccountButton(){
        btnAccount=findViewById(R.id.btnAccount)
        btnAccount.setOnClickListener{
            val homeIntent = Intent(this, AccountActivity::class.java)
            startActivity(homeIntent)
        }
    }

    private fun addAllRestaurantsToShowcase(){
        var restaurantList : List<Restaurant> = ArrayList<Restaurant>()
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val restaurantPreviewAdapter = RestaurantPreviewAdapter(restaurantList,this)
        restaurantShowcase.layoutManager=layoutManager
        restaurantShowcase.adapter=restaurantPreviewAdapter
        homePageViewModel.restaurantListLiveData.observe(this, Observer { newRestaurantList ->
            restaurantList= newRestaurantList
            restaurantPreviewAdapter.dataSet=restaurantList
            restaurantPreviewAdapter.notifyDataSetChanged()
            Log.v(TAG,newRestaurantList.toString())
        })
    }

    override fun onPreviewSelect(position: Int, restaurant: Restaurant) {
        val homeIntent = Intent(this, RestaurantDetailsActivity::class.java)
        homeIntent.putExtra("restaurant",restaurant)
        startActivity(homeIntent)
    }

    companion object {
        const val TAG = "HomePageActivity"
    }
}