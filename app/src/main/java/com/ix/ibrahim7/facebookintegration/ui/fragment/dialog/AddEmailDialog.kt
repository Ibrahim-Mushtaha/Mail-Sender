package com.ix.ibrahim7.facebookintegration.ui.fragment.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.DialogAddEmailBinding
import com.ix.ibrahim7.facebookintegration.model.Users
import java.util.*

class AddEmailDialog(val categoryID: String,val onGo: OnClickListener) : BottomSheetDialogFragment(){

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
            when {
                TextUtils.isEmpty(mBinding.txtName.text!!.toString()) -> {
                    mBinding.txtName.error =
                        requireActivity().getString(R.string.this_is_require)
                    mBinding.txtName.requestFocus()
                    return@setOnClickListener
                }
                TextUtils.isEmpty(mBinding.txtEmail.text!!.toString()) -> {
                    mBinding.txtEmail.error =
                        requireActivity().getString(R.string.this_is_require)
                    mBinding.txtEmail.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    onGo.onClick(
                        Users(
                            UUID.randomUUID().toString(),
                            mBinding.txtName.text.toString(),
                            mBinding.txtEmail.text.toString(),
                            categoryID
                        ), true
                    )
                    dismiss()
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

    }



    interface OnClickListener {
        fun onClick(users: Users,type: Boolean)
    }




}