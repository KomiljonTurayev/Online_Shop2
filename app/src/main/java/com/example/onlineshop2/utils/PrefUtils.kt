package com.example.onlineshop2.utils

import com.example.onlineshop2.model.CardModel
import com.example.onlineshop2.model.ProductModel
import com.orhanobut.hawk.Hawk

object PrefUtils {
    const val PREF_FAVOURITES = "pref_favourites"
    const val PREF_CARD = "pref_cart"

    fun setFavourite(item: ProductModel) {
        val items = Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null) {
            items.remove(item.id)
        } else {
            items.add(item.id)
        }

        Hawk.put(PREF_FAVOURITES, items)

    }

    fun getFavouriteList(): ArrayList<Int> {
        return Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())
    }

    fun checkFavourite(item: ProductModel): Boolean {
        val items = Hawk.get(PREF_FAVOURITES, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull<Int>() != null
    }

    fun setCard(item: ProductModel) {
        val items = Hawk.get<ArrayList<CardModel>>(PREF_CARD, arrayListOf<CardModel>())
        val cart = items.filter { it.product_id == item.id }.firstOrNull()
        if (cart != null) {
            if (item.cartCount > 0) {
                cart.count = item.cartCount
            } else {
                items.remove(cart)
            }
        } else {
            val newCart = CardModel(item.id, item.cartCount)
            items.add(newCart)
        }

        Hawk.put(PREF_CARD, items)

    }

    fun getCartList(): ArrayList<CardModel> {
        return Hawk.get(PREF_CARD, arrayListOf<CardModel>())


    }

    fun getCartList(item: ProductModel): Int {
        val items = Hawk.get<ArrayList<CardModel>>(PREF_CARD, arrayListOf<CardModel>())
        return items.filter { it.product_id == item.id }.firstOrNull()?.count ?: 0

    }

}