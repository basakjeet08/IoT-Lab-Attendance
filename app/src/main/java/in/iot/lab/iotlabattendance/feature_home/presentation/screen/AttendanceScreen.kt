package `in`.iot.lab.iotlabattendance.feature_home.presentation.screen

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        AttendanceScreen()
    }
}

@Composable
fun AttendanceScreen(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Attendance Screen"
    )
}