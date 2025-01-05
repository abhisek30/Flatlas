package `in`.avinia.flatlas.domain.repository

interface ILocalRepository {

    suspend fun isLoggedIn(): Boolean

    suspend fun saveToken(token: String)

    suspend fun getUserId(): Long?

    suspend fun saveUserId(userId: Long)
}