package `in`.iot.lab.iotlabattendance.feature_home.data.model

import com.google.gson.annotations.SerializedName

/**
 * This is the structure of the Get Attendance request which contains a list of Attendance Data
 *
 * @param attendanceData This contains a list of attendance Data
 */
data class AttendanceResponseData(
    @SerializedName("data")
    val attendanceData: List<AttendanceData>?
)