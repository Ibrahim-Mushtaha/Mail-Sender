package com.ix.ibrahim7.mailsender.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.adapter.UserAdapter
import com.ix.ibrahim7.mailsender.databinding.FragmentListUsersBinding
import com.ix.ibrahim7.mailsender.model.Category
import com.ix.ibrahim7.mailsender.model.Users
import com.ix.ibrahim7.mailsender.ui.fragment.dialog.AddEmailDialog
import com.ix.ibrahim7.mailsender.ui.fragment.dialog.OptionDialog
import com.ix.ibrahim7.mailsender.ui.viewmodel.UsersViewmodel
import com.ix.ibrahim7.mailsender.util.Constant
import com.ix.ibrahim7.mailsender.util.Constant.CATEGORYID
import com.ix.ibrahim7.mailsender.util.Constant.DURATION
import com.ix.ibrahim7.mailsender.util.Constant.TAG
import com.ix.ibrahim7.mailsender.util.Constant.USERLIST
import com.ix.ibrahim7.mailsender.util.Constant.editor
import com.ix.ibrahim7.mailsender.util.Constant.getSharePref
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.collections.ArrayList


class ListUsersFragment : Fragment(), UserAdapter.onClick, AddEmailDialog.OnClickListener,OptionDialog.OnClickListener {


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
            if (user_adapter.data.isEmpty()) mBinding.emptyContanier.visibility = View.VISIBLE else mBinding.emptyContanier.visibility = View.INVISIBLE
            user_adapter.notifyDataSetChanged()
        })

        if (!getSharePref(requireContext()).getBoolean(USERLIST,false)) {
            Constant.enableTips(
                requireActivity(),
                requireActivity().getString(R.string.list_user),
                mBinding.view,
                Gravity.TOP,
                DURATION.toLong(),
                Color.parseColor("#bdc3c7")
            )
            editor(requireContext()).putBoolean(USERLIST,true).apply()
        }

        mBinding.apply {

            listUser.apply {
                adapter = user_adapter
                layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(),R.anim.recyclerview_layout_animation)
            }

            tvback.setOnClickListener {
                findNavController().navigateUp()
            }

            tvAddEmail.setOnClickListener {
                AddEmailDialog(arg, null,this@ListUsersFragment).show(childFragmentManager, "")
            }


        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClickItem(users: Users, position: Int, type: Int) {
    when(type){
        1->{
            OptionDialog(this,null,users,2).show(childFragmentManager,"")
        }
    }
    }

    override fun onClick(users: Users, type: Int) {
        when(type) {
            1-> viewModel.insertUser(users)
            else-> viewModel.updateUser(users)
        }
    }

    override fun onClick(category: Category?, users: Users?) {
        AddEmailDialog(users!!.categoryID,users,this).show(childFragmentManager,"")
    }

}