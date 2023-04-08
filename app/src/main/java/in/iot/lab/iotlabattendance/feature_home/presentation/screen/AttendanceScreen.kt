package `in`.iot.lab.iotlabattendance.feature_home.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.feature_home.presentation.components.AttendanceEntryCardUIControl
import `in`.iot.lab.iotlabattendance.feature_home.presentation.components.SearchBarUI
import `in`.iot.lab.iotlabattendance.feature_home.presentation.stateholder.AttendanceViewModel

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

    val myViewModel: AttendanceViewModel = viewModel()

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBarUI(
                value = myViewModel.userRoll,
                buttonText = R.string.go,
                textLabel = R.string.roll_no,
                onValueChange = { myViewModel.updateUserRoll(it) }
            ) {
                myViewModel.getPostByRoll()
            }

            AttendanceEntryCardUIControl(myViewModel = myViewModel)
        }
    }
}