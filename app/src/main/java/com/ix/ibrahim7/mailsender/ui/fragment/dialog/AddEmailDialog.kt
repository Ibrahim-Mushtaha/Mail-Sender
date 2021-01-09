package com.ix.ibrahim7.mailsender.ui.fragment.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.DialogAddEmailBinding
import com.ix.ibrahim7.mailsender.model.Users
import java.util.*

class AddEmailDialog(val categoryID: String,val users: Users?,val onGo: OnClickListener) : BottomSheetDialogFragment(){

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

        when (users) {
            null -> {
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
                                ), 1
                            )
                            dismiss()
                        }
                    }
                }
            }

            else->{
                mBinding.txtName.setText(users.name)
                mBinding.txtEmail.setText(users.email)
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
                                    users.id,
                                    mBinding.txtName.text.toString(),
                                    mBinding.txtEmail.text.toString(),
                                    users.categoryID
                                ), 2
                            )
                            dismiss()
                        }
                    }
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
        fun onClick(users: Users,type: Int)
    }




}