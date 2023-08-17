package com.example.plugins

import com.example.data.database.RedisFactory
import com.example.domain.models.UserSession
import com.example.utils.RedisSessionStorage
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*


fun Application.configureSession(){
    install(Sessions){
        val secretEncryptKey = hex("00112233445566778899aabbccddeeff")
        val secretSignKey = hex("6819b57a326945c1968f45236589")
        val jedis = RedisFactory.createRedisClient()
        cookie<UserSession>("user_session",RedisSessionStorage(jedis)){
            cookie.path = "/item"
            cookie.maxAgeInSeconds = 60000
            transform(SessionTransportTransformerEncrypt(secretEncryptKey,secretSignKey))
        }

    }

}