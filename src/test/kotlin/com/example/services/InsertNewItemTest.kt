package com.example.services

import com.example.data.dao.ProductDao
import com.example.domain.models.CreateNewItem
import com.example.domain.models.ItemModel
import com.example.domain.services.CheckValidQuantityClass
import com.example.domain.services.InsertNewItemClass
import com.example.utils.ErrorCodes
import com.example.utils.ResponseMsg
import com.example.utils.ServiceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class InsertNewItemTest {

    private val productDao = mockk<ProductDao>()
    private val mockValidQuantity = mockk<CheckValidQuantityClass>()
    private val mockInsertNewItem = InsertNewItemClass(productDao,mockValidQuantity)
    private val item = CreateNewItem("xxx",20.0,"2kg")
    private val itemModel = ItemModel("9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8","xxx",20.0,"2kg","2023-08-17T13:23:42.836924","2023-08-17T13:23:42.836924")

    @Test
    fun testInsertItem(){

        coEvery { mockValidQuantity(any()) } returns ServiceResult.Success(true)
        coEvery { productDao.insertProduct(item) } returns ServiceResult.Success(itemModel)

        runBlocking {
            val result = mockInsertNewItem(item)
            assertEquals(result, ResponseMsg("Data inserted successfully : $itemModel"))
        }
    }

    @Test
    fun testFailInsertItem(){

        coEvery { mockValidQuantity(any()) } returns ServiceResult.Error(ErrorCodes.INVALID_QUANTITY)

        runBlocking {
            val result = mockInsertNewItem(item)
            assertEquals(result,ResponseMsg(ErrorCodes.INVALID_QUANTITY.message))
        }
    }

    @Test
    fun testExceptionInsertItem(){

        coEvery { mockValidQuantity(any()) } returns ServiceResult.Success(true)
        coEvery { productDao.insertProduct(item) } returns ServiceResult.Error(ErrorCodes.DATABASE_ERROR)

        runBlocking {
            val result = mockInsertNewItem(item)
            assertEquals(result,ResponseMsg("Insertion Failed"))
        }
    }

}