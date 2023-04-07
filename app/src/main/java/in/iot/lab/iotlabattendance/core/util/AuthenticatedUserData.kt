package `in`.iot.lab.iotlabattendance.core.util

object AuthenticatedUserData {
    var userEmail: String? = null
        private set
    var userRole: UserRole? = null
        private set
    var userRoll: String? = null
        private set
    fun setUserEmail(newEmail: String) {
        userEmail = newEmail
    }

    fun setUserRole(role: String) {
        userRole = if (role.lowercase() == "member")
            UserRole.Member
        else
            UserRole.Admin
    }

    fun setUserRoll(newRoll: String) {
        userRoll = newRoll
    }

}

enum class UserRole {
    Admin,
    Member
}