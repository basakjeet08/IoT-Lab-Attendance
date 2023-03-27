package `in`.iot.lab.iotlabattendance.feature_home.presentation.stateholder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    //This calls the repository and ask it to fetch data of roll without filter
    fun getPostByRoll(number: String) {

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
    fun getPostOfFixedDay(
        rollNumber: String,
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
                    rollNumber,
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
    fun getPostBetweenDays(rollNumber: String, inTimeBetween: String) {

        // Setting the Current State to Loading Before Starting to Fetch Data
        getAttendanceUiState = GetAttendanceUiState.Loading

        // Fetching the Data from the Server
        viewModelScope.launch {

            // Checking the State of the API call and storing it to reflect to the UI Layer
            getAttendanceUiState = try {

                // Response from the Server
                myRepository.getPostBetweenDays(
                    rollNumber,
                    inTimeBetween
                )

            } catch (_: ConnectException) {
                GetAttendanceUiState.Failure("No Internet Connection")
            }
        }
    }
}