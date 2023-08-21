package com.example.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class ItemModel(
                         val id:String,
                         val name:String,
                         val price:Double,
                         val quantity:String,
                         val createdAt: String,
                         val updatedAt: String
)
