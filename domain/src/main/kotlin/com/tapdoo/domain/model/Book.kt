package com.tapdoo.domain.model

data class Book(
    val id: Int,
    val title: String,
    val isbn: String,
    val price: Int,
    val currencySymbol: String,
    val author: String,
)