package `in`.iot.lab.iotlabattendance.feature_authentication.presentation.navigation

sealed class AuthenticationRoutes(val route: String) {

    object LoginRoute : AuthenticationRoutes("login-route")
    object RegisterRoute : AuthenticationRoutes("register-route")
    object ForgotPasswordRoute : AuthenticationRoutes("forgot-password-route")
}