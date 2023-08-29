package com.example.services

import com.example.data.dao.ProductDao
import com.example.domain.models.UpdateItemModel
import com.example.domain.services.UpdateTheItemClass
import com.example.utils.ErrorCodes
import com.example.utils.ResponseMsg
import com.example.utils.ServiceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class UpdateItemTest {
    private val productDao = mockk<ProductDao>()
    private val updateItem = UpdateTheItemClass(productDao)

    private val item = UpdateItemModel("xxx",24.5,"2g")

    @Test
    fun testUpdateItem (){
        coEvery { productDao.checkItemExists(any()) } returns ServiceResult.Success(true)
        coEvery { productDao.updateItem(any()) } returns ServiceResult.Success(1)

        runBlocking {
            val result = updateItem(item)
            assertEquals(result, ResponseMsg("updated successfully"))
        }
    }

    @Test
    fun testFailUpdateItem(){
        coEvery { productDao.checkItemExists(any()) } returns ServiceResult.Error(ErrorCodes.ITEM_NOTFOUND)

        runBlocking {
            val result = updateItem(item)
            assertEquals(result,ResponseMsg(ErrorCodes.ITEM_NOTFOUND.message))
        }
    }

    @Test
    fun testExceptionUpdateItem(){
        coEvery { productDao.checkItemExists(any()) } returns ServiceResult.Success(true)
        coEvery { productDao.updateItem(any()) } returns ServiceResult.Error(ErrorCodes.DATABASE_ERROR)

        runBlocking {
            val result = updateItem(item)
            assertEquals(result,ResponseMsg(ErrorCodes.DATABASE_ERROR.message))
        }
    }
}