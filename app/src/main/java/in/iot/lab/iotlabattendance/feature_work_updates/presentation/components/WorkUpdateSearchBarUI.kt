package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.core.theme.buttonShape
import `in`.iot.lab.iotlabattendance.core.util.AuthenticatedUserData
import `in`.iot.lab.iotlabattendance.core.util.UserRole

// This is the Preview function of the Screen when Loading
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        WorkUpdateSearchBarUI(
            value = "",
            buttonText = R.string.post,
            textLabel = R.string.roll_no,
            onValueChange = { }
        ) {}
    }
}

/**
 * This function draws the UI of the Search Bar in the Work Update Screen
 *
 * @param modifier Default Given from the parent for dynamic modifications
 * @param value Value that is entered by the User
 * @param buttonText This is the text of the Button
 * @param textLabel This is the Label which will be show above the Outlined Text Field indicating
 * what to write inside the textField
 * @param onValueChange This function is called when the value inside the text Field change
 * @param onClickEventPostButton This function is called when the Button Is Clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkUpdateSearchBarUI(
    modifier: Modifier = Modifier,
    value: String,
    buttonText: Int,
    textLabel: Int,
    onValueChange: (String) -> Unit,
    onClickEventPostButton: () -> Unit
) {

    // Focus Manager Variable
    val focusManager = LocalFocusManager.current

    // Main UI of the Function
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // This Contains the Roll Number inputted by the User
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },

            label = {
                Text(
                    stringResource(id = textLabel),
                    color = MaterialTheme.colorScheme.primary
                )
            },

            // Checking if the User is an Admin before giving the Admin Access
            enabled = AuthenticatedUserData.userRole == UserRole.Admin,

            // Setting Custom Colors for the Outlined TextField
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primaryContainer,
            ),

            // Shape of the TextField
            shape = MaterialTheme.shapes.medium,

            // Single Line Allowed in Input
            singleLine = true,

            // Setting the Keyboard Button to Done and the code to clear the focus
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),

            // Setting the Keyboard to Numbers Only
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .width(100.dp)
                .weight(weight = 1f, fill = true)
        )

        // Spacing of 16 dp
        Spacer(modifier = Modifier.width(16.dp))


        // This is the Button inside which we keep the Gradient BOX implementation
        Button(
            modifier = modifier,
            onClick = onClickEventPostButton,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            contentPadding = PaddingValues(12.dp)
        ) {

            // This is the Text inside the Button
            Text(
                text = stringResource(id = buttonText),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}