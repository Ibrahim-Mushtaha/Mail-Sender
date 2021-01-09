package com.ix.ibrahim7.mailsender.ui.fragment.main

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.adapter.EmailAdapter
import com.ix.ibrahim7.mailsender.databinding.FragmentHomeBinding
import com.ix.ibrahim7.mailsender.model.Message
import com.ix.ibrahim7.mailsender.ui.fragment.dialog.SendEmailDialog
import com.ix.ibrahim7.mailsender.ui.viewmodel.HomeViewmodel
import com.ix.ibrahim7.mailsender.util.Constant
import com.ix.ibrahim7.mailsender.util.Constant.DURATION
import com.ix.ibrahim7.mailsender.util.Constant.HOME
import com.ix.ibrahim7.mailsender.util.Constant.dialog
import com.ix.ibrahim7.mailsender.util.Constant.editor
import com.ix.ibrahim7.mailsender.util.Constant.enableTips
import com.ix.ibrahim7.mailsender.util.Constant.getSharePref
import com.ix.ibrahim7.mailsender.util.Constant.setImage
import kotlinx.android.synthetic.main.fragment_main.*


class HomeFragment : Fragment(), EmailAdapter.onClick, SendEmailDialog.OnClickListener {


    lateinit var mbinding: FragmentHomeBinding

    private val email_adapter by lazy {
        EmailAdapter(ArrayList(), this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mbinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getData()
        mbinding.listEmail.apply {
            adapter = email_adapter
            layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(),R.anim.recyclerview_layout_animation)
        }

        viewModel.MessageLiveData!!.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty())  mbinding.emptyContanier.visibility=View.INVISIBLE
            email_adapter.data.clear()
            email_adapter.data.addAll(it)
            email_adapter.notifyDataSetChanged()
        })


        if (!getSharePref(requireContext()).getBoolean(HOME,false)) {
            enableTips(
                requireActivity(),
                requireActivity().getString(R.string.current_emails),
                mbinding.view,
                Gravity.TOP,
                DURATION.toLong(),
                Color.parseColor("#bdc3c7")
            )
            editor(requireContext()).putBoolean(HOME,true).apply()
        }

        requireActivity().bottom_nav.menu[3].apply {
            setIcon(R.drawable.ic_add)
            setOnMenuItemClickListener {
                SendEmailDialog(this@HomeFragment).show(childFragmentManager, "")
                true
            }
        }



        super.onViewCreated(view, savedInstanceState)
    }


    fun getData() {
        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personPhoto: Uri? = acct.photoUrl
            mbinding.apply {
                tvUserName.text = personName
                tvUserEmail.text = personEmail
                setImage(
                    requireContext(),
                    Uri.parse(personPhoto.toString()),
                    tvProfileImage,
                    R.drawable.ic_profile_img
                )
            }
        }
    }


    fun getfacebookImage(id: String) {
        val image_url =
            "http://graph.facebook.com/$id/me?fields=id,name,picture"

        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken()
        ) { object1, response ->
            Log.v("eee image4", response.toString())
        }
        val b = Bundle()
        b.putString("fields", "gender ,name ,id ,first_name ,last_name")
        request.parameters = b
        request.executeAsync()

        setImage(requireContext(), image_url, mbinding.tvProfileImage, R.drawable.ic_profile_img)
    }

    override fun onClickItem(message: Message, position: Int, type: Int) {

    }

    override fun onClick(message: Message?,type: Int) {
        when (type) {
            0 -> Constant.showDialog(requireActivity())
            1 -> {
                viewModel.insertMessage(message!!)
                Snackbar.make(mbinding.root, requireActivity().getString(R.string.sended_successfully), Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            2 -> {
                Snackbar.make(mbinding.root, requireActivity().getString(R.string.failed), Snackbar.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }
        }
    }


}