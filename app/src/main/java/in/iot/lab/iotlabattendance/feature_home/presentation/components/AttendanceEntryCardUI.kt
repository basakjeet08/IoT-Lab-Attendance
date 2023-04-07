package `in`.iot.lab.iotlabattendance.feature_home.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.feature_authentication.presentation.components.TextButtonUI
import `in`.iot.lab.iotlabattendance.feature_home.data.model.AttendanceData
import `in`.iot.lab.iotlabattendance.feature_home.presentation.stateholder.AttendanceViewModel
import `in`.iot.lab.iotlabattendance.feature_home.util.GetAttendanceUiState

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        AttendanceEntryCardSuccess(
            attendanceData = AttendanceData(
                id = "",
                in_time = "",
                out_time = "",
                roll = ""
            )
        )
    }
}


@Composable
fun AttendanceEntryCardUIControl(
    modifier: Modifier = Modifier,
    myViewModel: AttendanceViewModel
) {
    when (myViewModel.getAttendanceUiState) {
        is GetAttendanceUiState.Initialized -> {
            myViewModel.getPostByRoll()
        }
        is GetAttendanceUiState.Loading -> {
            AttendanceEntryCardLoading()
        }

        is GetAttendanceUiState.Success -> {

            val attendanceData = (myViewModel.getAttendanceUiState as GetAttendanceUiState.Success)
                .attendanceResponseData.attendanceData

            if (attendanceData.isNullOrEmpty()) {
                AttendanceEntryCardFailure(
                    textToShow = R.string.database_is_empty_found_no_records
                ) {
                    myViewModel.getPostByRoll()
                }
            } else {
                LazyColumn {
                    items(attendanceData.size) {
                        AttendanceEntryCardSuccess(attendanceData = attendanceData[it])
                    }
                }
            }
        }
        else -> {
            AttendanceEntryCardFailure {
                myViewModel.getPostByRoll()
            }
        }
    }
}

@Composable
fun AttendanceEntryCardLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun AttendanceEntryCardSuccess(
    attendanceData: AttendanceData
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        // This Elevates the Card above the Background
        elevation = CardDefaults.cardElevation(4.dp),

        // Setting Custom Color for the Card
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),

        shape = RoundedCornerShape(8.dp)

    ) {

        // TODO - Have to fill the UI
        // Value Text
        Text(
            text = "Roll Number : " + attendanceData.roll,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )

    }
}


@Composable
fun AttendanceEntryCardFailure(
    textToShow: Int = R.string.failed_to_load_data_try_again,
    tryAgain: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        contentAlignment = Alignment.Center
    ) {
        TextButtonUI(textToShow = textToShow) {
            tryAgain()
        }
    }
}