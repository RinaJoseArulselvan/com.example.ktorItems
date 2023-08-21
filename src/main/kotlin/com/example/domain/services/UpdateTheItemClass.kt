package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.domain.models.UpdateItemModel
import com.example.utils.Response
import com.example.utils.ServiceResult

class UpdateTheItemClass(private val productDao: ProductDao) {
    suspend operator fun invoke(updateItemModel: UpdateItemModel):Response{
        return when(val itemExists = productDao.checkItemExists(updateItemModel.name)) {
            is ServiceResult.Success -> {
                when (val result = productDao.updateItem(updateItemModel)) {
                    is ServiceResult.Success -> Response("updated successfully")
                    is ServiceResult.Error -> Response(result.error.message)
                }
            }

            is ServiceResult.Error -> Response(itemExists.error.message)
        }
    }
}