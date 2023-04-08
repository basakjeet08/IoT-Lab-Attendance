package `in`.iot.lab.iotlabattendance.feature_home.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import java.text.SimpleDateFormat
import java.util.*

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
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
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
            .padding(top = 8.dp, bottom = 8.dp),

        // This Elevates the Card above the Background
        elevation = CardDefaults.cardElevation(4.dp),

        // Setting Custom Color for the Card
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,

            ),

        shape = MaterialTheme.shapes.medium,

        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary
        )

    ) {

        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            val currentInTime = attendanceData.in_time
            val currentOutTime = attendanceData.out_time


            //Format which we received From the API call
            val formatReceived = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'")

            //Setting TimeZone to Indian which is GMT_5.30
            formatReceived.timeZone = TimeZone.getTimeZone("GMT+5.30")

            //Setting Our Desired Format of Date and Time
            val desiredFormat = SimpleDateFormat("dd-MMM-yyyy      'Time : ' hh:mm a")

            //Making Date objects from the in time and out Time strings
            val dateInTime = currentInTime?.let { formatReceived.parse(it) }
            val dateOutTime = currentOutTime?.let { formatReceived.parse(it) }

            // Parsing the date Objects which returns the String in the format as specified in desiredFormat variable
            val correctedInTime = dateInTime?.let { desiredFormat.format(it) }
            val correctedOutTime = dateOutTime?.let { desiredFormat.format(it) }


            // Value Text
            Text(
                text = "Roll Number : " + attendanceData.roll,
                maxLines = 1,
                modifier = Modifier.padding(4.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Value Text
            Text(
                text = "Date : $correctedInTime",
                maxLines = 1,
                modifier = Modifier.padding(4.dp)
            )

            if (correctedOutTime != null) {
                // Value Text
                Text(
                    text = "Date : $correctedOutTime",
                    maxLines = 1,
                    modifier = Modifier.padding(4.dp)
                )
            } else {
                // Value Text
                Text(
                    text = "Out Time : Undefined",
                    maxLines = 1,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
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