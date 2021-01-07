package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.CategoryAdapter
import com.ix.ibrahim7.facebookintegration.databinding.FragmentListEmailBinding
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.CategoryOptionDialog
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.ChooseColorDialog
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.CategoryViewmodel
import kotlinx.android.synthetic.main.fragment_main.*


class ListEmailFragment : Fragment(), CategoryAdapter.onClick, ChooseColorDialog.OnClickListener ,CategoryOptionDialog.OnClickListener{

    lateinit var mBinding: FragmentListEmailBinding


    private val category_adapter by lazy {
        CategoryAdapter(ArrayList(), this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CategoryViewmodel::class.java]
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
            adapter = category_adapter
        }

        viewModel.CategoryLiveData!!.observe(viewLifecycleOwner, Observer { category ->
            category_adapter.data.clear()
            category_adapter.data.addAll(category)
            category_adapter.notifyDataSetChanged()
        })

        requireActivity().bottom_nav.menu[3].setIcon(R.drawable.ic_edit)
        requireActivity().bottom_nav.menu[3].setOnMenuItemClickListener {
            ChooseColorDialog(this@ListEmailFragment, null).show(childFragmentManager, "")
            true
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClickItem(category: Category, position: Int, type: Int) {
        when(type){
            1->{

            }
            2->{
                CategoryOptionDialog(this,category).show(childFragmentManager,"")
            }
        }
    }

    override fun onClick(category: Category, type: Int) {
        when (type) {
            1 -> viewModel.insertCategory(category)
            else -> viewModel.updateCategory(category)
        }
    }

    override fun onClick(category: Category,type: Boolean) {
        ChooseColorDialog(this, category).show(childFragmentManager, "")
    }


}