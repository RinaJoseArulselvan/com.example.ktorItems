package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.utils.Response
import com.example.utils.ServiceResult

class DeleteItemByNameClass(private val productDao: ProductDao) {
    suspend operator fun invoke(name : String):Response {
        return when (val itemExists = productDao.checkItemExists(name)) {
            is ServiceResult.Success -> {
                when (productDao.deleteItem(name)) {
                    is ServiceResult.Success -> Response("Deleted Successfully")
                    is ServiceResult.Error -> Response("Deletion failed")
                }
            }
            is ServiceResult.Error -> Response(itemExists.error.message)
        }
    }
}