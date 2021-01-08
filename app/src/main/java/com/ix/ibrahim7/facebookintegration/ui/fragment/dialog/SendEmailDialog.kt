package com.ix.ibrahim7.facebookintegration.ui.fragment.dialog

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.facebook.share.widget.ShareDialog
import com.github.tntkhang.gmailsenderlibrary.GMailSender
import com.github.tntkhang.gmailsenderlibrary.GmailListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.adapter.CategoryDialogAdapter
import com.ix.ibrahim7.facebookintegration.databinding.DialogAddEmailBinding
import com.ix.ibrahim7.facebookintegration.databinding.DialogSendEmailBinding
import com.ix.ibrahim7.facebookintegration.model.Category
import com.ix.ibrahim7.facebookintegration.model.Email
import com.ix.ibrahim7.facebookintegration.model.Users
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.CategoryViewmodel
import com.ix.ibrahim7.facebookintegration.util.Constant
import java.util.*
import kotlin.collections.ArrayList

class SendEmailDialog(val onGo: OnClickListener) : BottomSheetDialogFragment(),CategoryDialogAdapter.onClick{

    lateinit var mBinding:DialogSendEmailBinding

    private val category_adapter by lazy {
        CategoryDialogAdapter(ArrayList(),this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CategoryViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogSendEmailBinding.inflate(inflater,container,false).apply {
            executePendingBindings()
        }
        dialog!!.requestWindowFeature(STYLE_NO_TITLE)
        dialog!!.setCancelable(false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.listCategory.apply {
            adapter = category_adapter
        }

        mBinding.btnSave.setOnClickListener {
            when {
                TextUtils.isEmpty(mBinding.txtEmailTo.text!!.toString()) -> {
                    mBinding.txtEmailTo.error =
                        requireActivity().getString(R.string.this_is_require)
                    mBinding.txtEmailTo.requestFocus()
                    return@setOnClickListener
                }
                TextUtils.isEmpty(mBinding.txtEmailfrom.text!!.toString()) -> {
                    mBinding.txtEmailfrom.error =
                        requireActivity().getString(R.string.this_is_require)
                    mBinding.txtEmailfrom.requestFocus()
                    return@setOnClickListener
                }
                TextUtils.isEmpty(mBinding.txtEmailNote.text!!.toString()) -> {
                    mBinding.txtEmailNote.error =
                        requireActivity().getString(R.string.this_is_require)
                    mBinding.txtEmailNote.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    mBinding.txtEmailfrom.setText(getData().email)
                    onGo.onClick(0)
                    GMailSender.withAccount(getData().email, "Ibrahim6070$")
            .withTitle("Android app")
            .withBody(mBinding.txtEmailNote.text.toString())
            .withSender(getData().email)
            .toEmailAddress(mBinding.txtEmailTo.text.toString()) // one or multiple addresses separated by a comma
            .withListenner(object : GmailListener {
                override fun sendSuccess() {
                    onGo.onClick(1)
                }

                override fun sendFail(err: String) {
                    onGo.onClick(2)
                }
            })
            .send()

                    /*shareDialog = ShareDialog(requireActivity())
                    val content =
                        ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("shareContentUrl"))
                            .build()
                    shareDialog.show(content)*/
                    dismiss()
                }
            }
        }

        viewModel.CategoryLiveData!!.observe(viewLifecycleOwner, androidx.lifecycle.Observer { category ->
            category_adapter.data.clear()
            category_adapter.data.addAll(category)
            category_adapter.notifyDataSetChanged()
        })


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

    }


    fun getData():GoogleSignInAccount{
        return GoogleSignIn.getLastSignedInAccount(activity)!!
    }

    interface OnClickListener {
        fun onClick(type: Int)
    }

    override fun onClickItem(category: Category, position: Int, type: Int) {

    }


}