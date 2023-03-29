package `in`.iot.lab.iotlabattendance

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import `in`.iot.lab.iotlabattendance.core.theme.CustomAppTheme
import `in`.iot.lab.iotlabattendance.feature_bottom_navigation.HomeActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Starting the New Activity
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
        }
    }
}