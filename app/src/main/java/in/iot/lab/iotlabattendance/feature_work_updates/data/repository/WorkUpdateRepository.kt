package `in`.iot.lab.iotlabattendance.feature_work_updates.data.repository

import `in`.iot.lab.iotlabattendance.feature_work_updates.data.data_source.remote.WorkUpdateRetrofitInstance
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateBody
import `in`.iot.lab.iotlabattendance.feature_work_updates.presentation.util.WorkUpdateApiState

/**
 *  This Repository does all the Database Connections in this Module and decides from where to fetch the data
 *
 *  @property postWorkUpdate This Function Posts the Work Update in the Database
 */
class WorkUpdateRepository {

    //  This Function Posts the Work Update in the Database
    suspend fun postWorkUpdate(postWorkUpdateBody: PostWorkUpdateBody): WorkUpdateApiState {

        // Posting the Data to the Database
        val response =
            WorkUpdateRetrofitInstance.workUpdateApi.postWorkUpdates(postWorkUpdateBody = postWorkUpdateBody)

        // Returning the State according to the Request State
        return if (response.isSuccessful)
            WorkUpdateApiState.Success(response.body()!!)
        else
            WorkUpdateApiState.Failure("Error Connecting to the Server")
    }
}