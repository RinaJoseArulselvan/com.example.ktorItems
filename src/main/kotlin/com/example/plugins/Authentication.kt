package com.example.plugins

import com.example.domain.models.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*

fun Application.configureAuthentication(){

    install(Authentication){
        session<UserSession>("auth_sessions") {
            validate {session->
                if (session.username.isNotEmpty() and session.password.isNotEmpty()){
                    session
                }else{
                    null
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Session is not valid or has expired")
            }

        }
    }
}