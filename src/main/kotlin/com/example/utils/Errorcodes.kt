package com.example.utils

enum class ErrorCodes(val message : String){
    INVALID_ITEM("Check the json values."),
    DATABASE_ERROR("Unknown database error .try again after some time"),
    UNKNOWN_ERROR("Some Unknown error."),
    INVALID_QUANTITY("Invalid quantity . check the quantity"),
    INVALID_PRICE("Please check the price."),
    ITEM_EXISTS("This item already exists . Please check the json values")
}