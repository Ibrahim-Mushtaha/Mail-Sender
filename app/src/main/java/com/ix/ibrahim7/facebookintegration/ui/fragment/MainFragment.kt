package com.ix.ibrahim7.facebookintegration.ui.fragment

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.hk.kbottomnavigation.KBottomNavigation
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentMainBinding
import com.ix.ibrahim7.facebookintegration.ui.dialog.AddEmailDialog

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