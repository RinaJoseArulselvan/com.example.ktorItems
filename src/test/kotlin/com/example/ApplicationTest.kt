package com.example

import com.example.data.entities.ItemsEntity
import com.example.domain.models.Item
import com.example.domain.models.UserSession
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.example.plugins.*
import com.example.utils.ServiceResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime
import java.util.*

class ApplicationTest {
    @Test
    fun testLoginRoute() = testApplication {
        val postData = UserSession("rinajose","rina2002")
        val serializedPost = Json.encodeToString(postData)
        val response = client.post("item/login"){
            headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
            setBody(serializedPost)
        }
        assertEquals("Login successfully", response.bodyAsText())
        assertEquals(HttpStatusCode.OK,response.status)
    }

    @Test
    fun testAuthenticateInsertRoute() = testApplication {



    }


    @Test
    fun testUpdateRoute() = testApplication {



    }


    @Test
    fun testProduct()= testApplication{
            val post = "Success(" +
                    "data=Item(" +
                    "id=8925d518-adda-46d4-9ac7-630e3bb5af9d, " +
                    "name=pen, price=20.0, " +
                    "quantity=2g, " +
                    "createdAt=2023-08-10T13:05:42.570212, " +
                    "updatedAt=2023-08-10T18:10:02.983295))"

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
       val response = client.post("item/search/pen")
        assertEquals(post,response.bodyAsText())
        assertEquals(HttpStatusCode.OK, response.status)
    }


    @Test
    fun testLogoutRoute() = testApplication {



    }
}
