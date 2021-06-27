package com.ix.ibrahim7.mailsender.ui.fragment.splash

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.mailsender.R
import com.ix.ibrahim7.mailsender.databinding.FragmentSplashBinding
import com.ix.ibrahim7.mailsender.ui.viewmodel.SplashState
import com.ix.ibrahim7.mailsender.ui.viewmodel.SplashViewModel
import com.ix.ibrahim7.mailsender.other.*


class SplashFragment : Fragment() {

    lateinit var mbinding :FragmentSplashBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mbinding = FragmentSplashBinding.inflate(inflater, container, false).apply {
            executePendingBindings()
        }
        return mbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mbinding.tvVersionCode.text = requireActivity().getString(R.string.version) + getInfo().versionName

        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SplashState.MainActivity -> {
                    if (getSharePref(requireContext()).getBoolean(LOGIN,false))
                        findNavController().navigate(R.id.action_splashFragment_to_mainFragment2)
                    else
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        })


        val a: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        a.reset()

        mbinding.apply {
            logoContainer.clearAnimation()
            logoContainer.startAnimation(a)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getInfo():PackageInfo{
        val manager = requireActivity().packageManager
        return manager.getPackageInfo(requireActivity().packageName, PackageManager.GET_ACTIVITIES)
    }
}