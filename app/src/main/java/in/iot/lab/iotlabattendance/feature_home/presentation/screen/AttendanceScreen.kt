package `in`.iot.lab.iotlabattendance.feature_home.presentation.screen

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.feature_home.presentation.components.AttendanceEntryCardUIControl
import `in`.iot.lab.iotlabattendance.feature_home.presentation.components.SearchBarUI
import `in`.iot.lab.iotlabattendance.feature_home.presentation.stateholder.AttendanceViewModel
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
        AttendanceScreen()
    }
}

@Composable
fun AttendanceScreen(
    modifier: Modifier = Modifier
) {

    val myViewModel: AttendanceViewModel = viewModel()
    val context = LocalContext.current

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
                firstButtonText = R.string.go,
                secondButtonText = R.string.x,
                textLabel = R.string.roll_no,
                onValueChange = { myViewModel.updateUserRoll(it) },
                onClickClearButton = { myViewModel.resetToDefaults() }
            ) {
                myViewModel.controlFlow()
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                OutlinedButton(onClick = {
                    setDatePicker(
                        1,
                        context = context,
                        myViewModel
                    )
                }) {
                    Text(text = "From ${myViewModel.fromDate}")
                }

                OutlinedButton(onClick = {
                    setDatePicker(
                        2,
                        context = context,
                        myViewModel
                    )
                }) {
                    Text(text = "Until ${myViewModel.untilDate}")
                }
            }

            AttendanceEntryCardUIControl(myViewModel = myViewModel)
        }
    }
}

// Setting up the Date Picker and taking the Dates as follows
private fun setDatePicker(flagValue: Int, context: Context, myViewModel: AttendanceViewModel) {

    val myCalendar = Calendar.getInstance()
    val cal = DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDay ->
        when (flagValue) {
            1 -> {
                myViewModel.forFixedDay = selectedDay.toString()
                myViewModel.forFixedMonth = (selectedMonth + 1).toString()
                myViewModel.forFixedYear = selectedYear.toString()
                myViewModel.inTimeBetweenStart = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                val formatToShow = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                myViewModel.updateFromDate(formatToShow)
            }
            2 -> {
                myViewModel.inTimeBetweenEnd = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                val formatToShow = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                myViewModel.updateUntilDate(formatToShow)
            }
        }
    }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DATE))
    cal.show()
}