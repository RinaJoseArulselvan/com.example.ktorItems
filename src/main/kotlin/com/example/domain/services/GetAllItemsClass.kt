package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.domain.models.ItemModel
import com.example.utils.ServiceResult

class GetAllItemsClass(private val productDao: ProductDao) {
    suspend operator fun invoke():List<ItemModel?>{
        return when(val result = productDao.getAllItems()){
            is ServiceResult.Success -> result.data
            is ServiceResult.Error -> emptyList()
        }
    }
}