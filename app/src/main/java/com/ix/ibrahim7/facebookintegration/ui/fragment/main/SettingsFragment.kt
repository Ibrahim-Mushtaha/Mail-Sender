package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentSettingsBinding
import com.ix.ibrahim7.facebookintegration.model.Email
import com.ix.ibrahim7.facebookintegration.model.Users
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.AddEmailDialog
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.SendEmailDialog
import com.ix.ibrahim7.facebookintegration.util.Constant
import com.ix.ibrahim7.facebookintegration.util.Constant.CATEGORY
import com.ix.ibrahim7.facebookintegration.util.Constant.HOME
import com.ix.ibrahim7.facebookintegration.util.Constant.USERLIST
import com.ix.ibrahim7.facebookintegration.util.Constant.editor
import kotlinx.android.synthetic.main.fragment_main.*


class SettingsFragment : Fragment() ,SendEmailDialog.OnClickListener{

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
                SendEmailDialog(this@SettingsFragment).show(childFragmentManager, "")
                true
            }
        }


        mBinding.apply {
            btnTips.setOnClickListener {
                editor(requireContext()).apply {
                    putBoolean(HOME,false)
                    putBoolean(USERLIST,false)
                    putBoolean(CATEGORY,false)
                    apply()
                }
                Snackbar.make(mBinding.root,"You have activated the tips",Snackbar.LENGTH_SHORT).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onClick(type: Int) {

    }


}