package com.example.utils

import kotlinx.serialization.Serializable

@Serializable
sealed class ServiceResult<out T> {
    @Serializable
    data class Success<out T>(val data: T) : ServiceResult<T>()
    @Serializable
    data class Error(val error: ErrorCodes) : ServiceResult<Nothing>()
}
