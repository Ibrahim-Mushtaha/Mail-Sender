package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.CategoryAdapter
import com.ix.ibrahim7.facebookintegration.databinding.FragmentListEmailBinding
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.ui.dialog.ChooseColorDialog
import kotlinx.android.synthetic.main.fragment_main.*


class ListEmailFragment : Fragment(),CategoryAdapter.onClick ,ChooseColorDialog.GoFragmentMessage{

    lateinit var mBinding : FragmentListEmailBinding


    private val category_adapter by lazy {
        CategoryAdapter(ArrayList(),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListEmailBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mBinding.listCategory.apply {
            adapter=category_adapter
        }

        requireActivity().bottom_nav.menu[3].setIcon(R.drawable.ic_edit)
        requireActivity().bottom_nav.menu[3].setOnMenuItemClickListener {
            ChooseColorDialog(this@ListEmailFragment).show(childFragmentManager,"")
            true
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClickItem(category: Category, position: Int, type: Int) {

    }

    override fun onClick(category: Category) {
        category_adapter.data.add(category)
        category_adapter.notifyDataSetChanged()
    }


}