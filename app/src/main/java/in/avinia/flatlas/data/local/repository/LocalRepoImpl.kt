package `in`.avinia.flatlas.data.local.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import `in`.avinia.flatlas.data.local.datastore.Datastore
import `in`.avinia.flatlas.data.local.datastore.dataStore
import `in`.avinia.flatlas.domain.repository.ILocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepoImpl @Inject constructor(
    private val context: Context
) : ILocalRepository {

    override suspend fun isLoggedIn(): Boolean = context.dataStore.data.map { preference ->
        preference[Datastore.token].isNullOrBlank().not()
    }.first()

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { preference ->
            preference[Datastore.token] = token
        }
    }

    override suspend fun getUserId(): Long? = context.dataStore.data.map { preference ->
        preference[Datastore.userId]
    }.first()

    override suspend fun saveUserId(userId: Long) {
        context.dataStore.edit { preference ->
            preference[Datastore.userId] = userId
        }
    }
}