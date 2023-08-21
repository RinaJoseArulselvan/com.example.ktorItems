package com.example

import com.example.data.dao.ProductDaoImpl
import com.example.domain.models.CreateNewItem
import com.example.domain.models.ItemWithoutID
import com.example.domain.models.UpdateItemModel
import com.example.utils.ServiceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class ProductDaoImpTest {


    private val mockProductRepository: ProductDaoImpl = mockk()

    private val testInsertItem = CreateNewItem("boost",200.0,"250g")
    private val testGetItem = "mong dal"
    private val testUpdateItem = UpdateItemModel("mong dal",130.3,"200g")
    private val testDeleteItem = "boost"
    private val testResultInsertItem = ServiceResult.Success(true)
    private val testResultGetItem = ServiceResult.Success(
                        ItemWithoutID(
                            "9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8",
                            "mong dal",
                            100.0,
                            "200g",
                            "2023-08-17T13:23:42.836924",
                            "2023-08-17T13:23:42.843925"
                        )
    )
    private val testResultUpdateItem =  ServiceResult.Success(data = 1)
    private val testResultGetAllItems = ServiceResult.Success(listOf(
                        ItemWithoutID(
                            "5d980eb3-beec-47c9-8613-63a13419a97a",
                            "sugar",
                            50.0,
                            "20g",
                            "2023-08-10T18:06:53.208081",
                            "2023-08-10T18:06:53.213088"
                        ),
                        ItemWithoutID(
                            "c6dac153-9d78-41f1-99a4-afe953998971",
                            "salt",
                            50.0,
                            "20g",
                            "2023-08-11T11:02:09.817294",
                            "2023-08-11T11:02:09.822290"
                        ),
                        ItemWithoutID(
                            "10522817-95e0-460c-a179-f8216b36d44b",
                            "green dal",
                            100.0,
                            "200g",
                            "2023-08-17T13:09:58.923033",
                            "2023-08-17T13:09:58.930795"
                        ),
                        ItemWithoutID(
                            "8925d518-adda-46d4-9ac7-630e3bb5af9d",
                            "pen",
                            20.0,
                            "2g",
                            "2023-08-10T13:05:42.570212",
                            "2023-08-17T13:11:48.126399"
                        ),
                        ItemWithoutID(
                            "9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8",
                            "mong dal",
                            100.0,
                            "200g",
                            "2023-08-17T13:23:42.836924",
                            "2023-08-17T13:23:42.843925"
                        )
    ))
    private val testResultDeleteItem = ServiceResult.Success(data = 1)



    @Test
    fun testInsertProduct() = runBlocking {
        coEvery { mockProductRepository.insertProduct(any()) } returns testResultInsertItem

        val result = mockProductRepository.insertProduct(testInsertItem)
        assertEquals(testResultInsertItem,result)
    }

    @Test
    fun testGetItemByName() = runBlocking {
        coEvery { mockProductRepository.getItemByName(any()) } returns testResultGetItem

        val result = mockProductRepository.getItemByName(testGetItem)
        assertEquals(testResultGetItem,result)
    }

    @Test
    fun testUpdateItem() = runBlocking {
        coEvery { mockProductRepository.updateItem(any()) } returns testResultUpdateItem

        val result = mockProductRepository.updateItem(testUpdateItem)
        assertEquals(testResultUpdateItem,result)
    }

    @Test
    fun testDeleteItem() = runBlocking {
        coEvery { mockProductRepository.deleteItem(any()) } returns testResultDeleteItem

        val result = mockProductRepository.deleteItem(testDeleteItem)
        assertEquals(testResultDeleteItem,result)
    }

    @Test
    fun testGetAllItems() = runBlocking {
        coEvery { mockProductRepository.getAllItems() } returns testResultGetAllItems

        val result = mockProductRepository.getAllItems()
        assertEquals(testResultGetAllItems,result)
    }


}