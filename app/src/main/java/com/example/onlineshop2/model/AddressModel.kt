package com.example.onlineshop2.model

import java.io.Serializable

data class AddressModel
    (
    val address: String,
    val latitude: String,
    val longitude: String
) : Serializable
