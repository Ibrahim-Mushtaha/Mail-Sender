package com.ix.ibrahim7.facebookintegration.ui.fragment

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
import com.ix.ibrahim7.facebookintegration.R
import com.ix.ibrahim7.facebookintegration.databinding.FragmentSplashBinding
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.SplashState
import com.ix.ibrahim7.facebookintegration.ui.viewmodel.SplashViewModel
import com.ix.ibrahim7.facebookintegration.util.Constant
import com.ix.ibrahim7.facebookintegration.util.Constant.LOGIN
import com.ix.ibrahim7.facebookintegration.util.Constant.getSharePref


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
            imgSplashLogo.clearAnimation()
            imgSplashLogo.startAnimation(a)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}