package me.arkteek.worsetagram.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.arkteek.worsetagram.ui.views.HomeView
import me.arkteek.worsetagram.ui.views.ProfileView
import me.arkteek.worsetagram.ui.views.SearchView
import me.arkteek.worsetagram.ui.views.chat.ChatView
import me.arkteek.worsetagram.ui.views.chat.ChatsListView

@Composable
fun Router(
    controller: NavHostController,
    changePage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
  NavHost(modifier = modifier, navController = controller, startDestination = "Home") {
    composable(route = "Home") { HomeView(changePage) }
    composable(route = "Search") { SearchView(changePage) }
    composable(route = "ChatsList") { ChatsListView(changePage) }
    composable(route = "Profile") { ProfileView(changePage) }
    composable(route = "Settings") {}
    composable(route = "Post") {}
    composable(route = "AddPost") {}
    composable(route = "Chat") { ChatView(changePage) }
    composable(route = "ProfileEdit") {}
  }
}
