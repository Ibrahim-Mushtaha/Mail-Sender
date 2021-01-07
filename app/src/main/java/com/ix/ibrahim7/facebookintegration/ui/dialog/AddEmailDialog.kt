package com.ix.ibrahim7.facebookintegration.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.EmailAdapter
import com.ix.ibrahim7.facebookintegration.databinding.DialogAddEmailBinding
import com.ix.ibrahim7.facebookintegration.model.Email
import java.util.*
import kotlin.collections.ArrayList

class AddEmailDialog(val onGo: GoFragmentMessage) : BottomSheetDialogFragment(){

    lateinit var mBinding:DialogAddEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogAddEmailBinding.inflate(inflater,container,false).apply {
            executePendingBindings()
        }
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnSave.setOnClickListener {
            onGo.onClick(Email("1",mBinding.txtEmail.text.toString(),mBinding.txtName.text.toString(),Calendar.getInstance().time.toString()),true)
            dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

    }



    interface GoFragmentMessage {
        fun onClick(email: Email,type: Boolean)
    }




}