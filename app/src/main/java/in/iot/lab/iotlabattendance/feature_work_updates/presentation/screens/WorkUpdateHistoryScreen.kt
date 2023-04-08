package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.screens

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
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.components.WorkUpdateItemControl
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.components.WorkUpdateSearchBarUI
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.stateholder.WorkUpdateHistoryViewModel

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        WorkUpdateHistoryScreen()
    }
}

/**
 * This is the Work Update History Screen
 */
@Composable
fun WorkUpdateHistoryScreen(
    modifier: Modifier = Modifier
) {

    // ViewModel variable
    val myViewModel: WorkUpdateHistoryViewModel = viewModel()

    // This is the Main UI of the Function
    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // This Function Draws the Search Bar of the Screen
            WorkUpdateSearchBarUI(
                value = myViewModel.userRoll,
                buttonText = R.string.get,
                textLabel = R.string.roll_no,
                onValueChange = { myViewModel.updateUserRoll(it) }
            ) {
                myViewModel.getWorkUpdates()
            }

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This draws the Work Update Card Views
            WorkUpdateItemControl(myViewModel = myViewModel)
        }
    }
}