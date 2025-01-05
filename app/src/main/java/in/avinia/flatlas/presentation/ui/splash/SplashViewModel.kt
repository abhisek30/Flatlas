package `in`.avinia.flatlas.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.avinia.flatlas.domain.repository.ILocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localRepository: ILocalRepository,
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<SplashEffect>()
    val uiEffect : SharedFlow<SplashEffect> = _uiEffect

    init {
        checkIsAuthenticated()
    }

    private fun checkIsAuthenticated() {
        viewModelScope.launch(Dispatchers.IO) {
            val isAuthenticated = localRepository.isLoggedIn()
            if(isAuthenticated) {
                _uiEffect.emit(SplashEffect.NavigateToDashboard)
            } else {
                _uiEffect.emit(SplashEffect.NavigateToAuthentication)
            }
        }
    }
}