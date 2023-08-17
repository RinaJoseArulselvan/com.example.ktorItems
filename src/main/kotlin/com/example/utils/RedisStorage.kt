package com.example.utils
import io.ktor.server.sessions.*
import redis.clients.jedis.Jedis

class RedisSessionStorage(private val redisClient: Jedis) : SessionStorage {
         override suspend fun invalidate(id: String) {
             redisClient[id]?.let {
                    redisClient.del(id)
                 }
             }

         override suspend fun read(id: String): String {
             return redisClient[id] ?: throw NoSuchElementException("Session $id not found")
             }

         override suspend fun write(id: String, value: String) {
             redisClient[id] = value
             redisClient.expire(id, 60)
             }

}