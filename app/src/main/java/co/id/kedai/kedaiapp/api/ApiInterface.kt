package co.id.kedai.kedaiapp.api

import co.id.kedai.kedaiapp.model.DaftarResponse
import co.id.kedai.kedaiapp.model.DataResponse
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiInterface {

    @GET("/api/ebooks")
    fun getDataEbook(@Query("page") page: Int):
            Call<DataResponse>

    @GET("/api/{category}/posts")
    fun getDataAllBlog(
        @Path("category") category: String,
        @Query("page") page: Int
    ):
            Call<DataResponse>

    @GET("/api/events")
    fun getDataEvent(@Query("page") page: Int):
            Call<DataResponse>

    @FormUrlEncoded
    @POST("daftar")
    fun steRegistration(
        @Field("nama") nama: String,
        @Field("tpt_lahir") tplLahir: String,
        @Field("tgl_lahir") tglLahir: String,
        @Field("jkl") jkl: String,
        @Field("goldarah") golDar: String,
        @Field("no_telepon") noTelp: String,
        @Field("email") email: String,
        @Field("alamat") alamat: String,
        @Field("kampus") kampus: String,
        @Field("alasan") alasan: String
    ): Call<DaftarResponse>
}