package `in`.avinia.flatlas.data.remote.payload

import com.google.gson.annotations.SerializedName

data class LoginPayload(
    @SerializedName("fcmToken") val fcmToken: String
)
