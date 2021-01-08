package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.facebookintegration.adapter.UserAdapter
import com.ix.ibrahim7.facebookintegration.databinding.FragmentListUsersBinding
import com.ix.ibrahim7.facebookintegration.model.Email
import com.ix.ibrahim7.facebookintegration.model.Users
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.AddEmailDialog
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.CategoryViewmodel
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.UsersViewmodel
import com.ix.ibrahim7.facebookintegration.util.Constant.CATEGORYID
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*
import kotlin.collections.ArrayList


class ListUsersFragment : Fragment() ,UserAdapter.onClick,AddEmailDialog.OnClickListener{


    lateinit var mBinding: FragmentListUsersBinding

    private val arg by lazy {
        requireArguments().getString(CATEGORYID)!!
    }
    private val user_adapter by lazy {
        UserAdapter(ArrayList(),this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[UsersViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().bottom_nav.visibility=View.GONE
        mBinding = FragmentListUsersBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

      //  viewModel.insertUser(Users(UUID.randomUUID().toString(),"ibrahim","Ibrahim.mushtaha2@gmail.com",arg!!)).also {
            viewModel.getAllUsers(arg)
                viewModel.UsersLiveData!!.observe(viewLifecycleOwner, Observer {
                Log.v("eee it",it.toString())
                user_adapter.data.clear()
                user_adapter.data.addAll(it)
                user_adapter.notifyDataSetChanged()
            })
        //}
        mBinding.apply {

            listUser.apply {
                adapter=user_adapter
            }

            tvback.setOnClickListener {
                findNavController().navigateUp()
            }

            tvAddEmail.setOnClickListener {
                AddEmailDialog(arg,this@ListUsersFragment).show(childFragmentManager, "")
            }


        /*viewModel.UsersLiveData!!.observe(viewLifecycleOwner, Observer {
                user_adapter.data.clear()
                user_adapter.data.addAll(it)
                user_adapter.notifyDataSetChanged()
        })*/

        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClickItem(users: Users, position: Int, type: Int) {

    }

    override fun onClick(users: Users, type: Boolean) {
        Log.v("eee insert",users.id.toString())
        Log.v("eee insert",users.categoryID.toString())
        viewModel.insertUser(users)
        user_adapter.notifyDataSetChanged()
    }

}