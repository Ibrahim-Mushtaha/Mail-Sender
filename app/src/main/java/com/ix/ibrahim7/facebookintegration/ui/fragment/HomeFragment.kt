package com.ix.ibrahim7.facebookintegration.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentHomeBinding
import com.ix.ibrahim7.facebookintegration.util.Constant
import com.ix.ibrahim7.facebookintegration.util.Constant.USERID
import com.ix.ibrahim7.facebookintegration.util.Constant.getSharePref
import com.ix.ibrahim7.facebookintegration.util.Constant.setImage


class HomeFragment : Fragment() {


    lateinit var mbinding: FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mbinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getImage(getSharePref(requireContext()).getString(USERID,"")!!)


        super.onViewCreated(view, savedInstanceState)
    }

    fun getImage(id:String){
        val image_url =
            "http://graph.facebook.com/$id/picture?type=large"
        setImage(requireContext(),image_url,mbinding.tvProfileImage,R.drawable.ic_profile_img)
    }
}