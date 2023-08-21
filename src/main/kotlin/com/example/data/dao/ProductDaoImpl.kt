package com.example.data.dao

import com.example.data.database.DatabaseFactory.dbQuery
import com.example.data.entities.ItemsEntity
import com.example.domain.models.CreateNewItem
import com.example.domain.models.ItemModel
import com.example.domain.models.UpdateItemModel
import com.example.utils.ErrorCodes
import com.example.utils.ServiceResult
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.sql.SQLDataException
import java.util.UUID

class ProductDaoImpl : ProductDao {

    private fun resultRowToItem(row: ResultRow): ItemModel {
        return ItemModel(
            id = row[ItemsEntity.id].toString(),
            name = row[ItemsEntity.name],
            price = row[ItemsEntity.price],
            quantity = row[ItemsEntity.quantity],
            createdAt = row[ItemsEntity.createdAt].toString(),
            updatedAt = row[ItemsEntity.updatedAt].toString()
        )
    }

    override suspend fun insertProduct(item: CreateNewItem): ServiceResult<Boolean> {
        return try {
            dbQuery {
                ItemsEntity.insert {
                    it[id] = UUID.randomUUID()
                    it[name] = item.name
                    it[price] = item.price
                    it[quantity] = item.quantity
                }.resultedValues?.firstOrNull()?.let {
                    ServiceResult.Success(data = true)
                } ?: ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
            }
        } catch (e: SQLDataException) {
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        }

    }

    override suspend fun getItemByName(item: String): ServiceResult<ItemModel?> {
        return try {
            val items = dbQuery {
                ItemsEntity.select {
                    ItemsEntity.name eq item
                }
                    .map(::resultRowToItem)
                    .singleOrNull()
            }
            ServiceResult.Success(items)
        } catch (e: SQLDataException) {
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        } catch (e: Exception) {
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }

    override suspend fun updateItem(item: UpdateItemModel): ServiceResult<Int> {
        return try {
            val dbResult = dbQuery {
                ItemsEntity.update({
                    ItemsEntity.name eq item.name
                }) {
                    it[price] = item.price
                }
            }
            ServiceResult.Success(dbResult)
        } catch (e: SQLDataException) {
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        } catch (e: Exception) {
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }

    override suspend fun getAllItems(): ServiceResult<List<ItemModel>> {
        return try {
            val items = dbQuery {
                ItemsEntity.selectAll()
                    .map(::resultRowToItem)
            }
            ServiceResult.Success(items)
        } catch (e: SQLDataException) {
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        } catch (e: Exception) {
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }

    override suspend fun deleteItem(itemName: String): ServiceResult<Int> {
        return try {
            val dbDelete = dbQuery {
                ItemsEntity.deleteWhere {
                    this.name eq itemName
                }
            }
            ServiceResult.Success(dbDelete)
        } catch (e: SQLDataException) {
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        } catch (e: Exception) {
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }

    override suspend fun checkItemExists(name: String): ServiceResult<Boolean> {
        return try {
            val dbResponse = dbQuery {
                ItemsEntity.select {
                    ItemsEntity.name eq name
                }.map(::resultRowToItem)
            }
            if (dbResponse.isNotEmpty()) {
                ServiceResult.Success(true)
            } else {
                ServiceResult.Error(ErrorCodes.INVALID_ITEM)
            }
        } catch (e: SQLDataException) {
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        } catch (e: Exception) {
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }
}