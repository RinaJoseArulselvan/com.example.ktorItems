package com.example.data.dao

import com.example.domain.models.CreateNewItem
import com.example.domain.models.Item
import com.example.domain.models.UpdateItemModel
import com.example.utils.ServiceResult

interface ProductDao{

    suspend fun insertProduct(item : CreateNewItem): ServiceResult<Item>
    suspend fun getItemByName(item: String):ServiceResult<Item?>
    suspend fun getAllItems():ServiceResult<List<Item?>>
    suspend fun updateItem(item: UpdateItemModel):ServiceResult<Int>
    suspend fun deleteItem(itemName: String):ServiceResult<Int>
    suspend fun checkItemExists(name:String):ServiceResult<Boolean>

}
