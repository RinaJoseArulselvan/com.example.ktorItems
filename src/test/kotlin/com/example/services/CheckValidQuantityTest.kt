package com.example.services

import com.example.domain.services.CheckValidQuantityClass
import com.example.utils.ErrorCodes
import com.example.utils.ServiceResult
import kotlin.test.Test
import kotlin.test.assertEquals


class CheckValidQuantityTest {
    private val checkQuantity = CheckValidQuantityClass()

    @Test
    fun testCheckValidQuantity() {
        val result = checkQuantity("2kg")
        assertEquals(ServiceResult.Success(true),result)
    }

    @Test
    fun testInvalidQuantity(){
        val result = checkQuantity("2n")
        assertEquals(ServiceResult.Error(ErrorCodes.INVALID_QUANTITY),result)
    }
}