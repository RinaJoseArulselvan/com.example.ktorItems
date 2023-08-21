package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.utils.BaseResponse
import com.example.utils.ServiceResult

class GetItemByNameClass(private val productDao: ProductDao) {
    suspend operator fun invoke(name:String): Any {
        return when(val itemExists = productDao.checkItemExists(name)){
            is ServiceResult.Success-> {
                when (val dbResponse = productDao.getItemByName(name)) {
                    is ServiceResult.Success -> BaseResponse.SuccessResponse(dbResponse.data, "Items for $name")
                    is ServiceResult.Error -> BaseResponse.ErrorResponse(dbResponse.error, dbResponse.error.message)
                }
            }
            is ServiceResult.Error-> BaseResponse.ErrorResponse(itemExists.error,itemExists.error.message)
        }

    }
}