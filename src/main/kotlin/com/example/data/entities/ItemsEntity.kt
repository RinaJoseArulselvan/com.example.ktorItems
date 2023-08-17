package com.example.data.entities

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ItemsEntity : UUIDTable("items"){
    val name = varchar("name", 255).uniqueIndex()
    val price = double("price")
    val quantity = varchar("quantity",25)
    val createdAt = datetime("created_at").default(LocalDateTime.now())
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
}
