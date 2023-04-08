package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.stateholder

import android.util.Log.d
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import `in`.iot.lab.iotlabattendance.core.util.AuthenticatedUserData
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.repository.WorkUpdateRepository
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateGetState

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

        d("Get State" , "Invoked")

        workUpdateGetState = WorkUpdateGetState.Success(
            postWorkUpdateResponse = listOf()
        )
    }
}