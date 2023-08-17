package com.example.plugins

import com.example.data.dao.ProductDao
import com.example.data.dao.ProductDaoImpl
import com.example.domain.services.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin


fun Application.configureKoin(){
   install(Koin){
      modules(myModule)
   }
}

val myModule = module {
    single<ProductDao> { ProductDaoImpl()  }
    single { CheckValidQuantity() }
    single { GetItemByName(get()) }
    single { InsertNewItem(get(),get())}

    single { UpdateTheItem(get()) }
    single { DeleteItemByName(get()) }
}