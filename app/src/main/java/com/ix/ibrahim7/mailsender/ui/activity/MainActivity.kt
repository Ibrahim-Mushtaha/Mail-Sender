package com.ix.ibrahim7.mailsender.ui.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.ActivityMainBinding
import com.ix.ibrahim7.mailsender.other.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    lateinit var mbinding: ActivityMainBinding

    private val FACEBOOK_KEY = "com.ix.ibrahim7.facebookintegration"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id))
        FacebookSdk.setApplicationName(getString(R.string.fb_login_protocol_scheme))
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        setUpStatusBar(this,1)
        setSupportActionBar(toolbar)
        toolbar.visibility= View.GONE


        printkeyHash()
    }


    /*
    funcation to get the Key hash tags
     */
    private fun printkeyHash(){
        try {
            val info = packageManager.getPackageInfo(FACEBOOK_KEY, PackageManager.GET_SIGNATURES)
            for (Signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(Signature.toByteArray())
            }
        }catch (e:Exception){
            Log.v(
                "eee Exception ",
             e.message.toString()
            )
        }
    }

}