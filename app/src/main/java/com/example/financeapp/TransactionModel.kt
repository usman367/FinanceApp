package com.example.financeapp

import java.io.Serializable

data class TransactionModel (
        val id: Int,
        val amount : String,
        val description : String,
        val date: String,
        val method : String
) : Serializable