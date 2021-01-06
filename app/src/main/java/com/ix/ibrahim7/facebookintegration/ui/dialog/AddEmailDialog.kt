package com.ix.ibrahim7.facebookintegration.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.facebookintegration.R

class AddEmailDialog() : BottomSheetDialogFragment(){



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)
        return inflater.inflate(R.layout.dialog_add_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

    }



    interface GoFragmentMessage {
        fun onClick(type: Boolean)
    }



}