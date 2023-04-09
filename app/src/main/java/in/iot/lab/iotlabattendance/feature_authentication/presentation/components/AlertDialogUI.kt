package `in`.iot.lab.iotlabattendance.feature_authentication.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme

// This is the Failure Preview function of the Alert Dialog Box
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun FailurePreview() {
    CustomAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AlertDialogUI(
                title = R.string.access_level_requested,
                labelText = R.string.your_request_for_admin_access_level,
                onDismissRequest = {}
            )
        }
    }
}

/**
 * This is the Function which builds the Alert Dialog Box
 *
 * @param title This is the heading of the Alert Dialog Box
 * @param labelText This is the text in the Body of the dialog Box
 * @param onDismissRequest This is the function which gets executed when the user dismisses the Box
 */
@Composable
fun AlertDialogUI(
    title: Int,
    labelText: Int,
    onDismissRequest: () -> Unit
) {

    // This function Draws the Dialog Box UI
    AlertDialog(

        // This function runs when the user dismisses the AlertDialog Box by clicking outside of the screen
        onDismissRequest = onDismissRequest,

        // This is the Confirm Button
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text(stringResource(id = R.string.okay))
            }
        },

        // This is the heading of the alert Dialog Box
        title = {
            Text(
                text = stringResource(id = title),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },

        // This is the body of the Alert Dialog Box
        text = {
            Text(
                text = stringResource(id = labelText),
                textAlign = TextAlign.Center
            )
        }
    )
}