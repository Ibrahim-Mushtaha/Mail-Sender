package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.CategoryAdapter
import com.ix.ibrahim7.facebookintegration.databinding.FragmentListCategoryBinding
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.CategoryOptionDialog
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.ChooseColorDialog
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.CategoryViewmodel
import com.ix.ibrahim7.facebookintegration.util.Constant
import com.ix.ibrahim7.facebookintegration.util.Constant.CATEGORY
import com.ix.ibrahim7.facebookintegration.util.Constant.CATEGORYID
import com.ix.ibrahim7.facebookintegration.util.Constant.editor
import com.ix.ibrahim7.facebookintegration.util.Constant.getSharePref
import kotlinx.android.synthetic.main.fragment_main.*


class ListCategoryFragment : Fragment(), CategoryAdapter.onClick, ChooseColorDialog.OnClickListener ,CategoryOptionDialog.OnClickListener{

    lateinit var mBinding: FragmentListCategoryBinding


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
        requireActivity().bottom_nav.visibility=View.VISIBLE
        mBinding = FragmentListCategoryBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        if (!getSharePref(requireContext()).getBoolean(CATEGORY,false)) {
            Constant.enableTips(
                requireActivity(),
                requireActivity().getString(R.string.category_tips),
                mBinding.view,
                Gravity.TOP,
                3500,
                Color.parseColor("#bdc3c7")
            )
            editor(requireContext()).putBoolean(CATEGORY,true).apply()
        }

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
            ChooseColorDialog(this@ListCategoryFragment, null).show(childFragmentManager, "")
            true
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClickItem(category: Category, position: Int, type: Int) {
        when(type){
            1->{
                val bundle = Bundle().apply {
                    putString(CATEGORYID,category.id)
                }
                findNavController().navigate(R.id.action_listEmailFragment_to_listUsersFragment,bundle)
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