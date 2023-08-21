package com.example.domain.services

import com.example.data.dao.ProductDao
import com.example.domain.models.CreateNewItem
import com.example.utils.Response
import com.example.utils.ServiceResult

class InsertNewItemClass(
    private val productDao: ProductDao,
    private val checkValidQuantity: CheckValidQuantityClass
) {
    suspend operator fun invoke(item: CreateNewItem):Response{
        return when(val result = checkValidQuantity(item)){
            is ServiceResult.Success -> {
                when( productDao.insertProduct(item)){
                    is ServiceResult.Success->{
                        Response("Data inserted successfully")
                    }
                    is ServiceResult.Error-> Response("Insertion Failed")
                }
            }
           is ServiceResult.Error->
               Response(result.error.message)
        }
    }
}

 /*
 suspend operator fun invoke(item: CreateNewItem):Any{
        return when(val result = checkValidQuantity(item)){
            is ServiceResult.Success -> {
                when(val dbResult = productDao.insertProduct(item)){
                    is ServiceResult.Success->{
                        BaseResponse.SuccessResponse(dbResult.data,"Data inserted successfully")
                    }
                    is ServiceResult.Error-> BaseResponse.ErrorResponse(dbResult.error,"Insertion Failed")
                }
            }
           is ServiceResult.Error->
               BaseResponse.ErrorResponse(result.error,result.error.message)
        }
    }
 */