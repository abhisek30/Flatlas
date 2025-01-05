package `in`.avinia.flatlas.domain.repository

import `in`.avinia.flatlas.domain.model.Login

interface IRemoteRepository {
    suspend fun login(idToken: String?, fcmToken: String): Result<Login>
}