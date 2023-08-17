package com.example.domain.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class Item(
    @Contextual val id: EntityID<@Contextual UUID>,
    val name:String,
    val price:Double,
    val quantity:String,
    @Contextual val createdAt:LocalDateTime,
    @Contextual val updatedAt:LocalDateTime
)
