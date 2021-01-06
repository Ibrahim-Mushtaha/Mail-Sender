package com.ix.ibrahim7.facebookintegration.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginResult
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentLoginBinding
import com.ix.ibrahim7.facebookintegration.util.Constant.EMAIL
import com.ix.ibrahim7.facebookintegration.util.Constant.LOGIN
import com.ix.ibrahim7.facebookintegration.util.Constant.TAG
import com.ix.ibrahim7.facebookintegration.util.Constant.USERID
import com.ix.ibrahim7.facebookintegration.util.Constant.editor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException


class LoginFragment : Fragment() {

    lateinit var mbinding: FragmentLoginBinding
    lateinit var callbackManager: CallbackManager
    var text =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callbackManager = CallbackManager.Factory.create()
        FacebookSdk.sdkInitialize(requireContext())
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id))
        FacebookSdk.setApplicationName(getString(R.string.fb_login_protocol_scheme))
        mbinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mbinding.apply {


            loginButton.apply {
                setReadPermissions(EMAIL)
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                fragment = this@LoginFragment
            }

            // Callback registration
            loginButton.registerCallback(callbackManager, object :
                FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    editor(requireContext()).apply {
                        putString(USERID,AccessToken.getCurrentAccessToken()!!.userId)
                        putBoolean(LOGIN, true)
                            apply()
                    }
                    RequestData()
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment2)
                    Log.e("eee success", AccessToken.getCurrentAccessToken()!!.toString())
                }

                override fun onCancel() {
                    Log.e("eee", "cancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.e("eee error", exception.message.toString())
                    editor(requireContext()).putBoolean(LOGIN,false).apply()
                }

            })


        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun RequestData() {
        GlobalScope.launch  (Dispatchers.IO) {
            val request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken()
            ) { object1, response ->
                val json = response.jsonObject
                try {
                    if (json != null) {
                         text =
                            "<b>Name :</b> " + json.getString("name") + "<br><br><b>Email :</b> " + json.getString(
                                "email"
                            ) + "<br><br><b>Profile link :</b> " + json.getString("link")
                        /*details_txt.setText(Html.fromHtml(text));
                                profile.setProfileId(json.getString("id"));*/Log.e(
                            TAG,
                            json.getString("name")
                        )
                        Log.v(TAG, json.getString("email"))
                        Log.v(TAG, json.getString("id"))
                        //web.loadData(text, "text/html", "UTF-8");
                    } else {
                        Log.v(TAG, "json null")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val parameters = Bundle()
            parameters.putString("fields", "id,name,link,email,picture")
            request.parameters = parameters
            Log.v("$TAG data", text)
            request.executeAsync()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}