package com.wille.moraPerf.ui

sealed interface ConnectionState {

    /** Contacting the daemon. */
    data object Loading : ConnectionState

    /** No token stored yet; the user has to enter one. */
    data object NeedsToken : ConnectionState

    /** The daemon answered and the token is accepted. */
    data object Ready : ConnectionState

    /**
     * The daemon could not be reached or rejected the token. [detail] is a
     * technical hint for the UI, never the token itself.
     */
    data class Error(val detail: String?) : ConnectionState
}
