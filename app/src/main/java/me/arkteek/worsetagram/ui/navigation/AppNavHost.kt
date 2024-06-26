package me.arkteek.worsetagram.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.arkteek.worsetagram.common.constants.ROUTE_CHAT
import me.arkteek.worsetagram.common.constants.ROUTE_CHAT_LIST
import me.arkteek.worsetagram.common.constants.ROUTE_COMMENTS
import me.arkteek.worsetagram.common.constants.ROUTE_HOME
import me.arkteek.worsetagram.common.constants.ROUTE_LOGIN
import me.arkteek.worsetagram.common.constants.ROUTE_NEW_POST
import me.arkteek.worsetagram.common.constants.ROUTE_POST
import me.arkteek.worsetagram.common.constants.ROUTE_PROFILE
import me.arkteek.worsetagram.common.constants.ROUTE_PROFILE_OTHER
import me.arkteek.worsetagram.common.constants.ROUTE_PROFILE_SETTINGS
import me.arkteek.worsetagram.common.constants.ROUTE_SEARCH
import me.arkteek.worsetagram.common.constants.ROUTE_SIGNUP
import me.arkteek.worsetagram.ui.screen.HomeScreen
import me.arkteek.worsetagram.ui.screen.SearchScreen
import me.arkteek.worsetagram.ui.screen.authentication.LoginScreen
import me.arkteek.worsetagram.ui.screen.authentication.SignupScreen
import me.arkteek.worsetagram.ui.screen.chat.ChatListScreen
import me.arkteek.worsetagram.ui.screen.chat.ChatScreen
import me.arkteek.worsetagram.ui.screen.post.CommentScreen
import me.arkteek.worsetagram.ui.screen.post.PostCreationScreen
import me.arkteek.worsetagram.ui.screen.post.PostScreen
import me.arkteek.worsetagram.ui.screen.profile.ProfileOtherScreen
import me.arkteek.worsetagram.ui.screen.profile.ProfileSelfScreen
import me.arkteek.worsetagram.ui.screen.profile.ProfileSettingsScreen
import me.arkteek.worsetagram.ui.viewmodel.AuthViewModel
import me.arkteek.worsetagram.ui.viewmodel.HomeViewModel
import me.arkteek.worsetagram.ui.viewmodel.PostCreationViewModel
import me.arkteek.worsetagram.ui.viewmodel.PostViewModel
import me.arkteek.worsetagram.ui.viewmodel.ProfileOtherViewModel
import me.arkteek.worsetagram.ui.viewmodel.ProfileViewModel

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
    composable(ROUTE_HOME) {
      val homeViewModel: HomeViewModel = hiltViewModel()
      HomeScreen(homeViewModel, navController)
    }
    composable(ROUTE_SEARCH) { SearchScreen(viewModel, navController) }
    composable(ROUTE_CHAT) { ChatScreen(viewModel, navController) }
    composable(ROUTE_NEW_POST) {
      val postCreationViewModel: PostCreationViewModel = hiltViewModel()
      PostCreationScreen(postCreationViewModel, navController)
    }
    composable(ROUTE_CHAT_LIST) {
      ChatListScreen(viewModel = viewModel, navController = navController)
    }
    composable(
      route = ROUTE_POST,
      arguments = listOf(navArgument("postId") { type = NavType.StringType }),
    ) { entry ->
      val postId = entry.arguments?.getString("postId") ?: ""
      val postViewModel: PostViewModel = hiltViewModel()
      PostScreen(postId, navController, viewModel = postViewModel)
    }
    composable(
      route = ROUTE_COMMENTS,
      arguments = listOf(navArgument("postId") { type = NavType.StringType }),
    ) { entry ->
      val postId = entry.arguments?.getString("postId") ?: ""
      val postViewModel: PostViewModel = hiltViewModel()
      CommentScreen(postId, navController, viewModel = postViewModel)
    }
    composable(ROUTE_PROFILE_SETTINGS) {
      val profileViewModel: ProfileViewModel = hiltViewModel()
      ProfileSettingsScreen(viewModel = profileViewModel, navController)
    }
    composable(ROUTE_PROFILE) {
      val profileViewModel: ProfileViewModel = hiltViewModel()
      ProfileSelfScreen(authViewModel = viewModel, viewModel = profileViewModel, navController)
    }
    composable(
      route = ROUTE_PROFILE_OTHER,
      arguments = listOf(navArgument("profileId") { type = NavType.StringType }),
    ) { entry ->
      val profileId = entry.arguments?.getString("profileId") ?: ""
      val profileViewModel: ProfileOtherViewModel = hiltViewModel()
      ProfileOtherScreen(viewModel = profileViewModel, navController, profileId)
    }
  }
}
