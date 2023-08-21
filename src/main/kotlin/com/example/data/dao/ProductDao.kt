package com.example.data.dao

import com.example.domain.models.CreateNewItem
import com.example.domain.models.ItemModel
import com.example.domain.models.UpdateItemModel
import com.example.utils.ServiceResult

interface ProductDao{

    suspend fun insertProduct(item : CreateNewItem): ServiceResult<Boolean>
    suspend fun getItemByName(item: String):ServiceResult<ItemModel?>
    suspend fun getAllItems():ServiceResult<List<ItemModel?>>
    suspend fun updateItem(item: UpdateItemModel):ServiceResult<Int>
    suspend fun deleteItem(itemName: String):ServiceResult<Int>
    suspend fun checkItemExists(name : String):ServiceResult<Boolean>

}