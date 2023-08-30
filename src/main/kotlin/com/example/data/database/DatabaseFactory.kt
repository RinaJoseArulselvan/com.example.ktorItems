package com.example.data.database

import com.example.data.entities.ItemsEntity
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


object DatabaseFactory  {
    private fun hikari(isTest: Boolean): HikariDataSource {
        val config = if (isTest) {
            HikariConfig().apply {
                jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
                driverClassName = "org.h2.Driver"
                username = "sa"
                password = ""
            }
            }else{
            HikariConfig().apply {
                jdbcUrl = "jdbc:postgresql://localhost:5432/shoppingcart"
                driverClassName = "org.postgresql.Driver"
                username = "postgres"
                password = "rina2002"
                maximumPoolSize = 3
            }
            }
        config.validate()
        return HikariDataSource(config)
    }

    fun init(isTest: Boolean = false) {
        val database = Database.connect(hikari(isTest))
        transaction(database) {
            SchemaUtils.create(ItemsEntity)
        }
    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
