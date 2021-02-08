package com.elderlyChild.dake.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elderlyChild.dake.R
import com.elderlyChild.dake.models.Restaurant
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeSlotChoicesAdapter(var dataSet : List<LocalTime>, var selectionListener: SelectionListener):
        RecyclerView.Adapter<TimeSlotChoicesAdapter.ViewHolder>() {

    class ViewHolder(var view: View, var selectionListener: SelectionListener) :
            RecyclerView.ViewHolder(view) , View.OnClickListener {

        lateinit var time: LocalTime

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selectionListener.onSelect(adapterPosition,time)
        }

    }

    interface SelectionListener{
        fun onSelect(position: Int, time: LocalTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = Button(parent.context)
        return ViewHolder(view, selectionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time= dataSet[position]
        var button: Button = holder.itemView as Button
        button.text = DateTimeFormatter.ofPattern("HH:mm").format(holder.time)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}