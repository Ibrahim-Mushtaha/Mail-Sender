package com.ix.ibrahim7.mailsender.ui.fragment.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.DialogChooseColorBinding
import com.ix.ibrahim7.mailsender.model.Category
import dev.sasikanth.colorsheet.ColorSheet
import java.util.*

class ChooseColorDialog(val onGo: OnClickListener, val category: Category?) :
    BottomSheetDialogFragment() {

    lateinit var mBinding: DialogChooseColorBinding

    private var color = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogChooseColorBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colors = resources.getIntArray(R.array.colors)

        when (category) {
            null -> {

                mBinding.tvChooseColor.setOnClickListener {
                    ColorSheet().cornerRadius(4)
                    ColorSheet().colorPicker(
                        colors = colors,
                        listener = { color ->
                            it.background.setTint(color)
                            this.color = color.toString()
                        })
                        .show(childFragmentManager)
                }

                mBinding.btnSave.setOnClickListener {
                    when {
                        TextUtils.isEmpty(mBinding.txtName.text!!.toString()) -> {
                            mBinding.txtName.error =
                                requireActivity().getString(R.string.this_is_require)
                            mBinding.txtName.requestFocus()
                            return@setOnClickListener
                        }
                        else -> {
                            if (color == "") color =
                                ContextCompat.getColor(requireContext(), R.color.green).toString()
                            onGo.onClick(
                                Category(
                                    UUID.randomUUID().toString(),
                                    mBinding.txtName.text.toString(),
                                    color
                                ), 1
                            )
                            dismiss()
                        }
                    }

                }
            }

            else -> {

                mBinding.apply {

                    color = category.color
                    txtName.setText(category.name)
                    tvChooseColor.background.setTint(category.color.toInt())

                    btnSave.setOnClickListener {
                        when {
                        TextUtils.isEmpty(mBinding.txtName.text!!.toString()) -> {
                            mBinding.txtName.error =
                                requireActivity().getString(R.string.this_is_require)
                            mBinding.txtName.requestFocus()
                            return@setOnClickListener
                        }
                            else->{
                                onGo.onClick(Category(category.id, txtName.text.toString(), color), 2)
                                dismiss()
                            }
                        }
                    }

                    tvChooseColor.setOnClickListener {
                        ColorSheet().cornerRadius(4)
                        ColorSheet().colorPicker(
                            colors = colors,
                            listener = { color ->
                                it.background.setTint(color)
                                this@ChooseColorDialog.color = color.toString()
                            })
                            .show(childFragmentManager)
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
        fun onClick(category: Category, type: Int)
    }


}