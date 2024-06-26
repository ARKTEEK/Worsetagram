package me.arkteek.worsetagram.ui.screen.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R

@Composable
fun AuthHeader() {
  Column(
    modifier = Modifier.wrapContentSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    AsyncImage(
      model = "https://i.imgur.com/EPUv9qd.png",
      modifier = Modifier.size(128.dp, 128.dp),
      contentDescription = stringResource(id = R.string.app_name),
    )

    Text(
      modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 50.dp),
      text = stringResource(id = R.string.app_name),
      style = MaterialTheme.typography.headlineMedium,
      textAlign = TextAlign.Center,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}
