package com.example.data.entities

import org.jetbrains.exposed.sql.Table

object LoginEntity :Table("login") {

    private val id = integer("id").autoIncrement()
    val username = varchar(name = "user", length = 255)
    val password = varchar(name = "pass", length = 255)

    override val primaryKey = PrimaryKey(id)

}