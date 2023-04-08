package `in`.iot.lab.iotlabattendance.feature_work_updates.data.data_source.remote

import `in`.iot.lab.iotlabattendance.core.util.Constants
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.GetWorkUpdateResponse
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateBody
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.model.PostWorkUpdateResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * This Interface contains all the Functions and calls that can be done on the API call
 *
 * @property postWorkUpdates This Function Post the Work Updates in the Database
 * @property getWorkUpdates This function fetches the Work Update Data from the Database
 */
interface WorkUpdateRetrofitApi {

    // This Function Post the Work Updates in the Database
    @POST(Constants.WORK_UPDATES_ENDPOINT)
    suspend fun postWorkUpdates(
        @Body postWorkUpdateBody: PostWorkUpdateBody
    ): retrofit2.Response<PostWorkUpdateResponse>

    // This function fetches the Work Update Data from the Database
    @GET(Constants.WORK_UPDATES_ENDPOINT)
    suspend fun getWorkUpdates(
        @Query("filter[roll][_eq]") rollNumber: String
    ): retrofit2.Response<GetWorkUpdateResponse>
}