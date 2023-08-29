package com.example.data.dao

import com.example.data.database.DatabaseFactory
import com.example.domain.models.CreateNewItem
import com.example.domain.models.ItemModel
import com.example.domain.models.UpdateItemModel
import com.example.utils.ServiceResult
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.*

class ProductDaoImplTest {

    private var productDao =  ProductDaoImpl()
    private lateinit var insertedProduct:ItemModel

    @Before
    fun setUp() {
        DatabaseFactory.init(isTest = true)
    }


    private val testInsertItem = CreateNewItem("boost",200.0,"250g")
    private val testGetItem = "tea"
    private val testUpdateItem = UpdateItemModel("tea",100.3,"200g")
    private val testDeleteItem = "tea"
    private val testItemExists = "tea"
    private val testResultUpdateItem =  ServiceResult.Success(data = 1)
    private val testResultDeleteItem = ServiceResult.Success(data = 1)
    private val testResultItemExists = ServiceResult.Success(data = true)

    @Test
    fun testInsertProduct() = testApplication {

        runBlocking {
            val result = productDao.insertProduct(testInsertItem)
            assertNotNull(result)
        }

    }


    @Test
    fun testGetProductByName() = testApplication {

        val dbResult = productDao.insertProduct(CreateNewItem("tea", 200.0, "250g"))
        insertedProduct = when (dbResult){
            is ServiceResult.Success -> dbResult.data!!
            is ServiceResult.Error -> throw NullPointerException()
        }
        val testResultGetItem = ServiceResult.Success(insertedProduct)
        val result = productDao.getItemByName(testGetItem)
        assertEquals(testResultGetItem, result)

    }


    @Test
    fun testUpdateProduct() = testApplication {

        productDao.insertProduct(CreateNewItem("tea", 200.0, "250g"))
        val result = productDao.updateItem(testUpdateItem)
        assertEquals(testResultUpdateItem,result)

    }

    @Test
    fun testDeleteProduct() = testApplication {

        productDao.insertProduct(CreateNewItem("tea", 200.0, "250g"))
        val result = productDao.deleteItem(testDeleteItem)
        assertEquals(testResultDeleteItem,result)

    }

    @Test
    fun testGetAllProducts() = testApplication {

        val dbResult = productDao.insertProduct(CreateNewItem("tea", 200.0, "250g"))
        insertedProduct = when (dbResult){
            is ServiceResult.Success -> dbResult.data!!
            is ServiceResult.Error -> throw NullPointerException()
        }
        val testResultGetAllItems = ServiceResult.Success(listOf(insertedProduct))
        val result = productDao.getAllItems()
        assertEquals(testResultGetAllItems,result)
    }


    @Test
    fun testCheckProductExists() = testApplication {

        productDao.insertProduct(CreateNewItem("tea", 200.0, "250g"))
        val result = productDao.checkItemExists(testItemExists)
        assertEquals(testResultItemExists,result)

    }

}


