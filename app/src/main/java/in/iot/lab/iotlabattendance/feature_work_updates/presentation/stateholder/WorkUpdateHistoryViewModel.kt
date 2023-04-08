package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.iotlabattendance.core.util.AuthenticatedUserData
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.repository.WorkUpdateRepository
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.screens.WorkUpdateHistoryScreen
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateGetState
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * This is the View Model of the [WorkUpdateHistoryScreen] and contains its data and states
 *
 * @property myRepository Repository Variable
 * @property userRoll User inputted roll Number
 * @property workUpdateGetState This variable contains the work Update Api Call State
 * @property updateUserRoll This Function updates the userRoll
 * @property getWorkUpdates This function fetches the Work Update History Data
 */
class WorkUpdateHistoryViewModel : ViewModel() {

    // Repository Variable
    private val myRepository = WorkUpdateRepository()

    // User inputted roll Number
    var userRoll: String by mutableStateOf(AuthenticatedUserData.userRoll!!)
        private set

    // This variable contains the work Update Api Call State
    var workUpdateGetState: WorkUpdateGetState by mutableStateOf(WorkUpdateGetState.Initialized)
        private set

    fun updateUserRoll(newRoll: String) {
        userRoll = newRoll
    }

    // This function fetches the Work Update History Data
    fun getWorkUpdates() {

        // Setting the Current State to Loading Before Starting to Fetch Data
        workUpdateGetState = WorkUpdateGetState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            workUpdateGetState = try {

                // Response from the Server
                myRepository.getWorkUpdate(userRoll)

            } catch (_: ConnectException) {
                WorkUpdateGetState.Failure("No Internet Connection")
            } catch (_: Exception) {
                WorkUpdateGetState.Failure("No Internet Connection")
            }
        }
    }
}