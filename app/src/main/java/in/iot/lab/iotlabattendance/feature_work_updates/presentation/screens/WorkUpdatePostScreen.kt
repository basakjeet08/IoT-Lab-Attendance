package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.components.WorkUpdateSearchBarUI
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.stateholder.WorkUpdatePostViewModel
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateApiState

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        WorkUpdatePostScreen()
    }
}


/**
 * This screen is the Main UI Screen of the File
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkUpdatePostScreen(
    modifier: Modifier = Modifier
) {

    // ViewModel Variable
    val myViewModel: WorkUpdatePostViewModel = viewModel()
    // Loading State of the Api Post Request
    var isLoadingState = false
    // Context of the Function
    val context = LocalContext.current

    // Checking what to do according to the different States of API Request
    when (myViewModel.workUpdateApiState) {
        is WorkUpdateApiState.Initialized -> {}
        is WorkUpdateApiState.Loading -> {
            isLoadingState = true
        }
        is WorkUpdateApiState.Success -> {
            Toast.makeText(
                context,
                "Update Posted Successfully",
                Toast.LENGTH_SHORT
            ).show()

            // Resetting the Api State
            myViewModel.resetApiStateToDefaults()

        }
        else -> {
            Toast.makeText(
                context,
                (myViewModel.workUpdateApiState as WorkUpdateApiState.Failure).errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            // Resetting the Api State
            myViewModel.resetApiStateToDefaults()
        }
    }

    // Main UI of the Screen Starts Here
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
                buttonText = R.string.post,
                textLabel = R.string.roll_no,
                onValueChange = { myViewModel.updateUserRoll(it) }
            ) {
                myViewModel.postWorkUpdate()
            }

            // Spacing of 16 dp
            Spacer(modifier = Modifier.height(16.dp))

            // This is the text Input where the user will give his updates
            OutlinedTextField(
                value = myViewModel.userUpdate,
                onValueChange = {
                    myViewModel.updateUserUpdate(it)
                },

                // This input field can contain more than 1 line
                singleLine = false,

                // This text field is over the whole screen
                modifier = Modifier
                    .fillMaxSize(),

                // This is the Label of the input which is shown to the top left when selected
                label = {
                    Text(
                        stringResource(id = R.string.post_work_updates_here),
                        color = MaterialTheme.colorScheme.primary
                    )
                },

                // Setting Custom Colors for the Outlined TextField
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = MaterialTheme.colorScheme.primary,
                    disabledBorderColor = MaterialTheme.colorScheme.primaryContainer,
                ),

                // Shape of the TextField
                shape = MaterialTheme.shapes.medium,
            )
        }

        // This shows the Circular Progress when the api State is Loading
        if (isLoadingState) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}