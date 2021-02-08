package com.elderlyChild.dake.ui.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elderlyChild.dake.R
import com.elderlyChild.dake.adapters.TimeSlotChoicesAdapter
import com.elderlyChild.dake.models.businessHours.OpeningHours
import com.elderlyChild.dake.viewModels.RestaurantInfoSharedViewModel
import java.time.LocalDate
import java.time.LocalTime

/**
 * A simple [Fragment] subclass.
 * Use the [MakeReservationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MakeReservationFragment : Fragment(R.layout.fragment_make_reservation) ,
        TimeSlotChoicesAdapter.SelectionListener {

    private val viewModel: RestaurantInfoSharedViewModel by activityViewModels()
    lateinit var btnPartySizeDate : Button
    lateinit var availabilityRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if(view != null){
            btnPartySizeDate=view.findViewById(R.id.btnPartySizeDate)
            availabilityRecyclerView=view.findViewById(R.id.availableTimes)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        initAvailabilityRecycler()
    }

    private fun initButton(){
        viewModel.selectedPartySizeLiveData.observe(viewLifecycleOwner, Observer{
            btnPartySizeDate.text= "$PERSON_UNICODE_CHAR${it?:"0"} $CENTERED_DOT " +
                    "${viewModel.selectedDateLiveData.value.toString()}"
        })
        viewModel.selectedDateLiveData.observe(viewLifecycleOwner,Observer{
            btnPartySizeDate.text= "$PERSON_UNICODE_CHAR${viewModel.selectedPartySizeLiveData.value} $CENTERED_DOT " +
                    "${it.toString()}"
        })

        btnPartySizeDate.setOnClickListener{
            ChooseReservationOptionsFragment().show(
                    childFragmentManager, ChooseReservationOptionsFragment.TAG)
        }
    }

    private fun initAvailabilityRecycler(){
        availabilityRecyclerView.layoutManager=LinearLayoutManager(
                this.context,LinearLayoutManager.HORIZONTAL,false)

        viewModel.businessHoursLiveData.observe(viewLifecycleOwner,Observer{ openingHours ->
            var data=openingHours?:OpeningHours()
            viewModel.selectedDateLiveData.observe(viewLifecycleOwner, Observer{
                var adapter = TimeSlotChoicesAdapter(data.getTimeSlotList(
                        it?: LocalDate.now()),this)
                availabilityRecyclerView.adapter=adapter
            })
        })
    }

    override fun onSelect(position: Int, time: LocalTime) {

    }

    companion object{
        const val TAG = "MakeReservationFragment"
        const val PERSON_UNICODE_CHAR = "\uD83D\uDC64"
        const val CENTERED_DOT="\u00b7"
    }


}