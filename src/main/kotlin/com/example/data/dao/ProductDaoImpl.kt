package com.example.data.dao

import com.example.data.database.DatabaseFactory.dbQuery
import com.example.data.entities.ItemsEntity
import com.example.domain.models.CreateNewItem
import com.example.domain.models.Item
import com.example.domain.models.UpdateItemModel
import com.example.utils.ErrorCodes
import com.example.utils.ServiceResult
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.sql.SQLDataException
import java.util.UUID

class ProductDaoImpl : ProductDao {

    private fun resultRowToItem(row: ResultRow) : Item{
        return Item(
                id =row[ItemsEntity.id],
                name = row[ItemsEntity.name],
                price = row[ItemsEntity.price],
                quantity = row[ItemsEntity.quantity],
                createdAt = row[ItemsEntity.createdAt],
                updatedAt = row[ItemsEntity.updatedAt]
            )
    }

    override suspend fun insertProduct(item: CreateNewItem): ServiceResult<Item> {
        return try {
            dbQuery{
                ItemsEntity.
                insert {
                    it[id] = UUID.randomUUID()
                    it[name] = item.name
                    it[price] = item.price
                    it[quantity]= item.quantity
                }.resultedValues?.firstOrNull()?.let {
                    ServiceResult.Success(resultRowToItem(it))
                }?: ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
            }
        }catch (e:SQLDataException){
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        }

    }

    override suspend fun getItemByName( itemName: String): ServiceResult<Item?> {
        return try {
            val items = dbQuery {
                ItemsEntity.select {
                    ItemsEntity.name eq itemName
                }
                    .map(::resultRowToItem)
                    .singleOrNull()
                }
            ServiceResult.Success(items)
        }catch (e: SQLDataException){
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        }catch (e: Exception){
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }

    override suspend fun updateItem(item: UpdateItemModel): ServiceResult<Int> {
        return try {
            val item = dbQuery {
                ItemsEntity.update({
                    ItemsEntity.name eq item.name
                }) {
                    it[price] = item.price
                }
            }
            ServiceResult.Success(item)
            }catch (e: SQLDataException){
                ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
            }catch (e: Exception){
                ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
            }
    }

    override suspend fun getAllItems(): ServiceResult<List<Item>> {
        return try {
            val items = dbQuery {
                ItemsEntity.selectAll()
                    .map(:: resultRowToItem)
            }
            ServiceResult.Success(items)
        }catch (e: SQLDataException){
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        }catch (e: Exception){
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }

    override suspend fun deleteItem(itemName: String): ServiceResult<Int> {
        return try{
            val dbDelete = dbQuery {
                ItemsEntity.deleteWhere {
                    this.name eq itemName
                }
            }
            ServiceResult.Success(dbDelete)
        }catch(e: SQLDataException){
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        }catch (e: Exception){
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }

      override suspend fun checkItemExists(name: String): ServiceResult<Boolean> {
        return try {
            val dbResult = dbQuery {
                ItemsEntity.select {
                    ItemsEntity.name eq name
                }.map(::resultRowToItem)
            }
            if (dbResult.isNotEmpty()){
                ServiceResult.Success(true)
            }else {
                ServiceResult.Error(ErrorCodes.ITEM_NOTFOUND)
            }
        }catch(e: SQLDataException){
            ServiceResult.Error(ErrorCodes.DATABASE_ERROR)
        }catch (e: Exception){
            ServiceResult.Error(ErrorCodes.UNKNOWN_ERROR)
        }
    }
}
