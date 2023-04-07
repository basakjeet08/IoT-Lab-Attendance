package `in`.iot.lab.iotlabattendance.core.util

// This Object contains the Data of the User
object AuthenticatedUserData {

    // The Email of the Current Logged In User
    var userEmail: String? = null
        private set

    // The Role of the Current Logged in User
    var userRole: UserRole? = null
        private set

    // The Roll Number of the Current Logged In User
    var userRoll: String? = null
        private set

    // Setting the Email of the Logged In User
    fun setUserEmail(newEmail: String) {
        userEmail = newEmail
    }

    // Setting the Role of the Logged In User
    fun setUserRole(role: String) {
        userRole = if (role.lowercase() == "admin")
            UserRole.Admin
        else
            UserRole.Member
    }

    // Setting the Roll of the Logged In User
    fun setUserRoll(newRoll: String) {
        userRoll = newRoll
    }
}