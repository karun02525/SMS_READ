package com.payo.main

data class SMS(
    val id: String,
    val address: String,
    val message: String,
    val readState: String,
    val date: String,
    val folderName: String
)

data class DataCreditDebitModel(
    val id: String,
    val credited: String,
    val debited: String,
    val date:String,
    val status:String
)