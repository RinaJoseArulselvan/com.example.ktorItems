package com.example.routes

import com.example.data.dao.ProductDaoImpl
import com.example.domain.models.CreateNewItem
import com.example.domain.models.Credentials
import com.example.domain.models.UpdateItemModel
import com.example.domain.models.UserSession
import com.example.domain.services.DeleteItemByName
import com.example.domain.services.GetItemByName
import com.example.domain.services.InsertNewItem
import com.example.domain.services.UpdateTheItem
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject

fun Route.productRoutes(){

    val insertNewItem:InsertNewItem by inject()

    val productDao:ProductDaoImpl by inject()

    val getItemByName: GetItemByName by inject()

    val updateTheItem: UpdateTheItem by inject()

    val deleteItem : DeleteItemByName by inject()

    route("/item"){
        post("/login") {
            val request = call.receive<Credentials>()
            call.sessions.set(UserSession(request.username,request.password))
            call.respond("Login successfully")

        }
        authenticate("auth_sessions"){
            post {
                val result = call.sessions.get<UserSession>()
                if (result != null) {
                    val requestItem = call.receive<CreateNewItem>()
                    insertNewItem(requestItem)
                    call.respond(result.username)
                }
            }

            post("/update") {
                val request = call.receive<UpdateItemModel>()
                val response = updateTheItem(request)
                call.respondText(response.item.toString())
            }

            delete("/{name}") {
                val request = call.parameters["name"]?:return@delete call.respond("Invalid name")
                val response= deleteItem(request)
                call.respond(response)
            }
        }

        post("/search/{name}") {
            val request = call.parameters["name"]?:return@post call.respond("Invalid name")
            val response = getItemByName(request)
            call.respondText(response.item.toString())
        }

       get {
           val items = productDao.getAllItems()
           call.respond(items)
       }

        post("/logout"){
            call.sessions.clear<UserSession>()
            call.respond("Successfully logged out")
            call.respondRedirect("/login")
        }
    }

}