package `in`.iot.lab.iotlabattendance.feature_bottom_navigation.components

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import `in`.iot.lab.iotlabattendance.MainActivity
import `in`.iot.lab.iotlabattendance.R
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme

// This is the Preview function of the Top App Bar
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DefaultPreview() {
    CustomAppTheme {
        AppBar(
            topBarTitle = R.string.app_name,
            icon = R.drawable.logout
        )
    }
}

/**
 * This is the Composable for the App Top Bar
 *
 * @param modifier Default Modifier so that the Parent Function can send something if needed
 * @param topBarTitle The Title of the Top Bar will be passes by this
 * @param icon The Image which needs to be shown at the Left most Side will be passed here
 * @param contentDescription The Content Description of the Icon will be passed here
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    @StringRes topBarTitle: Int,
    icon: Int = R.drawable.logout,
    @StringRes contentDescription: Int? = null
) {

    // Context of the Activity
    val context = LocalContext.current

    // This Top App Bar aligns the title inside Centred Horizontally
    CenterAlignedTopAppBar(
        modifier = modifier,
        actions = {
//            Icon(
//                imageVector = icon,
//                contentDescription = contentDescription?.let { stringResource(id = it) },
//                modifier = Modifier
//                    .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
//                    .size(32.dp)
//                    .clickable {
//                        FirebaseAuth
//                            .getInstance()
//                            .signOut()
//
//                        // Redirecting to the Home Activity
//                        context.startActivity(Intent(context, MainActivity::class.java))
//                        (context as Activity).finish()
//
//                    }
//            )

            Image(
                painter = painterResource(id = icon),
                contentDescription = contentDescription?.let { stringResource(id = it) },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
                    .size(28.dp)
                    .clickable {
                        FirebaseAuth
                            .getInstance()
                            .signOut()

                        // Redirecting to the Home Activity
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as Activity).finish()
                    }
            )
        },
        title = {
            Text(text = stringResource(id = topBarTitle))
        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer
//        )
    )
}