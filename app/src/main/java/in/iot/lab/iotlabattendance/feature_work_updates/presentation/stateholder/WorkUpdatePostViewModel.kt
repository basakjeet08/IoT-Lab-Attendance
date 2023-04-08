package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.iotlabattendance.core.util.AuthenticatedUserData
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateBody
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.repository.WorkUpdateRepository
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.screens.WorkUpdatePostScreen
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateApiState
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.UnknownHostException

/**
 *  This Class is the Work Update View Model Class which holds the State of the [WorkUpdatePostScreen]
 *
 */
class WorkUpdatePostViewModel : ViewModel() {

    // Repository Variable of the Class
    private val myRepository: WorkUpdateRepository = WorkUpdateRepository()

    // user Inputted Roll Number
    var userRoll: String by mutableStateOf(AuthenticatedUserData.userRoll!!)
        private set

    // user Inputted Update
    var userUpdate: String by mutableStateOf("")
        private set

    // Work Update Api State Holder
    var workUpdateApiState: WorkUpdateApiState by mutableStateOf(WorkUpdateApiState.Initialized)
        private set

    // This function Updates the User Inputted Roll
    fun updateUserRoll(newRoll: String) {
        userRoll = newRoll
    }

    // This function Updates the User Inputted Update
    fun updateUserUpdate(newValue: String) {
        userUpdate = newValue
    }

    // This function resets the API State
    fun resetApiStateToDefaults() {
        workUpdateApiState = WorkUpdateApiState.Initialized
    }

    // This function posts the Work Update in the Database
    fun postWorkUpdate() {

        workUpdateApiState = WorkUpdateApiState.Loading

        // Checking if all the fields are filled before proceeding
        if (userUpdate.isEmpty() || userRoll.isEmpty()) {
            workUpdateApiState = WorkUpdateApiState.Failure("Enter the Roll and Update")
            return
        }

        // making the body for the post Request
        val postWorkUpdateBody = PostWorkUpdateBody(
            roll = userRoll,
            updates = userUpdate
        )

        // Requesting the Server to post the data
        viewModelScope.launch {
            workUpdateApiState = try {

                // Posting the Data to the Database
                val response = myRepository.postWorkUpdate(postWorkUpdateBody = postWorkUpdateBody)

                // Updating the Api request State accordingly
                if (response is WorkUpdateApiState.Success)
                    WorkUpdateApiState.Success(response.postWorkUpdateResponse)
                else
                    WorkUpdateApiState.Failure("Failed!! Try Again")

            } catch (_: ConnectException) {
                WorkUpdateApiState.Failure("Internet not Available !!")
            } catch (_: UnknownHostException) {
                WorkUpdateApiState.Failure("Internet not Available !!")
            }
        }
    }
}