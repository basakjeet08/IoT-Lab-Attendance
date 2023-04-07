package `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation

import `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation.BottomNavRoutes.*
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.AttendanceScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.WorkUpdateHistoryScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.WorkUpdatePostScreen

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
    object AttendanceRoute : BottomNavRoutes("attendance-route")
    object WorkUpdatePostRoute : BottomNavRoutes("work-update-post-route")
    object WorkUpdateHistoryRoute : BottomNavRoutes("work-update-route")
}