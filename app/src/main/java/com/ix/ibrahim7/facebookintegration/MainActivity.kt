package com.ix.ibrahim7.facebookintegration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.ix.ibrahim7.facebookintegration.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var mbinding: ActivityMainBinding
    lateinit var callbackManager:CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id))
        FacebookSdk.setApplicationName(getString(R.string.fb_login_protocol_scheme))
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mbinding.root)


        callbackManager = CallbackManager.Factory.create()
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
        })




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}