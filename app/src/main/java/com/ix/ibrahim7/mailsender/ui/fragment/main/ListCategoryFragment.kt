package com.ix.ibrahim7.mailsender.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.adapter.CategoryAdapter
import com.ix.ibrahim7.mailsender.databinding.FragmentListCategoryBinding
import com.ix.ibrahim7.mailsender.model.Category
import com.ix.ibrahim7.mailsender.model.Users
import com.ix.ibrahim7.mailsender.ui.fragment.dialog.OptionDialog
import com.ix.ibrahim7.mailsender.ui.fragment.dialog.ChooseColorDialog
import com.ix.ibrahim7.mailsender.ui.viewmodel.CategoryViewmodel
import com.ix.ibrahim7.mailsender.other.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.ArrayList


class ListCategoryFragment : Fragment(), CategoryAdapter.onClick, ChooseColorDialog.OnClickListener,
    OptionDialog.OnClickListener {

    lateinit var mBinding: FragmentListCategoryBinding

    companion object {
        val data = ArrayList<Category>()
    }


    private val category_adapter by lazy {
        CategoryAdapter(data, this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CategoryViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().bottom_nav.visibility = View.VISIBLE
        mBinding = FragmentListCategoryBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        if (!getSharePref(requireContext()).getBoolean(CATEGORY, false)) {
            enableTips(
                requireActivity(),
                requireActivity().getString(R.string.category_tips),
                mBinding.view,
                Gravity.TOP,
                DURATION.toLong(),
                Color.parseColor("#bdc3c7")
            )
            editor(requireContext()).putBoolean(CATEGORY, true).apply()
        }

        mBinding.listCategory.apply {
            adapter = category_adapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.recyclerview_layout_animation
            )
        }

        viewModel.CategoryLiveData!!.observe(viewLifecycleOwner, Observer { category ->
            if (category.isEmpty()) mBinding.emptyContanier.visibility =
                View.VISIBLE else mBinding.emptyContanier.visibility = View.INVISIBLE
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
        when (type) {
            1 -> {
                val bundle = Bundle().apply {
                    putString(CATEGORYID, category.id)
                }
                findNavController().navigate(
                    R.id.action_listEmailFragment_to_listUsersFragment,
                    bundle
                )
            }
            2 -> {
                OptionDialog(this, category, null, 1).show(childFragmentManager, "")
            }
        }
    }

    override fun onClick(category: Category, type: Int) {
        when (type) {
            1 -> {
                viewModel.insertCategory(category)
            }
            else -> viewModel.updateCategory(category)
        }
    }

    override fun onClick(category: Category?, users: Users?) {
        ChooseColorDialog(this, category).show(childFragmentManager, "")
    }

}