package `in`.iot.lab.iotlabattendance.feature_home.data.repository

import `in`.iot.lab.iotlabattendance.feature_home.data.data_source.remote.RetrofitInstance
import `in`.iot.lab.iotlabattendance.feature_home.util.GetAttendanceUiState

/**
 * This is the repository which is going to control flow of every data related to
 * feature_teacher list module
 *
 * @property getPostByRoll This fetches the attendance of roll without filters
 * @property getPostOfFixedDay This fetches the attendance of roll of a fixed Date
 * @property getPostBetweenDays This fetches the attendance of roll in a given range of Date
 */

class Repository {

    // This fetches the attendance of roll without filters
    suspend fun getPostByRoll(number: String): GetAttendanceUiState {

        // This is the Response from the Server
        val response = RetrofitInstance.api.getPostByRoll(number)

        return if (response.isSuccessful)
            GetAttendanceUiState.Success(attendanceResponseData = response.body()!!)
        else
            GetAttendanceUiState.Failure(errorMessage = "Error Connecting to the Server")
    }

    // This fetches the attendance of roll of a fixed Date
    suspend fun getPostOfFixedDay(
        rollNumber: String,
        inTimeDay: String,
        inTimeMonth: String,
        inTimeYear: String
    ): GetAttendanceUiState {

        // This is the Response from the Server
        val response = RetrofitInstance.api.getPostOfFixedDay(
            number = rollNumber,
            inTimeDay = inTimeDay,
            inTimeMonth = inTimeMonth,
            inTimeYear = inTimeYear
        )

        return if (response.isSuccessful)
            GetAttendanceUiState.Success(attendanceResponseData = response.body()!!)
        else
            GetAttendanceUiState.Failure(errorMessage = "Error Connecting to the Server")
    }

    // This fetches the attendance of roll in a given range of Date
    suspend fun getPostBetweenDays(
        rollNumber: String,
        inTimeBetween: String
    ): GetAttendanceUiState {

        // This is the Response from the Server
        val response = RetrofitInstance.api.getPostBetweenDays(rollNumber, inTimeBetween)

        return if (response.isSuccessful)
            GetAttendanceUiState.Success(attendanceResponseData = response.body()!!)
        else
            GetAttendanceUiState.Failure(errorMessage = "Error Connecting to the Server")
    }
}