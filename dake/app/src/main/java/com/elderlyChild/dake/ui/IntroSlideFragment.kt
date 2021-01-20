package com.elderlyChild.dake.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.elderlyChild.dake.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TEXT = "text"
private const val ARG_IMG_STR = "imgStr"

/**
 * A simple [Fragment] subclass.
 * Use the [IntroSlideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IntroSlideFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var text: String? = null
    private var imgStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString(ARG_TEXT)
            imgStr = it.getString(ARG_IMG_STR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Fill view with the text and drawable image location in String form
        var textDescriptor : TextView = view.findViewById(R.id.textDescriptor)
        var imgIntro : ImageView = view.findViewById(R.id.imgIntro)
        textDescriptor.text = text

        //Fill imageView with image specified by imgStr
        imgStr?.let { fillImage(it, imgIntro) }
    }

    private fun fillImage(imgStr: String, imgView: ImageView) {
        //Get Image ID
        val imgID = resources.getIdentifier(imgStr, "drawable", context?.packageName)

        //Set Image with Glide
        Glide.with(this).load(imgID).into(imgView)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param text Parameter 1.
         * @param imgStr Parameter 2.
         * @return A new instance of fragment IntroSlideFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(text: String, imgStr: String) =
                IntroSlideFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_TEXT, text)
                        putString(ARG_IMG_STR, imgStr)
                    }
                }
    }
}