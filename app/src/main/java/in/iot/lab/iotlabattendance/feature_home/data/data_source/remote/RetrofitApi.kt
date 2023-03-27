package `in`.iot.lab.iotlabattendance.feature_home.data.data_source.remote

import `in`.iot.lab.iotlabattendance.core.util.Constants
import `in`.iot.lab.iotlabattendance.feature_home.data.model.AttendanceResponseData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This Interface contains all the Functions and calls that can be done on the API call
 *
 * @property getPostByRoll For fetching attendance with only Roll Number
 * @property getPostOfFixedDay For fetching attendance with Roll number and of a particular date
 * @property getPostBetweenDays For fetching attendance of a roll Number between two given dates
 */
interface RetrofitApi {

    // For fetching attendance with only Roll Number
    @GET(Constants.ATTENDANCE_ENDPOINT)
    suspend fun getPostByRoll(
        @Query("filter[roll][_eq]") number: String
    ): retrofit2.Response<AttendanceResponseData>

    // For fetching attendance with Roll number and of a particular date
    @GET(Constants.ATTENDANCE_ENDPOINT)
    suspend fun getPostOfFixedDay(
        @Query("filter[roll][_eq]") number: String,
        @Query("filter[day(in_time)][_eq]") inTimeDay: String,
        @Query("filter[month(in_time)][_eq]") inTimeMonth: String,
        @Query("filter[year(in_time)][_eq]") inTimeYear: String
    ): retrofit2.Response<AttendanceResponseData>

    // For fetching attendance of a roll Number between two given dates
    @GET(Constants.ATTENDANCE_ENDPOINT)
    suspend fun getPostBetweenDays(
        @Query("filter[roll][_eq]") number: String,
        @Query("filter[in_time][_between]") inTimeBetween: String
    ): retrofit2.Response<AttendanceResponseData>
}