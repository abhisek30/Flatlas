package `in`.avinia.flatlas.data.remote.mapper

import `in`.avinia.flatlas.data.remote.dto.LoginDto
import `in`.avinia.flatlas.domain.model.Login

fun LoginDto.toDomain() = Login(
    userId = id ?: -1,
    token = token.orEmpty()
)