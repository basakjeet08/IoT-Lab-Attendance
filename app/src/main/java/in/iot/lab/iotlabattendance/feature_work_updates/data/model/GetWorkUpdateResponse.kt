package `in`.iot.lab.iotlabattendance.feature_work_updates.data.model

/**
 * This is the Structure of the Get Work Update Response Api Call
 *
 * @param data This contains the list of the work Update data fetched
 */
data class GetWorkUpdateResponse(
    val data: List<WorkUpdateData>?
)