package me.arkteek.worsetagram.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.arkteek.worsetagram.common.ROUTE_CHAT
import me.arkteek.worsetagram.common.ROUTE_HOME
import me.arkteek.worsetagram.common.ROUTE_LOGIN
import me.arkteek.worsetagram.common.ROUTE_NEW_POST
import me.arkteek.worsetagram.common.ROUTE_POST
import me.arkteek.worsetagram.common.ROUTE_PROFILE
import me.arkteek.worsetagram.common.ROUTE_SEARCH
import me.arkteek.worsetagram.common.ROUTE_SIGNUP
import me.arkteek.worsetagram.ui.screen.HomeScreen
import me.arkteek.worsetagram.ui.screen.auth.LoginScreen
import me.arkteek.worsetagram.ui.screen.auth.SignupScreen
import me.arkteek.worsetagram.ui.viewModel.AuthViewModel

@Composable
fun AppNavHost(
  viewModel: AuthViewModel,
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  startDestination: String = ROUTE_LOGIN,
) {
  NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
    composable(ROUTE_LOGIN) { LoginScreen(viewModel, navController) }
    composable(ROUTE_SIGNUP) { SignupScreen(viewModel, navController) }
    composable(ROUTE_HOME) { HomeScreen(viewModel, navController) }
    composable(ROUTE_SEARCH) {}
    composable(ROUTE_CHAT) {}
    composable(ROUTE_PROFILE) {}
    composable(ROUTE_POST) {}
    composable(ROUTE_NEW_POST) {}
  }
}
