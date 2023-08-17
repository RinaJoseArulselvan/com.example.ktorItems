package com.example.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class CreateNewItem(
                 val name:String,
                 val price:Double,
                 val quantity:String,
                )
