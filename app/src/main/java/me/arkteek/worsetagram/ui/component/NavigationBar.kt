package me.arkteek.worsetagram.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.constants.ROUTE_CHAT_LIST
import me.arkteek.worsetagram.common.constants.ROUTE_HOME
import me.arkteek.worsetagram.common.constants.ROUTE_NEW_POST
import me.arkteek.worsetagram.common.constants.ROUTE_PROFILE
import me.arkteek.worsetagram.common.constants.ROUTE_SEARCH

@Composable
fun BottomNavigationBar(navController: NavHostController) {
  BottomAppBar(
    containerColor = Color.White,
    tonalElevation = 8.dp,
    modifier = Modifier.height(56.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      IconButton(onClick = { navController.navigate(ROUTE_HOME) }) {
        Icon(
          painter = painterResource(R.drawable.ic_home),
          contentDescription = "Home Icon",
          modifier = Modifier.size(24.dp),
        )
      }
      IconButton(onClick = { navController.navigate(ROUTE_SEARCH) }) {
        Icon(
          painter = painterResource(R.drawable.ic_search),
          contentDescription = "Search Icon",
          modifier = Modifier.size(24.dp),
        )
      }
      IconButton(onClick = { navController.navigate(ROUTE_NEW_POST) }) {
        Icon(
          painter = painterResource(R.drawable.ic_new_post),
          contentDescription = "Add Icon",
          modifier = Modifier.size(24.dp),
        )
      }
      IconButton(onClick = { navController.navigate(ROUTE_CHAT_LIST) }) {
        Icon(
          painter = painterResource(R.drawable.ic_messages),
          contentDescription = "Messages Icon",
          modifier = Modifier.size(24.dp),
        )
      }
      IconButton(onClick = { navController.navigate(ROUTE_PROFILE) }) {
        Icon(
          painter = painterResource(R.drawable.ic_profile),
          contentDescription = "Profile Icon",
          modifier = Modifier.size(24.dp),
        )
      }
    }
  }
}
