package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.utils.Response
import com.example.utils.ServiceResult

class DeleteItemByName(private val productDao: ProductDao) {
    suspend operator fun invoke(name : String):Response{
       return when(productDao.deleteItem(name)){
            is ServiceResult.Success -> Response("Deleted Successfully")
            is ServiceResult.Error -> Response("Deletion failed")
        }
    }
}