package co.id.kedai.kedaiapp.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val URL = "https://app.kedai.or.id"
    const val URL_WEBVIEW = "https://app.kedai.or.id/webview/"
    private const val URL_STE = "https://ste.kedai.or.id/api/v1/ste/"

    val instances: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiInterface::class.java)
    }

    private val gson: Gson = GsonBuilder().setLenient().create()

    val instancesSte: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_STE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        retrofit.create(ApiInterface::class.java)
    }
}