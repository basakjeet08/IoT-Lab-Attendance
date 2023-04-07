package `in`.iot.lab.iotlabattendance.feature_authentication.presentation.stateholder

import android.util.Log.d
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
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.util.LoginState

class LoginViewModel : ViewModel() {

    var userInputEmail: String by mutableStateOf("")
        private set

    var userInputPassword: String by mutableStateOf("")
        private set

    var showPassword: Boolean by mutableStateOf(false)
        private set

    var loginState: LoginState by mutableStateOf(LoginState.Initialized)

    // Firebase Authentication Variable
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

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

    fun sendFirebaseLoginRequest() {

        // Updating the login State to the Loading State
        loginState = LoginState.Loading

        // Checking if all the TextFields are filled or not
        if (userInputEmail.isEmpty() || userInputPassword.isEmpty()) {
            loginState = LoginState.Failure(errorMessage = "Enter All the Data")
            return
        }


        firebaseAuth.signInWithEmailAndPassword(userInputEmail, userInputPassword)
            .addOnSuccessListener {

                val fireStore = Firebase.firestore

                val roll = it.user?.email?.replace("@kiit.ac.in", "").toString()

                fireStore.collection("Users").document(roll).get()
                    .addOnSuccessListener {
                        val isAdmin = it.get("role")
                        d("Success Login", isAdmin.toString())

                        AuthenticatedUserData.setUserEmail(userInputEmail)
                        AuthenticatedUserData.setUserRole(isAdmin.toString())
                        AuthenticatedUserData.setUserRoll(roll)

                        loginState = LoginState.Success

                    }
                    .addOnFailureListener {
                        d("Failed Login", " failed")
                    }
            }
            .addOnFailureListener {

                d("Failure", it.message.toString())

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

}