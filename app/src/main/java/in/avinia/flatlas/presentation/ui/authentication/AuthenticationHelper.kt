package `in`.avinia.flatlas.presentation.ui.authentication


sealed class AuthenticationEffect {
    data object NavigateToDashboard : AuthenticationEffect()
    data class ShowError(val message: String) : AuthenticationEffect()
}

sealed class AuthenticationAction {
    data class SignInCta(val token: String?) : AuthenticationAction()
}
