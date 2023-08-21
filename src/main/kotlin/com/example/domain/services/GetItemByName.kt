package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.utils.BaseResponse
import com.example.utils.ServiceResult

class GetItemByName(private val productDao: ProductDao) {
    suspend operator fun invoke(name:String):BaseResponse{
        return when(val dbResponse = productDao.getItemByName(name)){
            is ServiceResult.Success -> BaseResponse(dbResponse,"Items for $name")
            is ServiceResult.Error -> BaseResponse(dbResponse,dbResponse.error.message)
        }
    }
}