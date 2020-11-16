package model

import io.ktor.auth.*


data class UserModel(
    val id: Int = 0,
    val userName: String,
    val password: String
): Principal