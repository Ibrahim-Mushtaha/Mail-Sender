package com.ix.ibrahim7.facebookintegration.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.DialogChooseColorBinding
import com.ix.ibrahim7.facebookintegration.model.Category
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.dialog_choose_color.*
import kotlinx.android.synthetic.main.dialog_choose_color.view.*

class ChooseColorDialog(val onGo: GoFragmentMessage) : BottomSheetDialogFragment(){

    lateinit var mBinding:DialogChooseColorBinding

    var color = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogChooseColorBinding.inflate(inflater,container,false).apply {
            executePendingBindings()
        }
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = resources.getIntArray(R.array.colors)

        mBinding.tvChooseColor.setOnClickListener {
            ColorSheet().cornerRadius(4)
            ColorSheet().colorPicker(
                colors = colors,
                listener = { color ->
                    it.background.setTint(color)
                    // Handle color
                    this.color = color.toString()
                })
                .show(childFragmentManager)
        }

        mBinding.btnSave.setOnClickListener {
            onGo.onClick(Category("1",mBinding.txtName.text.toString(),color))
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
        fun onClick(category: Category)
    }



}