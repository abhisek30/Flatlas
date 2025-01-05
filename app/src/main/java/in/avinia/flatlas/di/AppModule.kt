package `in`.avinia.flatlas.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.avinia.flatlas.data.local.repository.LocalRepoImpl
import `in`.avinia.flatlas.data.remote.repository.RemoteRepoImpl
import `in`.avinia.flatlas.data.remote.service.ApiService
import `in`.avinia.flatlas.domain.repository.ILocalRepository
import `in`.avinia.flatlas.domain.repository.IRemoteRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepo(remoteRepoImpl: RemoteRepoImpl) : IRemoteRepository {
        return remoteRepoImpl
    }

    @Provides
    @Singleton
    fun provideLocalRepo(
        @ApplicationContext context: Context,
    ): ILocalRepository {
        return LocalRepoImpl(context)
    }

}