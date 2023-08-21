package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.domain.models.CreateNewItem
import com.example.utils.BaseResponse
import com.example.utils.ServiceResult

class InsertNewItem(
    private val productDao: ProductDao,
    private val checkValidQuantity: CheckValidQuantity
) {
    suspend operator fun invoke(item: CreateNewItem):BaseResponse{
        return when(val result = checkValidQuantity(item)){
            is ServiceResult.Success -> {
                when(val dbResult = productDao.insertProduct(item)){
                    is ServiceResult.Success->{
                        BaseResponse(dbResult.data,"Data inserted successfully")
                    }
                    is ServiceResult.Error-> BaseResponse(item,"Insertion Failed")
                }
            }
           is ServiceResult.Error->
               BaseResponse(item,result.error.message)
        }
    }
}