package `in`.avinia.flatlas.presentation.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.avinia.flatlas.core.extension.fetchFcmToken
import `in`.avinia.flatlas.domain.repository.ILocalRepository
import `in`.avinia.flatlas.domain.repository.IRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val remoteRepository: IRemoteRepository,
    private val localRepository: ILocalRepository,
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<AuthenticationEffect>()
    val uiEffect: SharedFlow<AuthenticationEffect> = _uiEffect

    private var fcmToken = ""
    init {
        fetchFcmToken(onTokenReceived = { fcm ->
            fcmToken = fcm
        })
    }

    fun handleAction(action: AuthenticationAction) {
        when(action) {
            is AuthenticationAction.SignInCta -> signIn(action.token)
        }
    }

    private fun signIn(idToken: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = remoteRepository.login(idToken,fcmToken)
            result.onSuccess { login ->
                if(login.token.isEmpty() || login.userId < 0) {
                    _uiEffect.emit(AuthenticationEffect.ShowError("Login failed"))
                } else {
                    localRepository.saveToken(login.token)
                    localRepository.saveUserId(login.userId)
                    _uiEffect.emit(AuthenticationEffect.NavigateToDashboard)
                }
            }.onFailure {
                _uiEffect.emit(AuthenticationEffect.ShowError("Login failed"))
            }
        }
    }
}