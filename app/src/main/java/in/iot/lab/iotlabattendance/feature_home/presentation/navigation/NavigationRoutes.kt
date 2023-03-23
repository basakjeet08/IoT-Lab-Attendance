package `in`.iot.lab.iotlabattendance.feature_home.presentation.navigation

import `in`.iot.lab.iotlabattendance.feature_home.presentation.navigation.NavigationRoutes.AttendanceRoute
import `in`.iot.lab.iotlabattendance.feature_home.presentation.navigation.NavigationRoutes.HomeRoute
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.AttendanceScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.HomeScreen

/**
 * This contains all the possible Routes from the Teacher List Module
 *
 * @property HomeRoute This is the Home Screen or the Landing Screen
 * which is [HomeScreen]
 * @property AttendanceRoute This is the route to the Attendance Screen which is
 * [AttendanceScreen]
 */
sealed class NavigationRoutes(val route: String) {
    object HomeRoute : NavigationRoutes("home-screen")
    object AttendanceRoute : NavigationRoutes("attendance-screen")
    object WorkUpdateRoute : NavigationRoutes("work-update-route")

}