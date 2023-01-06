package com.proxglobal.mieumeo.model

data class User(
    var name: String = "Ngoc",
    var cartLe: List<Le> = listOf(),
    var coin: Int = 0
)
