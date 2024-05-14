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
import me.arkteek.worsetagram.ui.screen.chat.ChatListScreen
import me.arkteek.worsetagram.ui.screen.HomeScreen
import me.arkteek.worsetagram.ui.screen.profile.NewPostScreen
import me.arkteek.worsetagram.ui.screen.profile.ProfileScreen
import me.arkteek.worsetagram.ui.screen.SearchScreen
import me.arkteek.worsetagram.ui.screen.authentication.LoginScreen
import me.arkteek.worsetagram.ui.screen.authentication.SignupScreen
import me.arkteek.worsetagram.ui.screen.authentication.AuthViewModel

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
    composable(ROUTE_SEARCH) { SearchScreen(viewModel, navController) }
    composable(ROUTE_CHAT) { ChatListScreen(viewModel, navController) }
    composable(ROUTE_PROFILE) { ProfileScreen(viewModel, navController) }
    composable(ROUTE_POST) { NewPostScreen(viewModel, navController) }
    composable(ROUTE_NEW_POST) {}
  }
}
