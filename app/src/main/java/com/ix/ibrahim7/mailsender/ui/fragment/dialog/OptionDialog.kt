package com.ix.ibrahim7.mailsender.ui.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.mailsender.databinding.DialogChooseColorBinding
import com.ix.ibrahim7.mailsender.databinding.DialogChooseOptionBinding
import com.ix.ibrahim7.mailsender.model.Category
import com.ix.ibrahim7.mailsender.model.Users
import com.ix.ibrahim7.mailsender.ui.viewmodel.CategoryViewmodel
import com.ix.ibrahim7.mailsender.ui.viewmodel.UsersViewmodel

class OptionDialog(val onGo: OnClickListener, val category: Category?,val users: Users?,val type: Int) : BottomSheetDialogFragment(),ChooseColorDialog.OnClickListener{

    lateinit var mBinding:DialogChooseOptionBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[CategoryViewmodel::class.java]
    }

    private val userViewModel by lazy {
        ViewModelProvider(this)[UsersViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogChooseOptionBinding.inflate(inflater,container,false).apply {
            executePendingBindings()
        }
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(type){
            1->{
                mBinding.tvEdit.setOnClickListener {
                    dismiss()
                    onGo.onClick(category!!,null)
                }

                mBinding.tvDelete.setOnClickListener {
                    viewModel.deleteCategory(category!!)
                    dismiss()
                }
            }
            else->{
                mBinding.tvEdit.setOnClickListener {
                    dismiss()
                    onGo.onClick(null,users)
                }

                mBinding.tvDelete.setOnClickListener {
                    userViewModel.deleteUser(users!!)
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

    override fun onClick(category: Category, type: Int) {

    }


    interface OnClickListener {
        fun onClick(category: Category?,users: Users?)
    }


}