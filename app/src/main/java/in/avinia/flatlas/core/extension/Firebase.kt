package `in`.avinia.flatlas.core.extension

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging

fun logMessage(message: String) {
    Log.e("Firebase", "logMessage: $message")
    FirebaseCrashlytics.getInstance().log(message)
}

fun logException(exception: Throwable) {
    Log.e("Firebase", "logException: $exception")
    FirebaseCrashlytics.getInstance().recordException(exception)
}

fun fetchFcmToken(onTokenReceived: (String) -> Unit) {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("FCM", "FCM Token: $token")
                onTokenReceived(token)
            } else {
                Log.e("FCM", "Fetching FCM token failed", task.exception)
            }
        }
}

fun firebaseAuthWithGoogle(idToken: String, onResult: (Boolean, String?) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            logMessage("firebaseAuthWithGoogle: ${task.result}")
            val token = task.result?.user?.getIdToken(false)?.result?.token
            onResult(task.isSuccessful,token)
        }.addOnFailureListener {
            logMessage("firebaseAuthWithGoogle: ${it.message}")
            onResult(false,null)
        }.addOnCanceledListener {
            logMessage("firebaseAuthWithGoogle: Canceled")
            onResult(false,null)
        }
}