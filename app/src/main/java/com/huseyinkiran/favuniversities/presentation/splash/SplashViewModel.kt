package com.huseyinkiran.favuniversities.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val eventChannel = Channel<NavDirections>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun startSplash() = viewModelScope.launch {
        val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        delay(1.5.seconds)
        eventChannel.send(action)
    }

}