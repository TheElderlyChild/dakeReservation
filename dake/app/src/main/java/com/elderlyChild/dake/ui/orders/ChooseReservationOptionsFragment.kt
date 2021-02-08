package com.elderlyChild.dake.ui.orders

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.elderlyChild.dake.R
import com.elderlyChild.dake.viewModels.RestaurantInfoSharedViewModel
import java.time.Duration
import java.time.LocalDate

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseReservationOptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseReservationOptionsFragment : DialogFragment() {

    private val viewModel: RestaurantInfoSharedViewModel by activityViewModels()
    lateinit var btnConfirmDatePartySize : Button
    lateinit var chooseDateSpinner : DatePicker
    lateinit var choosePartySize : NumberPicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_choose_reservation_options, container, false)
        btnConfirmDatePartySize=view.findViewById(R.id.btnConfirmDatePartySize)
        chooseDateSpinner=view.findViewById(R.id.chooseDateSpinner)
        choosePartySize=view.findViewById(R.id.choosePartySize)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initPartySizeSpinner()
        initDateSpinner()

    }

    private fun initButton(){
        btnConfirmDatePartySize.setOnClickListener{
            dismiss()
        }
    }

    private fun initPartySizeSpinner(){
        choosePartySize.minValue= MIN_PARTY_SIZE
        choosePartySize.maxValue= MAX_PARTY_SIZE

        choosePartySize.setOnValueChangedListener { picker, oldVal, newVal ->
            viewModel.selectedPartySizeLiveData.value = newVal
        }

        viewModel.selectedPartySizeLiveData.observe(viewLifecycleOwner, Observer{
            choosePartySize.value=it
        })
    }

    private fun initDateSpinner(){
        var dateInit=viewModel.selectedDateLiveData.value


        //Update view model on date change
        var dateClickListener = DatePicker.OnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            viewModel.selectedDateLiveData.value = LocalDate.of(year, monthOfYear+1, dayOfMonth)
        }

        //Initialize with the day's date
        if (dateInit != null) {
            chooseDateSpinner.init(dateInit.year,dateInit.month.value,dateInit.dayOfMonth-1, dateClickListener)
        }

        //Set current date as minimum
        chooseDateSpinner.minDate= Duration.between((LocalDate.of(1970,1,1)).atStartOfDay(),
                LocalDate.now().atStartOfDay()).toMillis()

        //Set maximum max date (90 days from the day)
        chooseDateSpinner.maxDate= Duration.between((LocalDate.of(1970,1,1)).atStartOfDay(),
                getMaxDate().atStartOfDay()).toMillis()

        //Update spinner with viewModel on viewModel change
        viewModel.selectedDateLiveData.observe(viewLifecycleOwner, Observer{date->
            chooseDateSpinner.updateDate(date.year,date.month.value-1,date.dayOfMonth)
        })
    }

    private fun getPartySizeList(): List<Int> {
        var sizeList = arrayListOf<Int>()
        for(value in 1..10){
            sizeList.add(value)
        }
        return sizeList
    }

    private fun getMaxDate(): LocalDate {
        return LocalDate.now().plusDays(90)
    }


    companion object {
        const val TAG = "ChooseReservationOptionsDialog"
        const val MIN_PARTY_SIZE= 1
        const val MAX_PARTY_SIZE= 10
    }
}