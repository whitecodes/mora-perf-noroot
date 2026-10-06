package com.wille.moraPerf.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wille.moraPerf.data.local.TokenStore
import com.wille.moraPerf.data.model.StateResponse
import com.wille.moraPerf.data.repo.MoraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MoraViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenStore = TokenStore(application)
    private val repository = MoraRepository()

    private val _connection = MutableStateFlow<ConnectionState>(ConnectionState.Loading)
    val connection: StateFlow<ConnectionState> = _connection.asStateFlow()

    private val _state = MutableStateFlow<StateResponse?>(null)
    val state: StateFlow<StateResponse?> = _state.asStateFlow()

    init {
        bootstrap()
    }

    private fun bootstrap() {
        val token = tokenStore.read()
        if (token == null) {
            _connection.value = ConnectionState.NeedsToken
            return
        }
        connect(token, remember = false)
    }

    /** Retries with the stored token. */
    fun retry() {
        if (tokenStore.read() == null) {
            _connection.value = ConnectionState.NeedsToken
        } else {
            bootstrap()
        }
    }

    /**
     * Validates a user-entered token first and only then persists it, so a typo
     * cannot replace a token that still works.
     */
    fun submitToken(raw: String) {
        val token = raw.trim()
        if (token.isEmpty()) return
        connect(token, remember = true)
    }

    /** Drops the stored token and sends the user back to the entry screen. */
    fun forgetToken() {
        tokenStore.clear()
        _state.value = null
        _connection.value = ConnectionState.NeedsToken
    }

    private fun connect(token: String, remember: Boolean) {
        viewModelScope.launch {
            _connection.value = ConnectionState.Loading
            runCatching { repository.fetchState(token) }
                .onSuccess { response ->
                    if (remember) tokenStore.save(token)
                    _state.value = response
                    _connection.value = ConnectionState.Ready
                }
                .onFailure { error ->
                    _connection.value = ConnectionState.Error(error.describeFailure())
                }
        }
    }
}

private fun Throwable.describeFailure(): String? = when (this) {
    is HttpException -> "HTTP ${code()}"
    is IOException -> message ?: javaClass.simpleName
    else -> message ?: javaClass.simpleName
}
