package com.elderlyChild.dake.adapters

import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elderlyChild.dake.R
import com.elderlyChild.dake.models.Restaurant
import com.elderlyChild.dake.repositories.StorageRepository


class RestaurantPreviewAdapter(var dataSet: List<Restaurant>?,
                               private var selectionListener: PreviewSelectionListener) :
    RecyclerView.Adapter<RestaurantPreviewAdapter.ViewHolder>(){
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(var view: View, var selectionListener: PreviewSelectionListener) :
        RecyclerView.ViewHolder(view) , View.OnClickListener {
        lateinit var restaurant:Restaurant
        val previewName: TextView = view.findViewById(R.id.previewName)
        val previewRating: TextView = view.findViewById(R.id.previewRating)
        val previewInfo: TextView = view.findViewById(R.id.previewInfo)
        val previewImg: ImageView = view.findViewById(R.id.previewImg)

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            selectionListener.onPreviewSelect(adapterPosition, restaurant)
        }
    }

    interface PreviewSelectionListener{
        fun onPreviewSelect(position : Int, restaurant: Restaurant)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.restaurant_preview, viewGroup, false)
        return ViewHolder(view, selectionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var restaurant= dataSet?.get(position)
        if(restaurant != null) {
            holder.restaurant = restaurant
            holder.previewName.text = restaurant.name
            holder.previewRating.text = "$STAR_UNICODE${restaurant.rating.toString()}" +
                    " (${restaurant.ratingsNo})"
            holder.previewInfo.text = restaurant.category
            Glide.with(holder.view).load(StorageRepository().getRestaurantProfileImgRef(restaurant.imageLocation.toString()))
                .into(holder.previewImg)
        }
    }

    override fun getItemCount(): Int {
            return dataSet?.size ?: 0
    }

    companion object {
        const val STAR_UNICODE ="\u2b50"
        const val TAG = "Restaurant Preview Adapter"
    }
}