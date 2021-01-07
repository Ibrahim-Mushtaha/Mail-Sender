package com.ix.ibrahim7.facebookintegration.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.facebook.FacebookSdk
import com.facebook.share.widget.ShareDialog
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.EmailAdapter
import com.ix.ibrahim7.facebookintegration.databinding.FragmentHomeBinding
import com.ix.ibrahim7.facebookintegration.model.Email
import com.ix.ibrahim7.facebookintegration.ui.dialog.AddEmailDialog
import com.ix.ibrahim7.facebookintegration.ui.dialog.ChooseColorDialog
import com.ix.ibrahim7.facebookintegration.util.Constant.USERID
import com.ix.ibrahim7.facebookintegration.util.Constant.getSharePref
import com.ix.ibrahim7.facebookintegration.util.Constant.setImage
import kotlinx.android.synthetic.main.fragment_main.*


class HomeFragment : Fragment(),EmailAdapter.onClick,AddEmailDialog.GoFragmentMessage {


    lateinit var mbinding: FragmentHomeBinding
    lateinit var shareDialog: ShareDialog

    private val email_adapter by lazy {
        EmailAdapter(ArrayList(),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FacebookSdk.sdkInitialize(requireContext())
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id))
        FacebookSdk.setApplicationName(getString(R.string.fb_login_protocol_scheme))
        mbinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mbinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getImage(getSharePref(requireContext()).getString(USERID,"")!!)


        mbinding.listEmail.apply {
            adapter=email_adapter
        }

        shareDialog = ShareDialog(requireActivity())

        requireActivity().bottom_nav.menu[3].apply {
            setIcon(R.drawable.ic_add)
            setOnMenuItemClickListener {
                AddEmailDialog(this@HomeFragment).show(childFragmentManager,"")
                true
            }
        }

        mbinding.btnClick.setOnClickListener {
            //ChooseColorDialog().show(childFragmentManager,"")
        }
            /*GMailSender.withAccount("ibrahim.mushtaha2@gmail.com", "Ibrahim6070$")
                .withTitle("Android app")
                .withBody("test message")
                .withSender("ibrahim.mushtaha2@gmail.com")
                .toEmailAddress("ibrahim.mushtaha1999@gmail.com") // one or multiple addresses separated by a comma
                .withListenner(object : GmailListener {
                    override fun sendSuccess() {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    }

                    override fun sendFail(err: String) {
                        Toast.makeText(requireContext(), "Fail: $err", Toast.LENGTH_SHORT).show()
                    }
                })
                .send()*/

            /*val content =
                ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("shareContentUrl"))
                    .build()
            shareDialog.show(content)*/

        super.onViewCreated(view, savedInstanceState)
    }

    fun getImage(id:String){
        val image_url =
            "http://graph.facebook.com/$id/picture?type=large"
        setImage(requireContext(),image_url,mbinding.tvProfileImage,R.drawable.ic_profile_img)
    }

    override fun onClickItem(email: Email, position: Int, type: Int) {

    }

    override fun onClick(email: Email,type: Boolean) {
        email_adapter.data.add(email)
        email_adapter.notifyDataSetChanged()
        if (email_adapter.data.isNotEmpty()) mbinding.emptyContanier.visibility=View.INVISIBLE
    }
}