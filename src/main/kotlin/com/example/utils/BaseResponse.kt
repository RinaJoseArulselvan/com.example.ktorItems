package com.example.utils

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse(
    @Contextual val item: Any,
    @Contextual val responseMsg:String
)