package `in`.iot.lab.iotlabattendance.feature_work_updates.data.model

/**
 *  This class contains the Work Update Data Structure
 *
 *  @param date_created Creation Date of the Work Update
 *  @param date_updated Date when the Work Update is Updated
 *  @param roll User Roll whose Work Update it is
 *  @param updates This Provide the Update
 */
data class WorkUpdateData(
    val date_created: String,
    val date_updated: String,
    val roll: String,
    val updates: String
)