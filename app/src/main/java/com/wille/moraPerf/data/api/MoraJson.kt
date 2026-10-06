package com.wille.moraPerf.data.api

import kotlinx.serialization.json.Json

/**
 * Shared JSON configuration. `ignoreUnknownKeys` matters: the daemon's payloads
 * carry fields the app does not model yet (`leds`, `config_rev`, …), and they
 * must not break parsing.
 */
internal val moraJson = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}
