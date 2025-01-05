package `in`.avinia.flatlas.presentation.ui.splash

sealed class SplashEffect {
    data object NavigateToDashboard : SplashEffect()

    data object NavigateToAuthentication : SplashEffect()
}