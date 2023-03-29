package `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation

import `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation.BottomNavRoutes.*
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.AttendanceScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.WorkUpdatePostScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.WorkUpdateHistoryScreen

/**
 * This contains all the possible Routes from the Teacher List Module
 *
 * @property WorkUpdatePostRoute This is the Home Screen or the Landing Screen
 * which is [WorkUpdatePostScreen]
 * @property AttendanceRoute This is the route to the Attendance Screen which is
 * [AttendanceScreen]
 * @property [WorkUpdateHistoryRoute] is the route to the History Screen [WorkUpdateHistoryScreen]
 */
sealed class BottomNavRoutes(val route: String) {
    object AttendanceRoute : BottomNavRoutes("attendance-screen")
    object WorkUpdatePostRoute : BottomNavRoutes("home-screen")
    object WorkUpdateHistoryRoute : BottomNavRoutes("work-update-route")
}