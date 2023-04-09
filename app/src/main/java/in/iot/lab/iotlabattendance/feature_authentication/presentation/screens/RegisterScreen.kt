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
import androidx.compose.runtime.*
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
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.core.theme.buttonShape
import `in`.iot.lab.iotlabattendance.core.theme.custom_icons.Visibility
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.components.*
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.navigation.AuthenticationRoutes
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.stateholder.RegisterViewModel
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.util.RegistrationState

// This is the Preview function of the Login Screen
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        RegisterScreen(navController = rememberNavController())
    }
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    // Focus Manager for Input Text Fields
    val focusManager = LocalFocusManager.current
    // ViewModel Class Variable for Storing Data and User UI events
    val myViewModel: RegisterViewModel = viewModel()
    // Context of the Activity
    val context = LocalContext.current
    // Boolean which stores if there is already a Login Request being processed at the time
    var registrationRequestEmpty = true

    // Checking what to do according to the different States of UI
    when (myViewModel.registrationState) {
        is RegistrationState.Success -> {

            // Checking if the user opted for Admin Access
            if (myViewModel.askedForAdminRole) {

                // Showing Alert Dialog Box only to the Admins to prompt them that they opted for access
                AlertDialogUI(
                    title = R.string.access_level_requested,
                    labelText = R.string.your_request_for_admin_access_level
                ) {

                    // Changing the asked for Admin Role variable to false to show the toast and
                    // Changing to Login Screen
                    myViewModel.updateAskedForAdminRole(newValue = false)
                }
            }

            // Checking if the User haven't opted for admin access and can be shown toast and redirected
            if (!myViewModel.askedForAdminRole) {

                // Showing the toast
                Toast.makeText(context, "Registered Successful", Toast.LENGTH_SHORT).show()

                // Resetting the values inside the ViewModel
                myViewModel.resetToDefaults()
                navController.navigate(AuthenticationRoutes.LoginRoute.route)
            }
        }
        is RegistrationState.Loading -> {
            registrationRequestEmpty = false
        }
        is RegistrationState.Failure -> {
            Toast.makeText(
                context,
                (myViewModel.registrationState as RegistrationState.Failure).errorMessage,
                Toast.LENGTH_SHORT
            ).show()
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

                // User Input Email Input Text Box UI
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

                    // It is the Clear Icon to be Showed as a Trailing Icon
                    trailingIcon = {
                        if (myViewModel.userInputEmail != "") {
                            IconButton(onClick = { myViewModel.clearUserInputEmail() }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    }
                ) {
                    myViewModel.updateUserInputEmail(it)
                }

                // Spacing of 16dp
                Spacer(modifier = Modifier.height(16.dp))

                // User Input Password Input Text Box UI
                UserInputUI(
                    userInput = myViewModel.userInputPassword,
                    inputFieldLabel = R.string.password,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),

                    // If we show the User Input to the User or not
                    visualTransformation = myViewModel.enterPasswordShowState(),

                    // It is the Eye Icon (show Password) to be Showed as a Trailing Icon
                    trailingIcon = {
                        IconButton(onClick = { myViewModel.changeEnterPasswordStatus() }) {
                            val visibilityIcon =
                                if (myViewModel.showEnterPassword) Visibility.VisibilityOn else Visibility.VisibilityOff
                            val description =
                                if (myViewModel.showEnterPassword) "Show password" else "Hide password"
                            Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    }
                ) {
                    myViewModel.updateUserInputPassword(it)
                }

                // Spacing of 16dp
                Spacer(modifier = Modifier.height(16.dp))

                // User Input confirm Password Input Text Box UI
                UserInputUI(
                    userInput = myViewModel.userInputRePassword,
                    inputFieldLabel = R.string.confirm_password,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),

                    // If we show the User Input to the User or not
                    visualTransformation = myViewModel.enterRePasswordShowState(),

                    // It is the Eye Icon (show Password) to be Showed as a Trailing Icon
                    trailingIcon = {
                        IconButton(onClick = { myViewModel.changeReEnterPasswordStatus() }) {
                            val visibilityIcon =
                                if (myViewModel.showReEnterPassword) Visibility.VisibilityOn else Visibility.VisibilityOff
                            val description =
                                if (myViewModel.showReEnterPassword) "Show password" else "Hide password"
                            Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    }
                ) {
                    myViewModel.updateUserInputRePassword(it)
                }

                // Spacing of 16dp
                Spacer(modifier = Modifier.height(16.dp))

                // Admin Request CheckBox
                CheckBoxUI(
                    checked = myViewModel.askedForAdminRole,
                    textToShow = R.string.are_you_an_admin
                ) {
                    myViewModel.updateAskedForAdminRole(it)
                }

                // Spacing of 24dp
                Spacer(modifier = Modifier.height(16.dp))

                // Register Gradient Button
                GradientButton(buttonShape = buttonShape, buttonText = R.string.register) {

                    if (registrationRequestEmpty)
                        myViewModel.postSignInRequestFirebase()
                    else
                        Toast.makeText(context, "Wait", Toast.LENGTH_SHORT).show()
                }

                // Spacing of 24dp
                Spacer(modifier = Modifier.height(24.dp))

                // Login Text Button UI
                TextButtonUI(textToShow = R.string.already_have_an_account) {

                    // This Executes when we press the TextButton
                    navController.navigate(AuthenticationRoutes.LoginRoute.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }

                // Spacing of 24dp
                Spacer(modifier = Modifier.height(24.dp))

            }
            // Progress Indicator
            if (!registrationRequestEmpty) {
                Box(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}