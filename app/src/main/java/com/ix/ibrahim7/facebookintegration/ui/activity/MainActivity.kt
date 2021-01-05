package com.ix.ibrahim7.facebookintegration.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.ix.ibrahim7.facebookintegration.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    lateinit var mbinding: ActivityMainBinding
    lateinit var callbackManager:CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    /*    FacebookSdk.sdkInitialize(this)
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id))
        FacebookSdk.setApplicationName(getString(R.string.fb_login_protocol_scheme))*/
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        setSupportActionBar(toolbar)
        toolbar.visibility= View.GONE
      /*  callbackManager = CallbackManager.Factory.create()
        login_button.setReadPermissions("email");

        // Callback registration
        mbinding.loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
                val accessToken = AccessToken.getCurrentAccessToken().userId
                val url = "http://graph.facebook.com/"
                Log.v("eee success",accessToken.toString())
            }

            override fun onCancel() {
                Log.v("eee","cancel")
                // App code
            }

            override fun onError(exception: FacebookException) {
                Log.v("eee error",exception.message.toString())
                // App code
            }
        })*/
        printkeyHash()
    }


    /*
    funcation to get the Key hash tags
     */
    private fun printkeyHash(){
        try {
            val info = packageManager.getPackageInfo("com.ix.ibrahim7.facebookintegration", PackageManager.GET_SIGNATURES)
            for (Signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(Signature.toByteArray())
                Log.v(
                    "eee key ",
                    android.util.Base64.encodeToString(md.digest(), android.util.Base64.DEFAULT)
                )
            }
        }catch (e:Exception){
            Log.v(
                "eee Exception ",
             e.message.toString()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      //  callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}