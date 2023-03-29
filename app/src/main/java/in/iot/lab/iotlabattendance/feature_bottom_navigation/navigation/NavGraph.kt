package `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.AttendanceScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.WorkUpdateHistoryScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.WorkUpdatePostScreen

/**
 * Navigation Graph : It contains all the Different Routes in the Home Feature
 */
@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = BottomNavRoutes.AttendanceRoute.route,
        builder = {

            //  Attendance Screen Option
            composable(
                BottomNavRoutes.AttendanceRoute.route,
                content = { AttendanceScreen() }
            )

            // Work Update Post Screen
            composable(
                BottomNavRoutes.WorkUpdatePostRoute.route,
                content = { WorkUpdatePostScreen() }
            )

            // Work Update History Screen
            composable(
                BottomNavRoutes.WorkUpdateHistoryRoute.route,
                content = { WorkUpdateHistoryScreen() }
            )
        }
    )
}