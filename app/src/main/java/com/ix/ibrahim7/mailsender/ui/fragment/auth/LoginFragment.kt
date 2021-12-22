package com.ix.ibrahim7.mailsender.ui.fragment.auth

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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.FragmentLoginBinding
import com.ix.ibrahim7.mailsender.other.*


class LoginFragment : Fragment() {

    lateinit var mbinding: FragmentLoginBinding
    lateinit var callbackManager: CallbackManager
    private val RC_SIGN_IN = 100

    lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callbackManager = CallbackManager.Factory.create()
        mbinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mbinding.apply {

            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

            loginButton.apply {
                setReadPermissions(EMAIL)
                setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                fragment = this@LoginFragment
            }

            // google sign in
            signInButton.setOnClickListener {
                signIn()
            }


            // facebook Callback registration
            loginButton.registerCallback(callbackManager, object :
                FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    editor(requireContext()).apply {
                        putString(USERID, AccessToken.getCurrentAccessToken()!!.userId)
                        putBoolean(LOGIN, true)
                        apply()
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment2)
                    Log.e("$TAG success", AccessToken.getCurrentAccessToken()!!.toString())
                }

                override fun onCancel() {
                    Log.e(TAG, "cancel")
                }

                override fun onError(exception: FacebookException) {
                    Log.e("$TAG error", exception.message.toString())
                    editor(requireContext()).putBoolean(LOGIN, false).apply()
                }

            })


        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(data!!)
        handleSignInResult(task)
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*
    handle the result of google sign in
    */
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            editor(requireContext()).apply {
                putBoolean(LOGIN, true)
                apply()
            }
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment2)
            Log.v(TAG + "name", account!!.displayName.toString())
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            e.printStackTrace()
        }
    }

}