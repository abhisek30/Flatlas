package `in`.avinia.flatlas.data.remote.repository

import `in`.avinia.flatlas.data.remote.mapper.toDomain
import `in`.avinia.flatlas.data.remote.payload.LoginPayload
import `in`.avinia.flatlas.data.remote.service.ApiService
import `in`.avinia.flatlas.domain.model.Login
import `in`.avinia.flatlas.domain.repository.IRemoteRepository
import javax.inject.Inject

class RemoteRepoImpl @Inject constructor(
    private val apiService: ApiService,
) : IRemoteRepository {

    override suspend fun login(idToken: String?, fcmToken: String): Result<Login> {
        val response = apiService.login(idToken, LoginPayload(fcmToken))
        return if (response.isSuccessful) {
            response.body()?.let { loginDto ->
                Result.success(loginDto.toDomain())
            } ?: run {
                Result.failure(Exception("Login failed $response"))
            }
        } else {
            Result.failure(Exception("Login failed $response"))
        }
    }
}