package `in`.iot.lab.iotlabattendance.feature_home.presentation.components

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
        AttendanceSearchBarUI(
            value = "",
            firstButtonText = R.string.go,
            secondButtonText = R.string.x,
            textLabel = R.string.roll_no,
            onValueChange = { },
            onClickClearButton = { }
        ) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceSearchBarUI(
    modifier: Modifier = Modifier,
    value: String,
    firstButtonText: Int,
    secondButtonText: Int,
    textLabel: Int,
    onValueChange: (String) -> Unit,
    onClickClearButton: () -> Unit,
    onClickEventGoButton: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },

            label = {
                Text(
                    stringResource(id = textLabel),
                    color = MaterialTheme.colorScheme.primary
                )
            },

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

            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .width(100.dp)
                .weight(weight = 1f, fill = true)
        )

        Spacer(modifier = Modifier.width(16.dp))


        // This is the Button inside which we keep the Gradient BOX implementation
        Button(
            modifier = modifier,
            onClick = onClickEventGoButton,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            contentPadding = PaddingValues(12.dp)
        ) {

            Text(
                text = stringResource(id = firstButtonText),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // This is the Button inside which we keep the Gradient BOX implementation
        Button(
            modifier = modifier,
            onClick = onClickClearButton,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            contentPadding = PaddingValues(12.dp)
        ) {

            Text(
                text = stringResource(id = secondButtonText),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}