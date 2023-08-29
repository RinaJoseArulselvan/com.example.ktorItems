package com.example.services

import com.example.data.dao.ProductDao
import com.example.domain.models.ItemModel
import com.example.domain.services.GetAllItemsClass
import com.example.utils.ServiceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals


class GetAllItemTest {

    private val productDao = mockk<ProductDao>()

    private val getAllItem = GetAllItemsClass(productDao)

    private val responseGetAllItems = listOf(
        ItemModel(
            "5d980eb3-beec-47c9-8613-63a13419a97a",
            "sugar",
            50.0,
            "20g",
            "2023-08-10T18:06:53.208081",
            "2023-08-10T18:06:53.213088"
        ),
        ItemModel(
            "c6dac153-9d78-41f1-99a4-afe953998971",
            "salt",
            50.0,
            "20g",
            "2023-08-11T11:02:09.817294",
            "2023-08-11T11:02:09.822290"
        ),
        ItemModel(
            "10522817-95e0-460c-a179-f8216b36d44b",
            "green dal",
            100.0,
            "200g",
            "2023-08-17T13:09:58.923033",
            "2023-08-17T13:09:58.930795"
        ),
        ItemModel(
            "8925d518-adda-46d4-9ac7-630e3bb5af9d",
            "pen",
            20.0,
            "2g",
            "2023-08-10T13:05:42.570212",
            "2023-08-17T13:11:48.126399"
        ),
        ItemModel(
            "9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8",
            "mong dal",
            100.0,
            "200g",
            "2023-08-17T13:23:42.836924",
            "2023-08-17T13:23:42.843925"
        )
    )
    private val testResultGetAllItems = ServiceResult.Success(listOf(
        ItemModel(
            "5d980eb3-beec-47c9-8613-63a13419a97a",
            "sugar",
            50.0,
            "20g",
            "2023-08-10T18:06:53.208081",
            "2023-08-10T18:06:53.213088"
        ),
        ItemModel(
            "c6dac153-9d78-41f1-99a4-afe953998971",
            "salt",
            50.0,
            "20g",
            "2023-08-11T11:02:09.817294",
            "2023-08-11T11:02:09.822290"
        ),
        ItemModel(
            "10522817-95e0-460c-a179-f8216b36d44b",
            "green dal",
            100.0,
            "200g",
            "2023-08-17T13:09:58.923033",
            "2023-08-17T13:09:58.930795"
        ),
        ItemModel(
            "8925d518-adda-46d4-9ac7-630e3bb5af9d",
            "pen",
            20.0,
            "2g",
            "2023-08-10T13:05:42.570212",
            "2023-08-17T13:11:48.126399"
        ),
        ItemModel(
            "9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8",
            "mong dal",
            100.0,
            "200g",
            "2023-08-17T13:23:42.836924",
            "2023-08-17T13:23:42.843925"
        )
    ))

    private val testFailureResult = emptyList<ItemModel>()
    @Test
    fun testGetAllItems() = runBlocking {

        coEvery { productDao.getAllItems() } returns testResultGetAllItems

        val result = getAllItem.invoke()
        assertEquals(result,responseGetAllItems)
    }

    @Test
    fun testFailGetAllItems() {

        coEvery { productDao.getAllItems() } returns ServiceResult.Success(emptyList())

        runBlocking {
            val result = getAllItem.invoke()
            assertEquals(result,testFailureResult)
        }
    }



}
