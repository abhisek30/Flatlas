package `in`.avinia.flatlas.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("userId") val id: Long?,
    @SerializedName("token") val token: String?
)
