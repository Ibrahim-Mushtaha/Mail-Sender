package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentSettingsBinding
import com.ix.ibrahim7.facebookintegration.model.Email
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.AddEmailDialog
import kotlinx.android.synthetic.main.fragment_main.*


class SettingsFragment : Fragment(),AddEmailDialog.OnClickListener {

    lateinit var mBinding:FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requireActivity().bottom_nav.menu[3].apply {
            setIcon(R.drawable.ic_add)
            setOnMenuItemClickListener {
                AddEmailDialog(this@SettingsFragment).show(childFragmentManager,"")
                true
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }


    override fun onClick(email: Email, type: Boolean) {

    }


}