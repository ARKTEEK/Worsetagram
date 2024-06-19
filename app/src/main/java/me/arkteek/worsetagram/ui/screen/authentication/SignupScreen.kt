package me.arkteek.worsetagram.ui.screen.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.arkteek.worsetagram.common.constants.ROUTE_HOME
import me.arkteek.worsetagram.common.constants.ROUTE_LOGIN
import me.arkteek.worsetagram.common.constants.ROUTE_SIGNUP
import me.arkteek.worsetagram.data.source.remote.AuthResource
import me.arkteek.worsetagram.ui.screen.LoadingScreen
import me.arkteek.worsetagram.ui.viewmodel.AuthViewModel

@Composable
fun SignupScreen(viewModel: AuthViewModel?, navController: NavHostController) {
  var firstName by remember { mutableStateOf("") }
  var lastName by remember { mutableStateOf("") }
  var nickname by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }

  val authResource = viewModel?.signupFlow?.collectAsState()

  Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
    AuthHeader()

    TextField(
      value = firstName,
      onValueChange = { firstName = it },
      label = { Text(text = "Firstname") },
      modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
      keyboardOptions =
        KeyboardOptions(
          capitalization = KeyboardCapitalization.None,
          autoCorrect = false,
          keyboardType = KeyboardType.Email,
          imeAction = ImeAction.Next,
        ),
    )

    TextField(
      value = lastName,
      onValueChange = { lastName = it },
      label = { Text(text = "Lastname") },
      modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
      keyboardOptions =
        KeyboardOptions(
          capitalization = KeyboardCapitalization.None,
          autoCorrect = false,
          keyboardType = KeyboardType.Email,
          imeAction = ImeAction.Next,
        ),
    )

    TextField(
      value = nickname,
      onValueChange = { nickname = it },
      label = { Text(text = "Nickname") },
      modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
      keyboardOptions =
        KeyboardOptions(
          capitalization = KeyboardCapitalization.None,
          autoCorrect = false,
          keyboardType = KeyboardType.Email,
          imeAction = ImeAction.Next,
        ),
    )

    TextField(
      value = email,
      onValueChange = { email = it },
      label = { Text(text = "Email") },
      modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
      keyboardOptions =
        KeyboardOptions(
          capitalization = KeyboardCapitalization.None,
          autoCorrect = false,
          keyboardType = KeyboardType.Email,
          imeAction = ImeAction.Next,
        ),
    )

    TextField(
      value = password,
      onValueChange = { password = it },
      label = { Text(text = "Password") },
      modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions =
        KeyboardOptions(
          capitalization = KeyboardCapitalization.None,
          autoCorrect = false,
          keyboardType = KeyboardType.Password,
          imeAction = ImeAction.Done,
        ),
    )

    Button(
      onClick = { viewModel?.singupUser(firstName, lastName, nickname, email, password) },
      modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
    ) {
      Text(text = "Create Account", style = MaterialTheme.typography.titleMedium)
    }

    Text(
      text = "Already have an account?\nClick here to Login",
      modifier =
        Modifier.padding(vertical = 4.dp)
          .fillMaxWidth()
          .clickable {
            navController.navigate(ROUTE_LOGIN) { popUpTo(ROUTE_SIGNUP) { inclusive = true } }
          }
          .padding(vertical = 8.dp)
          .fillMaxWidth(),
      textAlign = TextAlign.Center,
    )

    authResource?.value?.let {
      when (it) {
        is AuthResource.Failure -> {
          // TODO ShowToast(message = it.exception.message.toString())
        }
        is AuthResource.Loading -> {
          LoadingScreen()
        }
        is AuthResource.Success -> {
          LaunchedEffect(Unit) {
            navController.navigate(ROUTE_HOME) { popUpTo(ROUTE_SIGNUP) { inclusive = true } }
          }
        }
      }
    }
  }
}
