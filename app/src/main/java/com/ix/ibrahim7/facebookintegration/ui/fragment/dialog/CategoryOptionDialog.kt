package com.ix.ibrahim7.facebookintegration.ui.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.facebookintegration.databinding.DialogChooseColorBinding
import com.ix.ibrahim7.facebookintegration.databinding.DialogChooseOptionBinding
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.CategoryViewmodel

class CategoryOptionDialog(val onGo: OnClickListener, val category: Category) : BottomSheetDialogFragment(),ChooseColorDialog.OnClickListener{

    lateinit var mBinding:DialogChooseOptionBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[CategoryViewmodel::class.java]
    }

    private var color = ""

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

        mBinding.tvEdit.setOnClickListener {
            dismiss()
            onGo.onClick(category,true)
        }

        mBinding.tvDelete.setOnClickListener {
            viewModel.deleteCategory(category)
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

    override fun onClick(category: Category, type: Int) {

    }


    interface OnClickListener {
        fun onClick(category: Category,type: Boolean)
    }


}