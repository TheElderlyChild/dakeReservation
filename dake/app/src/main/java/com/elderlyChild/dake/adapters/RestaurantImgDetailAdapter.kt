package com.elderlyChild.dake.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elderlyChild.dake.ui.IntroSlideFragment
import com.elderlyChild.dake.ui.orders.RestaurantImgFragment
import com.google.firebase.storage.StorageReference

class RestaurantImgDetailAdapter (fa: FragmentActivity, private val detailImgPagerInfo: List<StorageReference>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return detailImgPagerInfo.size
    }

    override fun createFragment(position: Int): Fragment {
        return RestaurantImgFragment.newInstance(detailImgPagerInfo[position])
    }
}