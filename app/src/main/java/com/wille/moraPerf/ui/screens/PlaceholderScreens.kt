package com.wille.moraPerf.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wille.moraPerf.R

/**
 * Screens for M0. Each one is replaced by its real implementation in a later
 * milestone (Home in M1, Profiles/LED in M2, Games in M3, Settings in M4).
 */
@Composable
fun HomeScreen() = PlaceholderScreen(R.string.destination_home)

@Composable
fun ProfilesScreen() = PlaceholderScreen(R.string.destination_profiles)

@Composable
fun GamesScreen() = PlaceholderScreen(R.string.destination_games)

@Composable
fun LedScreen() = PlaceholderScreen(R.string.destination_led)

@Composable
fun SettingsScreen() = PlaceholderScreen(R.string.destination_settings)

@Composable
private fun PlaceholderScreen(@StringRes titleRes: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = stringResource(R.string.placeholder_pending),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
