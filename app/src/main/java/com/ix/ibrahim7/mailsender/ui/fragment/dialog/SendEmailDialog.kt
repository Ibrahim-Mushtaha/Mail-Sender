package com.ix.ibrahim7.mailsender.ui.fragment.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.facebook.share.widget.ShareDialog
import com.github.tntkhang.gmailsenderlibrary.GMailSender
import com.github.tntkhang.gmailsenderlibrary.GmailListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.adapter.CategoryDialogAdapter
import com.ix.ibrahim7.mailsender.databinding.DialogSendEmailBinding
import com.ix.ibrahim7.mailsender.model.Category
import com.ix.ibrahim7.mailsender.model.Message
import com.ix.ibrahim7.mailsender.other.getSnackBar
import com.ix.ibrahim7.mailsender.ui.viewmodel.CategoryViewmodel
import java.util.*
import kotlin.collections.ArrayList


class SendEmailDialog(val onGo: OnClickListener) : BottomSheetDialogFragment(),
    CategoryDialogAdapter.onClick {

    lateinit var mBinding: DialogSendEmailBinding

    lateinit var shareDialog: ShareDialog

    private val category_adapter by lazy {
        CategoryDialogAdapter(ArrayList(), this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CategoryViewmodel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogSendEmailBinding.inflate(inflater, container, false).apply {
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

        mBinding.txtEmailfrom.setText(getData().email)

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
                    onGo.onClick(null, 0)
                    GMailSender.withAccount(getData().email, "Ibrahim6070$")
                        .withTitle(requireActivity().getString(R.string.app_name))
                        .withBody(mBinding.txtEmailNote.text.toString())
                        .withSender(getData().email)
                        .toEmailAddress(mBinding.txtEmailTo.text.toString()) // one or multiple addresses separated by a comma
                        .withListenner(object : GmailListener {
                            override fun sendSuccess() {
                                onGo.onClick(
                                    Message(
                                        UUID.randomUUID().toString(),
                                        mBinding.txtEmailTo.text.toString(),
                                        mBinding.txtEmailNote.text.toString(),
                                        Calendar.getInstance().timeInMillis.toString()
                                    ), 1
                                )
                            }

                            override fun sendFail(err: String) {
                                onGo.onClick(null, 2)
                            }
                        })
                        .send()
                    dismiss()
                }
            }
        }

        viewModel.CategoryLiveData!!.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { category ->
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


    fun getData(): GoogleSignInAccount {
        return GoogleSignIn.getLastSignedInAccount(activity)!!
    }

    interface OnClickListener {
        fun onClick(message: Message?, type: Int)
    }

    override fun onClickItem(category: Category, position: Int, type: Int) {

    }



}