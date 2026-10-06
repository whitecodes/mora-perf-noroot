package com.wille.moraPerf.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wille.moraPerf.R
import com.wille.moraPerf.ui.navigation.MoraDestination
import com.wille.moraPerf.ui.screens.ErrorScreen
import com.wille.moraPerf.ui.screens.GamesScreen
import com.wille.moraPerf.ui.screens.HomeScreen
import com.wille.moraPerf.ui.screens.LedScreen
import com.wille.moraPerf.ui.screens.LoadingScreen
import com.wille.moraPerf.ui.screens.ProfilesScreen
import com.wille.moraPerf.ui.screens.SettingsScreen
import com.wille.moraPerf.ui.screens.TokenEntryScreen
import kotlinx.coroutines.launch

private val SquareShape = RoundedCornerShape(0.dp)

/**
 * Gates the whole app on the daemon connection: without a working token there
 * is nothing to configure, so the shell is only shown once [ConnectionState.Ready].
 */
@Composable
fun MoraApp(viewModel: MoraViewModel = viewModel()) {
    val connection by viewModel.connection.collectAsState()

    when (val current = connection) {
        ConnectionState.Loading -> LoadingScreen()

        ConnectionState.NeedsToken -> TokenEntryScreen(onSubmit = viewModel::submitToken)

        ConnectionState.Ready -> MoraShell()

        is ConnectionState.Error -> ErrorScreen(
            detail = current.detail,
            onRetry = viewModel::retry,
            onChangeToken = viewModel::forgetToken,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoraShell() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: MoraDestination.HOME.route
    val currentDestination =
        MoraDestination.entries.firstOrNull { it.route == currentRoute } ?: MoraDestination.HOME

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = SquareShape,
                drawerContainerColor = MaterialTheme.colorScheme.surface,
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
                )
                MoraDestination.entries.forEach { destination ->
                    NavigationDrawerItem(
                        label = { Text(stringResource(destination.labelRes)) },
                        selected = destination == currentDestination,
                        shape = SquareShape,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (destination.route != currentRoute) {
                                navController.navigate(destination.route) { launchSingleTop = true }
                            }
                        },
                    )
                }
            }
        },
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(currentDestination.labelRes)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = stringResource(R.string.action_open_menu),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                )
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = MoraDestination.HOME.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(MoraDestination.HOME.route) { HomeScreen() }
                composable(MoraDestination.PROFILES.route) { ProfilesScreen() }
                composable(MoraDestination.GAMES.route) { GamesScreen() }
                composable(MoraDestination.LED.route) { LedScreen() }
                composable(MoraDestination.SETTINGS.route) { SettingsScreen() }
            }
        }
    }
}
