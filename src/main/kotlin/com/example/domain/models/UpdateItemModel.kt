package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateItemModel(val name:String , val price: Double, val quantity:String)
