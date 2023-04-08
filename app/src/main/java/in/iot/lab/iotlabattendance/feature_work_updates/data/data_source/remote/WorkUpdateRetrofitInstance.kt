package `in`.iot.lab.iotlabattendance.feature_work_updates.data.data_source.remote

import `in`.iot.lab.iotlabattendance.core.util.Constants.Companion.WORK_UPDATE_URL
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.data_source.remote.WorkUpdateRetrofitInstance.workUpdateApi
import `in`.iot.lab.iotlabattendance.feature_work_updates.data.data_source.remote.WorkUpdateRetrofitInstance.workUpdateRetrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This is the Retrofit Instance Object which have a retrofit instance which is getting used
 * all around this module in the App
 *
 * @property workUpdateRetrofit This variable is private and contains the Build Features
 * @property workUpdateApi This is public and contains the object of the class Implemented by the
 * retrofit Library
 */
object WorkUpdateRetrofitInstance {

    // Contains the Base Url which gives all the details from the Database
    private val workUpdateRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WORK_UPDATE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Variable which can be used to call all the functions of the RetrofitApi interface
    val workUpdateApi: WorkUpdateRetrofitApi by lazy {
        workUpdateRetrofit.create(WorkUpdateRetrofitApi::class.java)
    }
}