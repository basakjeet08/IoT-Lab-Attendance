package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.components

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
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.WorkUpdateData
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.stateholder.WorkUpdateHistoryViewModel
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateGetState
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
        WorkUpdateItemControl(
            myViewModel = WorkUpdateHistoryViewModel()
        )
    }
}

/**
 * This is the Control of the Work Update Item Card Views
 */
@Composable
fun WorkUpdateItemControl(
    myViewModel: WorkUpdateHistoryViewModel
) {

    // Checking the Api Request State and setting the Views accordingly
    when (myViewModel.workUpdateGetState) {
        is WorkUpdateGetState.Initialized -> {
            myViewModel.getWorkUpdates()
        }
        is WorkUpdateGetState.Loading -> {
            WorkUpdateItemLoading()
        }
        is WorkUpdateGetState.Success -> {

            // This is the List of Work Update Data of the Roll Number
            val data = (myViewModel.workUpdateGetState as WorkUpdateGetState.Success)
                .data

            // Checking if the data is empty or not
            if (data == null || data.isEmpty()) {
                WorkUpdateItemFailure(
                    textToShow = R.string.database_is_empty_found_no_records,
                    tryAgain = { myViewModel.getWorkUpdates() }
                )
            } else {
                LazyColumn {
                    items(data.size) {
                        WorkUpdateItemSuccess(
                            workUpdateData = data[it]
                        )
                    }
                }
            }
        }
        else -> {
            WorkUpdateItemFailure(
                textToShow = R.string.failed_to_load_data_try_again,
                tryAgain = { myViewModel.getWorkUpdates() }
            )
        }
    }
}

/**
 * This function is executed when the Api Call State is Loading
 */
@Composable
fun WorkUpdateItemLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * This function is Executed when the Api Call state is Success
 *
 * @param workUpdateData This is each Work Update Data of the user going inside each card View
 */
@Composable
fun WorkUpdateItemSuccess(
    modifier: Modifier = Modifier,
    workUpdateData: WorkUpdateData
) {

    Card(
        modifier = modifier
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

            // Getting the Formatted Creation Date and Time
            val formattedCreatedDate = timeFormat(workUpdateData.date_created)

            // getting the formatted update Date and Time
            var formattedUpdatedDate: String? = null
            if (workUpdateData.date_updated != null)
                formattedUpdatedDate = timeFormat(workUpdateData.date_created)

            // Value Text
            Text(
                text = "Roll Number : ${workUpdateData.roll}",
                maxLines = 1,
                modifier = Modifier.padding(4.dp)
            )

            // Spacing of 4 dp
            Spacer(modifier = Modifier.height(4.dp))

            // Value Text
            Text(
                text = "Update : ${workUpdateData.updates}",
                maxLines = 1,
                modifier = Modifier.padding(4.dp)
            )

            // Spacing of 4 dp
            Spacer(modifier = Modifier.height(4.dp))

            // Value Text
            Text(
                text = "Created : $formattedCreatedDate",
                maxLines = 1,
                modifier = Modifier.padding(4.dp)
            )

            // Checking if the Update date is Null of there
            if (formattedUpdatedDate != null) {
                // Value Text
                Text(
                    text = "Updated : $formattedUpdatedDate ",
                    maxLines = 1,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

/**
 * This is the function which is executed when the Work Update Get Call is a Failure
 */
@Composable
fun WorkUpdateItemFailure(
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

// This function gives the Formatted Date and Time
private fun timeFormat(time: String): String? {

    //Format which we received From the API call
    val formatReceived = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS.sss'Z'")

    //Setting TimeZone to Indian which is GMT_5.30
    formatReceived.timeZone = TimeZone.getTimeZone("GMT+5.30")

    //Setting Our Desired Format of Date and Time
    val desiredFormat = SimpleDateFormat("dd-MMM-yyyy  'Time : ' hh:mm a")

    //Making Date objects from the in time and out Time strings
    val dateObject = time.let { formatReceived.parse(it) }

    // Parsing the date Objects which returns the String in the format as specified in desiredFormat variable
    return dateObject?.let { desiredFormat.format(it) }
}