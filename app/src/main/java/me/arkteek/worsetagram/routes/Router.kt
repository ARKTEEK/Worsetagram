package me.arkteek.worsetagram.routes

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.arkteek.worsetagram.Search
import me.arkteek.worsetagram.ui.Home

@Composable
fun Router(
    controller: NavHostController,
    changePage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
  NavHost(
      enterTransition = {
        slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
      },
      exitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500)) },
      modifier = modifier,
      navController = controller,
      startDestination = "Home") {
        composable(route = "Home") { Home(changePage) }
        composable(route = "Search") { Search(changePage) }
      }
}
