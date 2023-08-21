package com.example.domain.services

import com.example.domain.models.CreateNewItem
import com.example.utils.ErrorCodes
import com.example.utils.ServiceResult


class CheckValidQuantity() {

     operator fun invoke(item: CreateNewItem):ServiceResult<Boolean>{
        return if (item.quantity.endsWith("kg")
                or (item.quantity.endsWith("mg"))
                or (item.quantity.endsWith("g"))){
                      ServiceResult.Success(true)
        }else
            ServiceResult.Error(ErrorCodes.INVALID_QUANTITY)
    }


}