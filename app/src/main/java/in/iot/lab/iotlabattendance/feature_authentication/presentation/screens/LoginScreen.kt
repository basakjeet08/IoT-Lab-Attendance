package `in`.iot.lab.iotlabattendance.feature_authentication.presentation.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.stateholder.LoginViewModel
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.util.LoginState
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.core.theme.buttonShape
import `in`.iot.lab.iotlabattendance.core.theme.custom_icons.Visibility
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.components.GradientButton
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.components.TextButtonUI
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.components.UserInputUI
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.navigation.AuthenticationRoutes


// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    // Focus Manager for Input Text Fields
    val focusManager = LocalFocusManager.current
    // ViewModel variable
    val myViewModel: LoginViewModel = viewModel()
    // Context of the Activity
    val context = LocalContext.current
    // Boolean which stores if there is already a Login Request being processed at the time
    var loginRequestEmpty = true

    // Checking what to do according to the different States of UI
    when (myViewModel.loginState) {
        is LoginState.Success -> {

            // Resetting all the Values
            myViewModel.resetToDefault()
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
        }
        is LoginState.Loading -> {
            loginRequestEmpty = false
        }
        is LoginState.Failure -> {
            Toast.makeText(
                context,
                (myViewModel.loginState as LoginState.Failure).errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            myViewModel.resetLoginState()
        }
        else -> {}
    }

    Surface(
        modifier = modifier
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // User Input Email Field
                UserInputUI(
                    userInput = myViewModel.userInputEmail,
                    inputFieldLabel = R.string.email,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        if (myViewModel.userInputEmail != "") {
                            IconButton(onClick = { myViewModel.clearUserInputEmail() }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    }
                ) {
                    myViewModel.changeUserInputEmail(it)
                }

                // Spacing of 16dp
                Spacer(modifier = Modifier.height(16.dp))

                // User Input Password Field
                UserInputUI(
                    userInput = myViewModel.userInputPassword,
                    inputFieldLabel = R.string.password,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = myViewModel.passwordShowState(),

                    // It is the Eye Icon (show Password) to be Showed as a Trailing Icon
                    trailingIcon = {
                        IconButton(onClick = { myViewModel.changePasswordHideStatus() }) {
                            val visibilityIcon =
                                if (myViewModel.showPassword) Visibility.VisibilityOn else Visibility.VisibilityOff
                            val description =
                                if (myViewModel.showPassword) "Show password" else "Hide password"
                            Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    }
                ) {
                    myViewModel.changeUserInputPassword(it)
                }

                // Spacing of 16dp
                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                GradientButton(buttonShape = buttonShape, buttonText = R.string.login) {
                    if (loginRequestEmpty)
                        myViewModel.sendFirebaseLoginRequest()
                    else
                        Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
                }

                // Spacing of 16dp
                Spacer(modifier = Modifier.height(16.dp))

                // Forgot Password Text Button
                TextButtonUI(textToShow = R.string.forgot_password) {

                    // This Executes when we press the TextButton
                    navController.navigate(AuthenticationRoutes.ForgotPasswordRoute.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }

                // Create an Account Text Button
                TextButtonUI(textToShow = R.string.create_an_account) {
                    // This Executes when we press the TextButton
                    navController.navigate(AuthenticationRoutes.RegisterRoute.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }

            // Progress Indicator
            if (!loginRequestEmpty) {
                Box(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }

        }
    }
}