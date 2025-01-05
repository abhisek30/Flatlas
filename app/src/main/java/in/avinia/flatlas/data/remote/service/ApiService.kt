package `in`.avinia.flatlas.data.remote.service

import `in`.avinia.flatlas.data.remote.dto.LoginDto
import `in`.avinia.flatlas.data.remote.payload.LoginPayload
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/login")
    suspend fun login(
        @Header("idToken") idToken: String?,
        @Body body: LoginPayload,
    ): Response<LoginDto>

}