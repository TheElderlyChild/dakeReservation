package com.elderlyChild.dake.ui.orders

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.elderlyChild.dake.R
import com.elderlyChild.dake.repositories.StorageRepository
import com.google.firebase.storage.StorageReference
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_IMG_REF = "imgRef"

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantImgFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantImgFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var imgRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imgRef = StorageRepository().getImgRef(it.getString(ARG_IMG_REF)?:"")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_img, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val restaurantImgDetail = view.findViewById<ImageView>(R.id.restaurantImgDetail)
        //Fill imageView with image specified by restaurantImgDetail
        Glide.with(view).load(imgRef).into(restaurantImgDetail)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestaurantImgFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(imgRef: StorageReference) =
            RestaurantImgFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMG_REF, imgRef.path)
                }
            }
    }
}