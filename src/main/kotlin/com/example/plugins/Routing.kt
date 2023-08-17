package com.example.plugins

import com.example.routes.productRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*


fun Application.configureRouting() {
   routing {
       productRoutes()
   }
}
