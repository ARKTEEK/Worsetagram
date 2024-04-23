package me.arkteek.worsetagram.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.arkteek.worsetagram.ui.view.screen.ChatListScreen
import me.arkteek.worsetagram.ui.view.screen.ChatScreen
import me.arkteek.worsetagram.ui.view.screen.HomeScreen
import me.arkteek.worsetagram.ui.view.screen.NewPostScreen
import me.arkteek.worsetagram.ui.view.screen.ProfileScreen
import me.arkteek.worsetagram.ui.view.screen.SearchScreen

@Composable
fun Router(
    controller: NavHostController,
    changePage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
  NavHost(modifier = modifier, navController = controller, startDestination = "Home") {
    composable(route = "Home") { HomeScreen(changePage) }
    composable(route = "Search") { SearchScreen(changePage) }
    composable(route = "ChatsList") { ChatListScreen(changePage) }
    composable(route = "Profile") { ProfileScreen(changePage) }
    composable(route = "Settings") {}
    composable(route = "Post") {}
    composable(route = "NewPost") { NewPostScreen(changePage) }
    composable(route = "Chat") { ChatScreen(changePage) }
    composable(route = "ProfileEdit") {}
  }
}
