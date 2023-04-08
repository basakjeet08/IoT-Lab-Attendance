package `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util

import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateResponse
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateApiState.*

/**
 * This sealed Class contains all the States of Post Work Update Request in a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in posting Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class WorkUpdateApiState {

    object Initialized : WorkUpdateApiState()
    object Loading : WorkUpdateApiState()
    class Success(val postWorkUpdateResponse: PostWorkUpdateResponse) : WorkUpdateApiState()
    class Failure(val errorMessage: String) : WorkUpdateApiState()
}