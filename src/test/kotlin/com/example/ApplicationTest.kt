package com.example

import com.example.domain.models.CreateNewItem
import com.example.domain.models.ItemWithoutID
import com.example.domain.models.UpdateItemModel
import com.example.domain.models.UserSession
import com.example.utils.BaseResponse
import com.example.utils.Response
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ApplicationTest {

    @Test
    fun `test Login Insert Update Delete Routes with Session Authentication `() = testApplication {
        // Login Session

        val client = createClient {
            install(HttpCookies)
        }
        val postData = UserSession("rina jose","rina2002")
        val serializedPost = Json.encodeToString(postData)
        val response = client.post("item/login"){
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(serializedPost)
        }
        assertEquals("Login successfully", response.bodyAsText())
        assertEquals(HttpStatusCode.OK,response.status)


        //test Insert Route

        val postInsertData = CreateNewItem("coffee powder",10.0,"25g")
        val serializedInsertData = Json.encodeToString(postInsertData)

        val responseInsert = client.post("/item") {
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(serializedInsertData)
        }

        val expectedData = Response("Data inserted successfully")
        val responseSerialized = Json.decodeFromString<Response>(responseInsert.bodyAsText())
        assertEquals(expectedData,responseSerialized)


        //test Update Route

        val updateData = UpdateItemModel("coffee powder",30.0,"25g")
        val serializedUpdateData = Json.encodeToString(updateData)

        val responseUpdate = client.post("/item/update"){
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(serializedUpdateData)
        }

        val expectedUpdateData = Response("updated successfully")
        val serializedUpdateResponse= Json.decodeFromString<Response>(responseUpdate.bodyAsText())
        assertEquals(expectedUpdateData,serializedUpdateResponse)


        //test Delete Route

        val responseDelete = client.delete("/item/pencil")
        val expectedDeleteData = Response("Deleted Successfully")
        val serializedDeleteResponse = Json.decodeFromString<Response>(responseDelete.bodyAsText())
        assertEquals(expectedDeleteData,serializedDeleteResponse)
        assertEquals(HttpStatusCode.OK,responseDelete.status)


    }



    @Test
    fun `test Get Item By Name Route should return item based on name`()= testApplication{
        val response1 = client.post("/item/search/mong dal")
        val expectedData = BaseResponse.SuccessResponse(
            ItemWithoutID(
                "9590eb29-0f22-4ba5-8ba5-3f4a88ea00f8",
                "mong dal",
                100.0,
                "200g",
                "2023-08-17T13:23:42.836924",
                "2023-08-17T13:23:42.843925"),
            "Items for mong dal")
        val responseData = Json.decodeFromString<BaseResponse.SuccessResponse>(response1.bodyAsText())
        assertEquals(expectedData,responseData)
        assertEquals(HttpStatusCode.OK,response1.status)
    }

    @Test
    fun `test GET request should return all items`() = testApplication {
        val response = client.get("/item")
        val expectedList = listOf(
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
            /*
            ,
            ItemWithoutID(
                "66a3265e-9efb-43f3-b2f3-c3e14bff7a71",
                "coffee powder",
                15.0,
                "25g",
                "2023-08-17T18:04:09.632913",
                "2023-08-17T18:54:07.839626"
            )

             */
        )
        val responseSerialized = Json.decodeFromString<List<ItemWithoutID?>>(response.bodyAsText())
        assertEquals(expectedList,responseSerialized)
        assertEquals(HttpStatusCode.OK,response.status)

    }


    @Test
    fun `test Logout Route should return logged out`() = testApplication {
        val response = client.post("/item/logout")
        val expectedData = "Successfully logged out"
        assertEquals(expectedData,response.bodyAsText())
        assertEquals(HttpStatusCode.OK,response.status)
    }



    @Test
    fun testAuthenticatedInsertRoute() = testApplication {

        //test Insert Route

    }


    @Test
    fun testAuthenticatedUpdateRoute() = testApplication {

        //update  test

    }

    @Test
    fun testAuthenticatedDeleteRoute() = testApplication {

        //Delete Test

    }
}

/*
        ServiceResult.Success(
           data = Item(
               id = EntityID(UUID.fromString("8925d518-adda-46d4-9ac7-630e3bb5af9d"),ItemsEntity),
               name = "pen",
               price = 20.0,
               quantity = "2g",
               createdAt = LocalDateTime.parse("2023-08-10T13:05:42.570212"),
               updatedAt = LocalDateTime.parse("2023-08-10T18:10:02.983295")
           )
       )
        */
