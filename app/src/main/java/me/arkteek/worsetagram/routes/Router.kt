package me.arkteek.worsetagram.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.arkteek.worsetagram.ui.Home
import me.arkteek.worsetagram.ui.Messages
import me.arkteek.worsetagram.ui.Profile
import me.arkteek.worsetagram.ui.Search

@Composable
fun Router(
    controller: NavHostController,
    changePage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
  NavHost(modifier = modifier, navController = controller, startDestination = "Home") {
    composable(route = "Home") { Home(changePage) }
    composable(route = "Search") { Search(changePage) }
    composable(route = "Messages") { Messages(changePage) }
  }
}
