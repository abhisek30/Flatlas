package `in`.avinia.flatlas.presentation.ui.authentication.composable

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import `in`.avinia.flatlas.core.extension.getGoogleSignInClient
import `in`.avinia.flatlas.core.extension.logException
import `in`.avinia.flatlas.core.extension.logMessage

@Composable
fun GoogleSignInButton(
    context: Context,
    modifier: Modifier = Modifier,
    onSignInSuccess: (String) -> Unit
) {
    val googleSignInClient = getGoogleSignInClient(context)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    account?.idToken?.let { idToken ->
                        logMessage("GoogleSignInButton, got Id token: $idToken")
                        onSignInSuccess(idToken)
                    } ?: {
                        logMessage("GoogleSignInButton: Empty Id token")
                    }
                } catch (e: ApiException) {
                    logException(e)
                }
            }
        }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { launcher.launch(googleSignInClient.signInIntent) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Sign in with Google")
        }
    }
}