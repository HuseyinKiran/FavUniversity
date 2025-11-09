package com.huseyinkiran.favuniversities.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyinkiran.favuniversities.R
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed()
        startedSplash()
        observe()
        viewModel.startSplash()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow.collect { toHome ->
                    findNavController().navigate(toHome)
                }
            }
        }
    }

    private fun startedSplash() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.bg_splash)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isGone = true
    }

    private fun exitOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.bg_toolbar)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).isVisible = true
    }

}