package com.ix.ibrahim7.mailsender.ui.fragment.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.mailsender.BuildConfig
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.FragmentSettingsBinding
import com.ix.ibrahim7.mailsender.model.Message
import com.ix.ibrahim7.mailsender.ui.fragment.dialog.SendEmailDialog
import com.ix.ibrahim7.mailsender.other.*
import kotlinx.android.synthetic.main.fragment_main.*
import java.io.*


class SettingsFragment : Fragment(), SendEmailDialog.OnClickListener {

    lateinit var mBinding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        requireActivity().bottom_nav.menu[3].apply {
            setIcon(R.drawable.ic_add)
            setOnMenuItemClickListener {
                SendEmailDialog(this@SettingsFragment).show(childFragmentManager, "")
                true
            }
        }


        mBinding.apply {
            btnTips.setOnClickListener {
                editor(requireContext()).apply {
                    putBoolean(HOME, false)
                    putBoolean(USERLIST, false)
                    putBoolean(CATEGORY, false)
                    apply()
                }
                Snackbar.make(mBinding.root, requireActivity().getString(R.string.activate_tips), Snackbar.LENGTH_SHORT).show()
            }


            btnRate.setOnClickListener {
                val uri: Uri = Uri.parse("market://details?id=${requireActivity().packageName}")
                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                goToMarket.addFlags(
                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                )
                try {
                    startActivity(goToMarket)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=${requireActivity().packageName}")
                        )
                    )
                }
            }


            btnShare.setOnClickListener {
                shareApplication1()
            }

            btnOpenInstegram.setOnClickListener {
                openInstegram("ix.ibrahim7")
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onClick(message: Message?, type: Int) {

    }


    private fun openInstegram(username: String) {
        val uri = Uri.parse("http://instagram.com/_u/$username")
        val likeIng = Intent(Intent.ACTION_VIEW, uri)

        likeIng.setPackage("com.instagram.android")
        try {
            startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/xxx")
                )
            )
        }
    }

    private fun shareApplication1() {
        val app: ApplicationInfo = requireActivity().application.applicationInfo
        val filePath = app.sourceDir
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "*/*"

        val originalApk = File(filePath)
        try {
            //Make new directory in new location=
            var tempFile =
                File(requireActivity().externalCacheDir.toString() + "/ExtractedApk")
            //If directory doesn't exists create new
            if (!tempFile.isDirectory) if (!tempFile.mkdirs()) return
            //Get application's name and convert to lowercase
            tempFile = File(
                tempFile.path + "/" + getString(app.labelRes).replace(" ", "")
                    .toLowerCase() + ".apk"
            )
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return
                }
            }
            //Copy file to new location
            val `in`: InputStream = FileInputStream(originalApk)
            val out: OutputStream = FileOutputStream(tempFile)
            val buf = ByteArray(1024)
            var len: Int
            while (`in`.read(buf).also { len = it } > 0) {
                out.write(buf, 0, len)
            }
            `in`.close()
            out.close()
            println("File copied.")
//          intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            val photoURI = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID.toString() + ".provider",
                tempFile
            )
            //  intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            intent.putExtra(Intent.EXTRA_STREAM, photoURI)
            startActivity(Intent.createChooser(intent, "Share app via"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}