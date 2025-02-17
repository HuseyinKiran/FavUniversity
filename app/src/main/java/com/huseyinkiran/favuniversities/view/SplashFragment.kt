package com.huseyinkiran.favuniversities.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.huseyinkiran.favuniversities.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.splash_background)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        lifecycleScope.launch {
            delay(2500)
            val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.toolbar_softCyan)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
    }

}