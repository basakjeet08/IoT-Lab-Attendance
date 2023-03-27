package `in`.iot.lab.iotlabattendance.feature_home.util

import `in`.iot.lab.iotlabattendance.feature_home.data.model.AttendanceResponseData
import `in`.iot.lab.iotlabattendance.feature_home.util.GetAttendanceUiState.*

/**
 * This sealed Class contains all the States of the Get Attendance of a API
 *
 * @property Initialized is used to define the Initial State
 * @property Loading is used to define the state of the API call when it is in fetching Phase
 * @property Success is used to define when the API call is a Success
 * @property Failure is used to define when the API call is a Failure
 */
sealed class GetAttendanceUiState {
    object Initialized : GetAttendanceUiState()
    object Loading : GetAttendanceUiState()
    class Success(val attendanceResponseData: AttendanceResponseData) : GetAttendanceUiState()
    class Failure(val errorMessage: String) : GetAttendanceUiState()
}