package com.example.services

import com.example.data.dao.ProductDao
import com.example.domain.models.ItemModel
import com.example.domain.services.GetItemByNameClass
import com.example.utils.BaseResponse
import com.example.utils.ErrorCodes
import com.example.utils.ServiceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class GetItemByNameTest{

    private val productDao = mockk<ProductDao>()
    private val getItemByName = GetItemByNameClass(productDao)
    private val testMockResult = ServiceResult.Success(
        ItemModel(
            "9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8",
            "mong dal",
            100.0,
            "200g",
            "2023-08-17T13:23:42.836924",
            "2023-08-17T13:23:42.843925"
        )
    )
    private val testResult = BaseResponse.SuccessResponse(
        ItemModel(
            "9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8",
            "mong dal",
            100.0,
            "200g",
            "2023-08-17T13:23:42.836924",
            "2023-08-17T13:23:42.843925"
        )
    , "Items for mong dal")

    @Test
    fun testSuccessGetItemByName() {
        coEvery { productDao.checkItemExists(any()) } returns ServiceResult.Success(true)
        coEvery { productDao.getItemByName(any()) } returns testMockResult

        runBlocking {
           val result =  getItemByName("mong dal")
            assertEquals(result,testResult)
        }
    }


    @Test
    fun testFailureGetItemByName(){
        coEvery { productDao.checkItemExists(any()) } returns ServiceResult.Error(ErrorCodes.ITEM_NOTFOUND)

        runBlocking {
            val result = getItemByName("mong dal")
            assertEquals(result,BaseResponse.ErrorResponse(ErrorCodes.ITEM_NOTFOUND,ErrorCodes.ITEM_NOTFOUND.message))
        }
    }

    @Test
    fun testExceptionGetItemByName(){
        coEvery { productDao.checkItemExists(any()) } returns ServiceResult.Success(true)
        coEvery { productDao.getItemByName(any()) } returns ServiceResult.Error(ErrorCodes.DATABASE_ERROR)

        runBlocking {
            val result = getItemByName("mong dal")
            assertEquals(result,BaseResponse.ErrorResponse(ErrorCodes.DATABASE_ERROR,ErrorCodes.DATABASE_ERROR.message))
        }
    }
}