package com.example.services

import com.example.data.dao.ProductDao
import com.example.domain.services.DeleteItemByNameClass
import com.example.utils.ErrorCodes
import com.example.utils.ResponseMsg
import com.example.utils.ServiceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteItemByNameTest {

    private val productDao = mockk<ProductDao>()
    private val deleteItemByName = DeleteItemByNameClass(productDao)

    @Test
    fun testSuccessDeleteItemByName() {

        coEvery { productDao.checkItemExists("pen") } returns ServiceResult.Success(true)
        coEvery { productDao.deleteItem("pen") } returns ServiceResult.Success(1)

        runBlocking {
            val result = deleteItemByName("pen")
            assertEquals(ResponseMsg("Deleted Successfully"),result)
        }

    }

    @Test
    fun testFailureDeleteItemByName()  {

        coEvery { productDao.checkItemExists("pen") } returns ServiceResult.Error(ErrorCodes.ITEM_NOTFOUND)

        runBlocking {
            val result = deleteItemByName("pen")
            assertEquals(ResponseMsg(ErrorCodes.ITEM_NOTFOUND.message),result)
        }
    }
}