package com.example.data.database

import redis.clients.jedis.Jedis

object RedisFactory {
    fun createRedisClient(): Jedis {
        val host = "127.0.0.1"
        val port = 6379
        return Jedis(host,port)
    }

}