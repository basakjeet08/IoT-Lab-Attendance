package `in`.iot.lab.iotlabattendance.feature_work_updates.data.data_source.remote

import `in`.iot.lab.iotlabattendance.core.util.Constants
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateBody
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * This Interface contains all the Functions and calls that can be done on the API call
 *
 * @property postWorkUpdates This Function Post the Work Updates in the Database
 */
interface WorkUpdateRetrofitApi {

    // This Function Post the Work Updates in the Database
    @POST(Constants.POST_WORK_UPDATES)
    suspend fun postWorkUpdates(
        @Body postWorkUpdateBody: PostWorkUpdateBody
    ): retrofit2.Response<PostWorkUpdateResponse>
}