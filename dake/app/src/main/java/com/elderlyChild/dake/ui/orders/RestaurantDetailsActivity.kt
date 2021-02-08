package com.elderlyChild.dake.ui.orders

import java.util.Currency
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.elderlyChild.dake.R
import com.elderlyChild.dake.adapters.MenuShowcaseAdapter
import com.elderlyChild.dake.adapters.RestaurantImgDetailAdapter
import com.elderlyChild.dake.models.MenuItem
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.repositories.RestaurantRepository
import com.elderlyChild.dake.repositories.StorageRepository
import com.elderlyChild.dake.viewModels.RestaurantDetailViewModel
import com.elderlyChild.dake.viewModels.RestaurantInfoSharedViewModel


class RestaurantDetailsActivity : AppCompatActivity() , MenuShowcaseAdapter.MenuRequestListener  {

    private lateinit var menuShowcase : RecyclerView
    private lateinit var restaurantImages : ViewPager2
    private lateinit var reserveFragment : FragmentContainerView
    private lateinit var restaurantInfoFragmentView: FragmentContainerView
    private lateinit var restaurantName : TextView
    private lateinit var restaurantCategoryRating : TextView
    private lateinit var restaurantAddress : TextView
    private lateinit var btnMoreInfo : Button
    private lateinit var btnMenu: Button
    private lateinit var btnReserve : Button
    private lateinit var restaurantDetailViewModel : RestaurantDetailViewModel
    private val sharedViewModel: RestaurantInfoSharedViewModel by viewModels()
    private lateinit var restaurant : Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

        initRestaurant()
        initViews()
        displayRestaurantInfo()
        initViewModel()
        initSharedViewModel()
        addRestaurantInfo()
        createInfoFragment(savedInstanceState)
        initMenuItems()
        initButtonClickListeners()
        initRestaurantImages()
    }

    private fun initRestaurant(){
        val intentRestaurant=intent.getParcelableExtra<Parcelable>("restaurant")
        Log.v(TAG, intentRestaurant.toString())
        restaurant=intentRestaurant as Restaurant
    }

    private fun initViews(){
        restaurantImages=findViewById(R.id.restaurantImages)
        restaurantName=findViewById(R.id.restaurantName)
        restaurantAddress=findViewById(R.id.restaurantAddress)
        restaurantCategoryRating=findViewById(R.id.restaurantCategoryRating)
        menuShowcase=findViewById(R.id.menuShowcase)
        btnMenu=findViewById(R.id.btnMenu)
        btnReserve=findViewById(R.id.btnReserve)
        reserveFragment=findViewById(R.id.reserveFragment)
        restaurantInfoFragmentView=findViewById(R.id.restaurantInfoFragment)
        btnMoreInfo=findViewById(R.id.restaurantMoreInfo)
    }

    private fun displayRestaurantInfo(){
        restaurantName.text=restaurant.name
        restaurantAddress.text=restaurant.address
        restaurantCategoryRating.text="${restaurant.cuisine} $CENTERED_DOT "+
                "${STAR_UNICODE}${restaurant.avgRating.toString()}" +
                " (${restaurant.noRatings})"
    }

    private fun addRestaurantInfo(){
        restaurantDetailViewModel.restaurantInfo.observe(this, Observer {
            if (it == null) {
            } else {
                restaurant.addInfoMap(it)
                sharedViewModel.restaurantLiveData.value=restaurant
            }
        })
    }

    private fun initButtonClickListeners(){
        btnMenu.setOnClickListener{
            when(menuShowcase.visibility){
                View.VISIBLE -> menuShowcase.visibility = View.GONE
                View.GONE -> menuShowcase.visibility = View.VISIBLE
            }
        }

        btnReserve.setOnClickListener{
            when(reserveFragment.visibility){
                View.VISIBLE -> reserveFragment.visibility = View.GONE
                View.GONE -> reserveFragment.visibility = View.VISIBLE
            }
        }

        btnMoreInfo.setOnClickListener{
            when(restaurantInfoFragmentView.visibility){
                View.VISIBLE -> restaurantInfoFragmentView.visibility = View.GONE
                View.GONE -> restaurantInfoFragmentView.visibility = View.VISIBLE
            }
        }
    }
    private fun initViewModel(){
        restaurantDetailViewModel= RestaurantDetailViewModel(RestaurantRepository(), StorageRepository(), restaurant.id
                ?: "")
    }

    private fun initSharedViewModel(){
        sharedViewModel.restaurantLiveData.value=restaurant
        sharedViewModel.businessHoursLiveData=restaurantDetailViewModel.businessHours
    }

    private fun initRestaurantImages(){
        restaurantDetailViewModel.getRestaurantDetailImgsList(restaurant.id ?: "")
            .observe(this, Observer {
                restaurantImages.adapter = RestaurantImgDetailAdapter(this, it)
            })
    }

    private fun initMenuItems(){
        menuShowcase.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        restaurantDetailViewModel.menuItem.observe(this, Observer {
            Log.v(TAG, it.toString())
            menuShowcase.adapter = MenuShowcaseAdapter(it, this)
        })
    }

    private fun createInfoFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RestaurantInfoFragment>(R.id.restaurantInfoFragment)
            }
        }
    }

    override fun onMenuSelect(position: Int, menuItem: MenuItem) {
    }

    override fun provideCurrencySymbol(): MutableLiveData<String>{
        val symbolLiveData = MutableLiveData<String>()
        val currency: Currency = Currency.getInstance(restaurant.currencyCode)
        Log.v("Currency",currency.symbol)
        symbolLiveData.value=currency.symbol
        return symbolLiveData
    }

    override fun fillImageView(locationStr: String, imageView: ImageView) {
        Glide.with(this).load(restaurantDetailViewModel.getMenuItemImg(locationStr))
                .into(imageView)
    }

    companion object{
        const val CENTERED_DOT="\u00b7"
        const val STAR_UNICODE ="\u2b50"
        const val TAG ="RestaurantDetActivity"
    }

}