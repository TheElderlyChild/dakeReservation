package com.elderlyChild.dake.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elderlyChild.dake.ui.AuthenticationActivity
import com.elderlyChild.dake.ui.IntroSlideFragment

class IntroSlideFragmentAdapter (fa: FragmentActivity, private val introPagerInfo: List<Pair<String,String>>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = introPagerInfo.size

    override fun createFragment(position: Int):Fragment {
        return IntroSlideFragment.newInstance(introPagerInfo[position].first,
                introPagerInfo[position].second)
    }
}