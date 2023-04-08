package `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavController
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation.BottomNavOptions.*
import `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation.BottomNavOptions.Companion.menuItems

/**
 * This class will contain all the Various Screens related to the bottom Navigation bar
 *
 * @param route It keeps the Route to the Option
 * @param labelOfIcon It keeps the Label of the String
 * @param unselectedIcon It is the ImageVector which we have to show when the option is not selected
 * @param selectedIcon It is the ImageVector which we have to show when the option is selected
 * @param onOptionClicked It is a function which executes when we click this button to navigate
 *
 * @property menuItems This is a list of all the menu Items
 * @property WorkUpdatePostOption This is the option which contains the data of the Home Menu Item
 * @property AttendanceOption This is the option which contains the data of the History Menu Item
 * @property WorkUpdateHistoryOption This is the option which contains the data of the Profile Menu Item
 */
sealed class BottomNavOptions(
    val route: String,
    @StringRes val labelOfIcon: Int,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val onOptionClicked: (NavController) -> Unit
) {

    // This is made companion to let everyone in the App to get to User
    companion object {
        val menuItems = listOf(
            AttendanceOption,
            WorkUpdatePostOption,
            WorkUpdateHistoryOption
        )
    }

    // Attendance Option
    object AttendanceOption : BottomNavOptions(
        route = BottomNavRoutes.AttendanceRoute.route,
        labelOfIcon = R.string.attendance,
        unselectedIcon = R.drawable.ballot,
        selectedIcon = R.drawable.ballot_filled,
        onOptionClicked = {
            it.navigate(BottomNavRoutes.AttendanceRoute.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )

    // Work Update Post Option
    object WorkUpdatePostOption : BottomNavOptions(
        route = BottomNavRoutes.WorkUpdatePostRoute.route,
        labelOfIcon = R.string.post,
        unselectedIcon = R.drawable.add_box,
        selectedIcon = R.drawable.add_box_filled,
        onOptionClicked = {
            it.navigate(BottomNavRoutes.WorkUpdatePostRoute.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )

    // Work Update History Option
    object WorkUpdateHistoryOption : BottomNavOptions(
        route = BottomNavRoutes.WorkUpdateHistoryRoute.route,
        labelOfIcon = R.string.updates,
        unselectedIcon = R.drawable.analytics,
        selectedIcon = R.drawable.analytics_filled,
        onOptionClicked = {
            it.navigate(BottomNavRoutes.WorkUpdateHistoryRoute.route) {
                popUpTo(it.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )
}
