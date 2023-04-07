package `in`.iot.lab.iotlabattendance.feature_authentication.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import `in`.iot.lab.iotlabattendance.core.util.AuthenticatedUserData
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.screens.LoginScreen
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.util.LoginState

/**
 * This function is the ViewModel of the [LoginScreen] and holds the UI State of the Screen
 */
class LoginViewModel : ViewModel() {

    // Contains the Instance of teh FireStore
    private val fireStore = Firebase.firestore

    // Firebase Authentication Variable
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Contains the email Inputted by the User
    var userInputEmail: String by mutableStateOf("")
        private set

    // Contains the Password Inputted by the User
    var userInputPassword: String by mutableStateOf("")
        private set

    // Boolean to toggle Visibility of the Password
    var showPassword: Boolean by mutableStateOf(false)
        private set

    // Login Screen UI API Calls state Holder
    var loginState: LoginState by mutableStateOf(LoginState.Initialized)

    init {

        // Checking if the user is already Logged In
        if (firebaseAuth.currentUser != null) {
            loginState = LoginState.Loading

            // Updating the UserData of the User
            initialFetchUserData()
        }
    }

    fun changeUserInputEmail(newEmail: String) {
        userInputEmail = newEmail
    }

    fun changeUserInputPassword(newPassword: String) {
        userInputPassword = newPassword
    }

    fun clearUserInputEmail() {
        userInputEmail = ""
    }

    fun passwordShowState(): VisualTransformation {
        if (showPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    fun changePasswordHideStatus() {
        showPassword = !showPassword
    }

    fun resetLoginState() {
        loginState = LoginState.Initialized
    }

    fun resetToDefault() {
        userInputEmail = ""
        userInputPassword = ""
        showPassword = false
    }

    // This Function does the Firebase calling and logs in the User
    fun sendFirebaseLoginRequest() {

        // Updating the login State to the Loading State
        loginState = LoginState.Loading

        // Checking if all the TextFields are filled or not
        if (userInputEmail.isEmpty() || userInputPassword.isEmpty()) {
            loginState = LoginState.Failure(errorMessage = "Enter All the Data")
            return
        }

        // Calling the Firebase to log in the User according to the value they inputted
        firebaseAuth.signInWithEmailAndPassword(userInputEmail, userInputPassword)

            // This Block executes if the Above call is Successful
            .addOnSuccessListener {

                // Email Of the User
                val email = it.user?.email

                // Roll Number of the User
                val roll = email?.replace("@kiit.ac.in", "").toString()

                // Checking the Fire Store for the Access Role of the User
                fireStore.collection("Users").document(email!!).get()

                    // This Block is executed if the Above call is Successful
                    .addOnSuccessListener { documentSnap ->

                        // Role of the User
                        val userRoleIdentifier = documentSnap.get("role")

                        // Setting the UserData
                        AuthenticatedUserData.setUserEmail(email)
                        AuthenticatedUserData.setUserRole(userRoleIdentifier.toString())
                        AuthenticatedUserData.setUserRoll(roll)

                        // Changing the Login State of the User
                        loginState = LoginState.Success

                    }
                    // This block is executed if the Above Request is UnSuccessful email = it.user?.email
                    .addOnFailureListener {
                        loginState = LoginState.Failure("Failed to Fetch User Role")
                    }
            }
            // This block is executed if the Above Request is UnSuccessful
            .addOnFailureListener {
                loginState = when (it) {
                    is FirebaseAuthInvalidCredentialsException -> LoginState.Failure(
                        "Invalid Credentials"
                    )
                    is FirebaseAuthInvalidUserException -> LoginState.Failure(
                        "User Not Present"
                    )
                    else -> LoginState.Failure(
                        "Network Not Available"
                    )
                }
            }
    }

    private fun initialFetchUserData() {

        val email = firebaseAuth.currentUser?.email
        val roll = email?.replace("@kiit.ac.in", "").toString()

        fireStore.collection("Users").document(email!!).get()
            // This block is executed if the Above Request is Successful
            .addOnSuccessListener {

                // Role of the User
                val userRoleIdentifier = it.get("role")

                // Setting the UserData
                AuthenticatedUserData.setUserEmail(email)
                AuthenticatedUserData.setUserRole(userRoleIdentifier.toString())
                AuthenticatedUserData.setUserRoll(roll)

                // Changing the Login State of the User
                loginState = LoginState.Success

            }
            // This block is executed if the Above Request is UnSuccessful
            .addOnFailureListener {
                loginState = LoginState.Failure("Failed to Fetch User Role")
            }
    }
}