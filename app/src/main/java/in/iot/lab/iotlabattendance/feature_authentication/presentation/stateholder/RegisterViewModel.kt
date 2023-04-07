package `in`.iot.lab.iotlabattendance.feature_authentication.presentation.stateholder

import android.util.Log.d
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.util.RegistrationState

class RegisterViewModel : ViewModel() {

    // Firebase Authentication Variable
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var userInputEmail: String by mutableStateOf("")
        private set

    var userInputPassword: String by mutableStateOf("")
        private set

    var userInputRePassword: String by mutableStateOf("")
        private set

    // Variable which records whether to show the Password Entered by the User
    var showEnterPassword: Boolean by mutableStateOf(false)
        private set

    // Variable which records whether to show the Password Entered by the User
    var showReEnterPassword: Boolean by mutableStateOf(false)
        private set

    var checked: Boolean by mutableStateOf(false)
        private set

    var registrationState: RegistrationState by mutableStateOf(RegistrationState.Initialized)
        private set

    fun updateChecked(newValue: Boolean) {
        checked = newValue
    }


    fun updateUserInputEmail(newEmail: String) {
        userInputEmail = newEmail
    }

    fun updateUserInputPassword(newPassword: String) {
        userInputPassword = newPassword
    }

    fun updateUserInputRePassword(newPassword: String) {
        userInputRePassword = newPassword
    }

    fun clearUserInputEmail() {
        userInputEmail = ""
    }

    // This function returns the VisualTransformation of the Enter Password
    fun enterPasswordShowState(): VisualTransformation {
        if (showEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    // This function returns the VisualTransformation of the Enter Password
    fun enterRePasswordShowState(): VisualTransformation {
        if (showReEnterPassword)
            return VisualTransformation.None
        return PasswordVisualTransformation()
    }

    fun changeEnterPasswordStatus() {
        showEnterPassword = !showEnterPassword
    }

    fun changeReEnterPasswordStatus() {
        showReEnterPassword = !showReEnterPassword
    }


    fun resetToDefaults() {
        userInputEmail = ""
        userInputPassword = ""
        userInputRePassword = ""
        showEnterPassword = false
        showReEnterPassword = false
        registrationState = RegistrationState.Initialized
    }

    fun postSignInRequestFirebase() {

        registrationState = RegistrationState.Loading

        if (!userInputEmail.contains("@kiit.ac.in")) {
            registrationState = RegistrationState.Failure("Have to use a KIIT Email ID")
            return
        }

        if (userInputEmail.isEmpty() || userInputPassword.isEmpty() || userInputRePassword.isEmpty()) {
            registrationState = RegistrationState.Failure(errorMessage = "Enter All the Data")
            return
        }

        if (userInputPassword != userInputRePassword) {
            registrationState = RegistrationState.Failure(errorMessage = "Passwords doesn't Match")
            return
        }


        firebaseAuth.createUserWithEmailAndPassword(userInputEmail, userInputPassword)
            .addOnSuccessListener {


                val roll = it.user?.email?.replace("@kiit.ac.in", "").toString()

                d("Roll ", roll)

                val fireStoreDB = Firebase.firestore

                val user = hashMapOf<String, Any>(
                    "Roll Number" to roll.toInt(),
                    "role" to "user"
                )

                fireStoreDB.collection("Users").document(roll)
                    .set(user)
                    .addOnSuccessListener {
                        d("Adding User Data", "Success")
                    }
                    .addOnFailureListener {
                        d("Adding User Data", "Failed")
                    }

                registrationState = RegistrationState.Success
                d("Registration State", "State")
            }
            .addOnFailureListener {
                registrationState = when (it) {
                    is FirebaseAuthUserCollisionException -> RegistrationState.Failure(
                        "User Already Present"
                    )
                    is FirebaseAuthWeakPasswordException -> RegistrationState.Failure(
                        "Password Need at least 6 characters"
                    )
                    else -> RegistrationState.Failure(
                        "Network Not Available"
                    )
                }
            }
    }
}