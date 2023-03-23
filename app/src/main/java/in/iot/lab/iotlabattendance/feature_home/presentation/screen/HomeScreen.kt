package `in`.iot.lab.iotlabattendance.feature_home.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.components.GradientButton
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.core.theme.buttonShape
import `in`.iot.lab.iotlabattendance.feature_home.presentation.navigation.NavigationRoutes

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        HomeScreen(navController = rememberNavController())
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_landing_page),
            contentDescription = null,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        )

        GradientButton(
            buttonShape = buttonShape,
            buttonText = R.string.work_updates
        ) {
            navController.navigate(NavigationRoutes.WorkUpdateRoute.route)
        }

        Spacer(modifier = Modifier.height(32.dp))

        GradientButton(
            buttonShape = buttonShape,
            buttonText = R.string.attendance
        ) {
            navController.navigate(NavigationRoutes.AttendanceRoute.route)
        }
    }
}