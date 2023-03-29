package `in`.iot.lab.iotlabattendance.feature_bottom_navigation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
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
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
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
        labelOfIcon = R.string.post,
        unselectedIcon = Icons.Outlined.Email,
        selectedIcon = Icons.Filled.Email,
        onOptionClicked = { it.navigate(BottomNavRoutes.AttendanceRoute.route) }
    )

    // Work Update Post Option
    object WorkUpdatePostOption : BottomNavOptions(
        route = BottomNavRoutes.WorkUpdatePostRoute.route,
        labelOfIcon = R.string.attendance,
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        onOptionClicked = { it.navigate(BottomNavRoutes.WorkUpdatePostRoute.route) }
    )

    // Work Update History Option
    object WorkUpdateHistoryOption : BottomNavOptions(
        route = BottomNavRoutes.WorkUpdateHistoryRoute.route,
        labelOfIcon = R.string.updates,
        unselectedIcon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        onOptionClicked = { it.navigate(BottomNavRoutes.WorkUpdateHistoryRoute.route) }
    )
}
