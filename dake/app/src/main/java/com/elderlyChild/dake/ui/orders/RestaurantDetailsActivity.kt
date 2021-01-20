package com.elderlyChild.dake.ui.orders

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.elderlyChild.dake.R
import com.elderlyChild.dake.adapters.MenuShowcaseAdapter
import com.elderlyChild.dake.adapters.RestaurantImgDetailAdapter
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.repositories.RestaurantRepository
import com.elderlyChild.dake.repositories.StorageRepository
import com.elderlyChild.dake.viewModels.RestaurantDetailViewModel


class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var menuShowcase : RecyclerView
    private lateinit var restaurantImages : ViewPager2
    private lateinit var restaurantName : TextView
    private lateinit var restaurantCategoryRating : TextView
    private lateinit var restaurantAddress : TextView
    private lateinit var restaurantDetailViewModel : RestaurantDetailViewModel
    private lateinit var restaurant : Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

        initRestaurant()
        initViews()
        displayRestaurantInfo()
        initViewModel()
        initMenuItems()
        initRestaurantImages()

    }

    private fun initRestaurant(){
        val intentRestaurant=intent.getParcelableExtra<Parcelable>("restaurant")
        Log.v(TAG,intentRestaurant.toString())
        restaurant=intentRestaurant as Restaurant
    }

    private fun initViews(){
        restaurantImages=findViewById(R.id.restaurantImages)
        restaurantName=findViewById(R.id.restaurantName)
        restaurantAddress=findViewById(R.id.restaurantAddress)
        restaurantCategoryRating=findViewById(R.id.restaurantCategoryRating)
        menuShowcase=findViewById(R.id.menuShowcase)
    }

    private fun displayRestaurantInfo(){
        restaurantName.text=restaurant.name
        restaurantAddress.text=restaurant.address
        restaurantCategoryRating.text="${restaurant.category} $CENTERED_DOT "+
                "${STAR_UNICODE}${restaurant.rating.toString()}" +
                " (${restaurant.ratingsNo})"
    }

    private fun initViewModel(){
        restaurantDetailViewModel= RestaurantDetailViewModel(RestaurantRepository(), StorageRepository(),restaurant.id?:"")
    }

    private fun initRestaurantImages(){
        restaurantDetailViewModel.getRestaurantDetailImgsList(restaurant.id?:"")
            .observe(this, Observer{
            restaurantImages.adapter=RestaurantImgDetailAdapter(this,it)
        })
    }

    private fun initMenuItems(){
        menuShowcase.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        restaurantDetailViewModel.menuItem.observe(this,Observer{
            Log.v(TAG,it.toString())
            menuShowcase.adapter=MenuShowcaseAdapter(it)
        })
    }


    companion object{
        const val CENTERED_DOT="\u00b7"
        const val STAR_UNICODE ="\u2b50"
        const val TAG ="RestaurantDetActivity"
    }
}