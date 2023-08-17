package com.example.domain.models

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val username:String, val password:String): Principal
