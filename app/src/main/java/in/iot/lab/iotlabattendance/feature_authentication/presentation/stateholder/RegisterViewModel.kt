package `in`.iot.lab.iotlabattendance.feature_authentication.presentation.stateholder

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

    // Fire Store Instance Variable
    private val fireStoreDB = Firebase.firestore

    // Email Inputted by the User
    var userInputEmail: String by mutableStateOf("")
        private set

    // Password Inputted by the User
    var userInputPassword: String by mutableStateOf("")
        private set

    // Confirm Password Inputted by the User
    var userInputRePassword: String by mutableStateOf("")
        private set

    // Variable to Toggle whether to show password to the user
    var showEnterPassword: Boolean by mutableStateOf(false)
        private set

    // Variable to Toggle whether to show confirm password to the user
    var showReEnterPassword: Boolean by mutableStateOf(false)
        private set

    // Variable which checks if the User asked for Admin Role
    var askedForAdminRole: Boolean by mutableStateOf(false)
        private set

    // Keeps the Registration Related API calls State
    var registrationState: RegistrationState by mutableStateOf(RegistrationState.Initialized)
        private set

    fun updateAskedForAdminRole(newValue: Boolean) {
        askedForAdminRole = newValue
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

    // This function registers a new user in Firebase
    fun postSignInRequestFirebase() {

        // Changing the State to Loading Before Going for API Calls
        registrationState = RegistrationState.Loading

        // Checking if the email is a KIIT Email
        if (!userInputEmail.contains("@kiit.ac.in")) {
            registrationState = RegistrationState.Failure("Have to use a KIIT Email ID")
            return
        }

        // Checking if the fields are empty or not
        if (userInputEmail.isEmpty() || userInputPassword.isEmpty() || userInputRePassword.isEmpty()) {
            registrationState = RegistrationState.Failure(errorMessage = "Enter All the Data")
            return
        }

        // Checking if the password and confirm password are same
        if (userInputPassword != userInputRePassword) {
            registrationState = RegistrationState.Failure(errorMessage = "Passwords doesn't Match")
            return
        }

        // Calling the Firebase to register the User
        firebaseAuth.createUserWithEmailAndPassword(userInputEmail, userInputPassword)
            // This block executes if the Request is Success
            .addOnSuccessListener {
                // Extracting Roll number from the email
                val email = it.user?.email

                // Requested Role of the User
                val role = if (askedForAdminRole)
                    "requested"
                else
                    "user"

                // User Hashmap to be saved in Fire Store
                val user = hashMapOf<String, Any?>(
                    "Email" to email,
                    "role" to role
                )

                // Storing the User Data in Fire Store
                fireStoreDB.collection("Users").document(email!!)
                    .set(user)
                    // This block executes if the Request is Success
                    .addOnSuccessListener {
                        registrationState = RegistrationState.Success
                    }
                    // This block executes if the Request is UnSuccessful
                    .addOnFailureListener {
                        registrationState = RegistrationState.Failure("User Data wasn't Sent")
                    }
            }
            // This block executes if the Request is UnSuccessful
            .addOnFailureListener {

                // Checking which exception it is and warning the User Accordingly
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