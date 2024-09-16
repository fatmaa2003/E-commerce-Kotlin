package com.example.e_commercekotlin.data.model

data class PurchaseResponse(
    val invoiceNumber: String,
    val status: String,
    val message: String
)
