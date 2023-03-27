package `in`.iot.lab.iotlabattendance.feature_home.data.data_source.remote

import `in`.iot.lab.iotlabattendance.core.util.Constants.Companion.BASE_URL
import `in`.iot.lab.iotlabattendance.feature_home.data.data_source.remote.RetrofitInstance.api
import `in`.iot.lab.iotlabattendance.feature_home.data.data_source.remote.RetrofitInstance.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This is the Retrofit Instance Object which have a retrofit instance which is getting used
 * all around this module in the App
 *
 * @property retrofit This variable is private and contains the Build Features
 * @property api This is public and contains the object of the class Implemented by the
 * retrofit Library
 */
object RetrofitInstance {

    // Contains the Base Url which gives all the details from the Database
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Variable which can be used to call all the functions of the RetrofitApi interface
    val api: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }
}