package com.ix.ibrahim7.facebookintegration.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.bottomNav.itemIconTintList = null

        val navegation =
            childFragmentManager.findFragmentById(R.id.fragment_nav_host_main)
                ?.findNavController()?.also {
                    NavigationUI.setupWithNavController(
                        mBinding.bottomNav,
                        it
                    )
                }


    }

}