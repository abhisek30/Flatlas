package `in`.avinia.flatlas.core.extension

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import `in`.avinia.flatlas.BuildConfig

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val serverClientId = BuildConfig.SERVER_CLIENT_ID
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(serverClientId)
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}