package me.arkteek.worsetagram.ui.screen.authentication

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.constants.ROUTE_HOME
import me.arkteek.worsetagram.common.constants.ROUTE_LOGIN
import me.arkteek.worsetagram.common.constants.ROUTE_SIGNUP
import me.arkteek.worsetagram.domain.model.AuthResource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(viewModel: AuthViewModel?, navController: NavController) {

  var email by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }

  val authResource = viewModel?.loginFlow?.collectAsState()

  ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    val (refHeader, refEmail, refPassword, refButtonLogin, refTextSignup, refLoading) = createRefs()

    Box(
      modifier =
        Modifier.constrainAs(refHeader) {
            top.linkTo(parent.top, 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
          }
          .wrapContentSize()
    ) {
      AuthHeader()
    }

    TextField(
      value = email,
      onValueChange = { email = it },
      label = { Text(text = stringResource(id = R.string.email)) },
      modifier =
        Modifier.constrainAs(refEmail) {
          top.linkTo(refHeader.bottom, 20.dp)
          start.linkTo(parent.start, 30.dp)
          end.linkTo(parent.end, 20.dp)
          width = Dimension.fillToConstraints
        },
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
      label = { Text(text = stringResource(id = R.string.password)) },
      modifier =
        Modifier.constrainAs(refPassword) {
          top.linkTo(refEmail.bottom, 20.dp)
          start.linkTo(parent.start, 30.dp)
          end.linkTo(parent.end, 20.dp)
          width = Dimension.fillToConstraints
        },
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
      onClick = { viewModel?.loginUser(email, password) },
      modifier =
        Modifier.constrainAs(refButtonLogin) {
          top.linkTo(refPassword.bottom, 20.dp)
          start.linkTo(parent.start, 30.dp)
          end.linkTo(parent.end, 20.dp)
          width = Dimension.fillToConstraints
        },
    ) {
      Text(text = stringResource(id = R.string.login), style = MaterialTheme.typography.titleMedium)
    }

    Text(
      modifier =
        Modifier.constrainAs(refTextSignup) {
            top.linkTo(refButtonLogin.bottom, 20.dp)
            start.linkTo(parent.start, 30.dp)
            end.linkTo(parent.end, 20.dp)
          }
          .clickable {
            navController.navigate(ROUTE_SIGNUP) { popUpTo(ROUTE_LOGIN) { inclusive = true } }
          },
      text = stringResource(id = R.string.dont_have_account),
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
      color = MaterialTheme.colorScheme.onSurface,
    )

    authResource?.value?.let {
      when (it) {
        is AuthResource.Failure -> {
          //          ShowToast(message = it.exception.message.toString())
        }
        is AuthResource.Loading -> {
          CircularProgressIndicator(
            modifier =
              Modifier.constrainAs(refLoading) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
              }
          )
        }
        is AuthResource.Success -> {
          LaunchedEffect(Unit) {
            navController.navigate(ROUTE_HOME) { popUpTo(ROUTE_LOGIN) { inclusive = true } }
          }
        }
      }
    }
  }
}
