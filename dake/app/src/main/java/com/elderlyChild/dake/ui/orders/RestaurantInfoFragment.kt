package com.elderlyChild.dake.ui.orders

import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.elderlyChild.dake.R
import com.elderlyChild.dake.models.businessHours.OpeningHourParser
import com.elderlyChild.dake.models.businessHours.OpeningHours
import com.elderlyChild.dake.viewModels.RestaurantDetailViewModel
import com.elderlyChild.dake.viewModels.RestaurantInfoSharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantInfoFragment : Fragment(R.layout.fragment_restaurant_info) {

    private val viewModel: RestaurantInfoSharedViewModel by activityViewModels()
    private lateinit var restaurantDescription: TextView
    private lateinit var restaurantPhoneNo: TextView
    private lateinit var restaurantOpenHours: TextView
    private lateinit var restaurantCancelPolicy: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) {
            restaurantDescription= view.findViewById(R.id.restaurantDescription)
            restaurantPhoneNo= view.findViewById(R.id.restaurantPhoneNo)
            restaurantOpenHours = view.findViewById(R.id.restaurantOpenHours)
            restaurantCancelPolicy = view.findViewById(R.id.restaurantCancelPolicy)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.restaurantLiveData.observe(viewLifecycleOwner, Observer{
            if (it == null) {
            } else {
                update()
            }
        })

        viewModel.businessHoursLiveData.observe(viewLifecycleOwner, Observer{
            if (it == null) {
            } else {
                updateOpeningHours()
            }
        })
    }

    private fun update() {
        var restaurant = viewModel.restaurantLiveData.value
        if (restaurant != null) {
            restaurantDescription.text = restaurant.description
            var boldStyleSpan: StyleSpan = StyleSpan(android.graphics.Typeface.BOLD)
            var phoneNumberText : SpannableStringBuilder =
                    SpannableStringBuilder("Phone Number\n${restaurant.phoneNo}");
            phoneNumberText.setSpan(boldStyleSpan,0,12, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            restaurantPhoneNo.text=phoneNumberText

            var cancelPolicyText : SpannableStringBuilder =
                    SpannableStringBuilder("Cancel Policy\n${restaurant.cancelPolicy}");
            cancelPolicyText.setSpan(boldStyleSpan,0,13, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            restaurantCancelPolicy.text=cancelPolicyText
        }
    }

    private fun updateOpeningHours(){
        var openingHours = viewModel.businessHoursLiveData.value
        if (openingHours!=null){
            var boldStyleSpan: StyleSpan = StyleSpan(android.graphics.Typeface.BOLD)
            var openingHoursText : SpannableStringBuilder =
                    SpannableStringBuilder("Opening Hours\n${OpeningHourParser().toDisplayString(openingHours)}");
            openingHoursText.setSpan(boldStyleSpan,0,13, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            restaurantOpenHours.text=openingHoursText
        }
    }

    companion object{
        const val phoneEmoji = "\u260E"
    }
}