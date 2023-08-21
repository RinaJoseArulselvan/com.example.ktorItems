package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.domain.models.UpdateItemModel
import com.example.utils.BaseResponse
import com.example.utils.ServiceResult

class UpdateTheItem(private val productDao: ProductDao) {
    suspend operator fun invoke(updateItemModel: UpdateItemModel):BaseResponse{
        return  when(val result= productDao.updateItem(updateItemModel)){
                    is ServiceResult.Success -> BaseResponse(updateItemModel,"updated successfully")
                    is ServiceResult.Error -> BaseResponse(updateItemModel,result.error.message)
            }
    }
}