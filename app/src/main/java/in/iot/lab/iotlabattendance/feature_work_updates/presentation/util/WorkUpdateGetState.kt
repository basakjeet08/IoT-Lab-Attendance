package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util

import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateResponse
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateGetState.*

/**
 * This sealed Class contains all the States of Get Work Update History Request in a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in posting Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class WorkUpdateGetState {

    object Initialized : WorkUpdateGetState()
    object Loading : WorkUpdateGetState()
    class Success(val postWorkUpdateResponse: List<PostWorkUpdateResponse>) : WorkUpdateGetState()
    class Failure(val errorMessage: String) : WorkUpdateGetState()
}