package com.elderlyChild.dake.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elderlyChild.dake.R
import com.elderlyChild.dake.models.MenuItem

class MenuShowcaseAdapter(private val dataSet: List<MenuItem>) :
    RecyclerView.Adapter<MenuShowcaseAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menuPreviewName: TextView = view.findViewById(R.id.menuPreviewName)
        val menuPreviewDescription: TextView = view.findViewById(R.id.menuPreviewDescription)
        val menuPreviewPrice: TextView = view.findViewById(R.id.menuPreviewPrice)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.menu_item_preview, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        var item = dataSet[position]
        holder.menuPreviewName.text=item.name
        holder.menuPreviewDescription.text= item.description?.take(100) ?:""
        holder.menuPreviewPrice.text ="\$${String.format("%.2f", item.price)}"
        Log.v(TAG,item.name?:"false")

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    companion object{
        const val TAG ="MenuShowcaseAdapter"
    }
}