package com.wille.moraPerf.ui.navigation

import androidx.annotation.StringRes
import com.wille.moraPerf.R

/**
 * Top-level destinations shown in the navigation drawer.
 *
 * The drawer is text-only by design, so a destination carries a label instead
 * of an icon.
 */
enum class MoraDestination(
    val route: String,
    @StringRes val labelRes: Int,
) {
    HOME("home", R.string.destination_home),
    PROFILES("profiles", R.string.destination_profiles),
    GAMES("games", R.string.destination_games),
    LED("led", R.string.destination_led),
    SETTINGS("settings", R.string.destination_settings),
}
