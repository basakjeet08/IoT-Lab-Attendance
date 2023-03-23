package `in`.iot.lab.iotlabattendance.feature_home.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.AttendanceScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.HomeScreen
import `in`.iot.lab.iotlabattendance.feature_home.presentation.screen.WorkUpdateScreen

/**
 * Navigation Graph : It contains all the Different Routes in the Home Feature
 */
@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.HomeRoute.route,
        builder = {

            composable(
                NavigationRoutes.HomeRoute.route,
                content = {
                    HomeScreen(navController = navController)
                }
            )

            composable(
                NavigationRoutes.AttendanceRoute.route,
                content = {
                    AttendanceScreen()
                }
            )

            composable(
                NavigationRoutes.WorkUpdateRoute.route,
                content = {
                    WorkUpdateScreen()
                }
            )

        })
}