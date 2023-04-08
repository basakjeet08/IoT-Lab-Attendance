package `in`.iot.lab.iotlabattendance.feature_work_updates.data.model

/**
 * This function Contains the Data structure of the Work Update Post Request
 *
 * @param roll Roll Number of the person whose work update is being posted in the Database
 * @param updates The Work Update of the User
 */
data class PostWorkUpdateBody(
    val roll: String,
    val updates: String
)