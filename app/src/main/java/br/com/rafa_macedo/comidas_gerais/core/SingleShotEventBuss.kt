package br.com.rafa_macedo.comidas_gerais.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SingleShotEventBus<T> {
    private val _events = Channel<T>()
    val events = _events.receiveAsFlow() // expose as flow

    suspend fun postEvent(event: T) {
        _events.send(event) // suspends on buffer overflow
    }
}