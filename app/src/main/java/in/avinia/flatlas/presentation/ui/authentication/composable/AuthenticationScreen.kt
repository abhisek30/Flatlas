package `in`.avinia.flatlas.presentation.ui.authentication.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import `in`.avinia.flatlas.core.extension.firebaseAuthWithGoogle
import `in`.avinia.flatlas.core.extension.logMessage
import `in`.avinia.flatlas.presentation.ui.authentication.AuthenticationAction

@Composable
fun AuthenticationScreen(modifier: Modifier = Modifier, action : (AuthenticationAction) -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GoogleSignInButton(
            context = context,
            modifier = Modifier
                .align(Alignment.Center)
        ) { idToken ->
            firebaseAuthWithGoogle(idToken) { success, token ->
                if (success) {
                    action.invoke(AuthenticationAction.SignInCta(token))
                    logMessage("MainActivity: Firebase Auth with Google success")
                } else {
                    logMessage("MainActivity: Firebase Auth with Google failed")
                }
            }
        }
    }
}