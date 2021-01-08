package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.UserAdapter
import com.ix.ibrahim7.facebookintegration.databinding.FragmentListUsersBinding
import com.ix.ibrahim7.facebookintegration.model.Users
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.AddEmailDialog
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.UsersViewmodel
import com.ix.ibrahim7.facebookintegration.util.Constant
import com.ix.ibrahim7.facebookintegration.util.Constant.CATEGORYID
import com.ix.ibrahim7.facebookintegration.util.Constant.TAG
import com.ix.ibrahim7.facebookintegration.util.Constant.USERLIST
import com.ix.ibrahim7.facebookintegration.util.Constant.editor
import com.ix.ibrahim7.facebookintegration.util.Constant.getSharePref
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.collections.ArrayList


class ListUsersFragment : Fragment(), UserAdapter.onClick, AddEmailDialog.OnClickListener {


    lateinit var mBinding: FragmentListUsersBinding

    private val arg by lazy {
        requireArguments().getString(CATEGORYID)!!
    }
    private val user_adapter by lazy {
        UserAdapter(ArrayList(), this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[UsersViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().bottom_nav.visibility = View.GONE
        mBinding = FragmentListUsersBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getAllUser(arg)

        viewModel.CategoryAndUsersLiveData!!.observe(viewLifecycleOwner, Observer {CategoryAndUsers->
            user_adapter.data.clear()
            CategoryAndUsers.forEach {
                user_adapter.data.addAll(it.users)
            }
            user_adapter.notifyDataSetChanged()
        })

        if (!getSharePref(requireContext()).getBoolean(USERLIST,false)) {
            Constant.enableTips(
                requireActivity(),
                requireActivity().getString(R.string.list_user),
                mBinding.view,
                Gravity.TOP,
                3500,
                Color.parseColor("#bdc3c7")
            )
            editor(requireContext()).putBoolean(USERLIST,true).apply()
        }

        mBinding.apply {

            listUser.apply {
                adapter = user_adapter
            }

            tvback.setOnClickListener {
                findNavController().navigateUp()
            }

            tvAddEmail.setOnClickListener {
                AddEmailDialog(arg, this@ListUsersFragment).show(childFragmentManager, "")
            }


        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClickItem(users: Users, position: Int, type: Int) {

    }

    override fun onClick(users: Users, type: Boolean) {
        Log.v("$TAG insert User", users.categoryID)
        viewModel.insertUser(users)
        user_adapter.notifyDataSetChanged()
    }

}