package com.ix.ibrahim7.facebookintegration.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.facebookintegration.R
import dev.sasikanth.colorsheet.ColorSheet
import kotlinx.android.synthetic.main.dialog_choose_color.*
import kotlinx.android.synthetic.main.dialog_choose_color.view.*

class ChooseColorDialog() : BottomSheetDialogFragment(){



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)
        return inflater.inflate(R.layout.dialog_choose_color, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = resources.getIntArray(R.array.colors)

        view.tvChooseColor.setOnClickListener {
            ColorSheet().cornerRadius(4)
            ColorSheet().colorPicker(
                colors = colors,
                listener = { color ->
                    it.setBackgroundColor(color)
                    // Handle color

                })
                .show(childFragmentManager)
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
        fun onClick(type: Boolean)
    }



}