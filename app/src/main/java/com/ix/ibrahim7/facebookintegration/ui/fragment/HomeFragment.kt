package com.ix.ibrahim7.facebookintegration.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.FacebookSdk
import com.facebook.share.widget.ShareDialog
import com.github.tntkhang.gmailsenderlibrary.GMailSender
import com.github.tntkhang.gmailsenderlibrary.GmailListener
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentHomeBinding
import com.ix.ibrahim7.facebookintegration.ui.dialog.AddEmailDialog
import com.ix.ibrahim7.facebookintegration.util.Constant.USERID
import com.ix.ibrahim7.facebookintegration.util.Constant.getSharePref
import com.ix.ibrahim7.facebookintegration.util.Constant.setImage


class HomeFragment : Fragment() {


    lateinit var mbinding: FragmentHomeBinding
    lateinit var shareDialog: ShareDialog


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

        shareDialog = ShareDialog(requireActivity())

        mbinding.btnClick.setOnClickListener {
            AddEmailDialog().show(childFragmentManager,"")
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
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun getImage(id:String){
        val image_url =
            "http://graph.facebook.com/$id/picture?type=large"
        setImage(requireContext(),image_url,mbinding.tvProfileImage,R.drawable.ic_profile_img)
    }
}