package `in`.iot.lab.iotlabattendance.feature_home.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.iot.lab.iotlabattendance.core.util.AuthenticatedUserData
import `in`.iot.lab.iotlabattendance.feature_home.data.repository.Repository
import `in`.iot.lab.iotlabattendance.feature_home.util.GetAttendanceUiState
import kotlinx.coroutines.launch
import java.net.ConnectException

class AttendanceViewModel : ViewModel() {

    // Repository Variable
    private val myRepository = Repository()

    // This variable contains the API call State
    var getAttendanceUiState: GetAttendanceUiState by mutableStateOf(
        GetAttendanceUiState.Initialized
    )
        private set

    var userRoll: String by mutableStateOf(AuthenticatedUserData.userRoll!!)
        private set


    var forFixedDay: String = ""
    var forFixedMonth: String = ""
    var forFixedYear: String = ""
    var inTimeBetweenStart: String = ""
    var inTimeBetweenEnd: String = ""

    var fromDate: String by mutableStateOf("")
        private set
    var untilDate: String by mutableStateOf("")
        private set

    fun updateFromDate(newDate: String) {
        fromDate = newDate
    }

    fun updateUntilDate(newDate: String) {
        untilDate = newDate
    }


    //It contains all the logic and the API calls
    fun controlFlow() {

        if (fromDate == "" && untilDate == "")
            getPostByRoll(userRoll)
        else if (fromDate != "" && untilDate == "")
            getPostOfFixedDay(forFixedDay, forFixedMonth, forFixedYear)
        else {
            getPostBetweenDays("$inTimeBetweenStart,$inTimeBetweenEnd")
        }

    }

    //This calls the repository and ask it to fetch data of roll without filter
    fun getPostByRoll(number: String = userRoll) {

        // Setting the Current State to Loading Before Starting to Fetch Data
        getAttendanceUiState = GetAttendanceUiState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            getAttendanceUiState = try {

                // Response from the Server
                myRepository.getPostByRoll(number)
            } catch (_: ConnectException) {
                GetAttendanceUiState.Failure("No Internet Connection")
            }
        }
    }

    //This calls the repository and ask it to fetch data of roll of a particular day
    private fun getPostOfFixedDay(
        inTimeDay: String,
        inTimeMonth: String,
        inTimeYear: String
    ) {

        // Setting the Current State to Loading Before Starting to Fetch Data
        getAttendanceUiState = GetAttendanceUiState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            getAttendanceUiState = try {

                // Response from the Server
                myRepository.getPostOfFixedDay(
                    userRoll,
                    inTimeDay,
                    inTimeMonth,
                    inTimeYear
                )
            } catch (_: ConnectException) {
                GetAttendanceUiState.Failure("No Internet Connection")
            }
        }
    }

    //This calls the repository and ask it to fetch data of roll within a range
    private fun getPostBetweenDays(inTimeBetween: String) {

        // Setting the Current State to Loading Before Starting to Fetch Data
        getAttendanceUiState = GetAttendanceUiState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            getAttendanceUiState = try {

                // Response from the Server
                myRepository.getPostBetweenDays(
                    userRoll,
                    inTimeBetween
                )

            } catch (_: ConnectException) {
                GetAttendanceUiState.Failure("No Internet Connection")
            }
        }
    }

    fun updateUserRoll(newRoll: String) {
        userRoll = newRoll
    }

    fun resetToDefaults() {
        forFixedDay = ""
        forFixedMonth = ""
        forFixedYear = ""
        fromDate = ""
        untilDate = ""
        inTimeBetweenStart = ""
        inTimeBetweenEnd = ""
    }
}