package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.R.attr.button
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.EmailAdapter
import com.ix.ibrahim7.facebookintegration.databinding.FragmentHomeBinding
import com.ix.ibrahim7.facebookintegration.model.Email
import com.ix.ibrahim7.facebookintegration.ui.fragment.dialog.SendEmailDialog
import com.ix.ibrahim7.facebookintegration.util.Constant
import com.ix.ibrahim7.facebookintegration.util.Constant.HOME
import com.ix.ibrahim7.facebookintegration.util.Constant.dialog
import com.ix.ibrahim7.facebookintegration.util.Constant.editor
import com.ix.ibrahim7.facebookintegration.util.Constant.enableTips
import com.ix.ibrahim7.facebookintegration.util.Constant.getSharePref
import com.ix.ibrahim7.facebookintegration.util.Constant.setImage
import kotlinx.android.synthetic.main.fragment_main.*


class HomeFragment : Fragment(), EmailAdapter.onClick, SendEmailDialog.OnClickListener {


    lateinit var mbinding: FragmentHomeBinding

    private val email_adapter by lazy {
        EmailAdapter(ArrayList(), this)
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
        }


        if (!getSharePref(requireContext()).getBoolean(HOME,false)) {
            enableTips(
                requireActivity(),
                "Click on Next button to proceed...",
                mbinding.tvProfileImage,
                Gravity.LEFT,
                3500,
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

    override fun onClickItem(email: Email, position: Int, type: Int) {

    }

    override fun onClick(type: Int) {
        when (type) {
            0 -> Constant.showDialog(requireActivity())
            1 -> {
                Snackbar.make(mbinding.root, "Sanded Successfully", Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            2 -> {
                Snackbar.make(mbinding.root, "Failed please try again later", Snackbar.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }
        }
    }


    /*override fun onClick(email: Email, type: Boolean) {
        email_adapter.data.add(email)
        email_adapter.notifyDataSetChanged()
        if (email_adapter.data.isNotEmpty()) mbinding.emptyContanier.visibility = View.INVISIBLE
    }*/
}