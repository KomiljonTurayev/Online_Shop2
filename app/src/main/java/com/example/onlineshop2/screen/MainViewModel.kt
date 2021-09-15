package com.example.onlineshop2.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlineshop2.App
import com.example.onlineshop2.api.repository.ShopRepository
import com.example.onlineshop2.db.AppDatabese
import com.example.onlineshop2.model.CategoryModel
import com.example.onlineshop2.model.OfferModel
import com.example.onlineshop2.model.ProductModel
import io.reactivex.annotations.SchedulerSupport.IO
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val repository = ShopRepository()

    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()

    val offersData = MutableLiveData<List<OfferModel>>()
    val categoryData = MutableLiveData<List<CategoryModel>>()
    val productsData = MutableLiveData<List<ProductModel>>()


    fun getOffers() {
        repository.getOffers(error, progress, offersData)
    }

    fun getCategories() {
        repository.getCategories(error, categoryData)
    }

    fun getTopProducts() {
        repository.getTopProducts(error, productsData)
    }

    fun getProductsByCategory(id: Int) {
        repository.getCategoryIdProducts(id, error, productsData)
    }

    fun getProductsByIds(ids: List<Int>) {
        repository.getProductsByIds(ids, error, progress, productsData)

    }

    fun insertAllProducts2Db(items: List<ProductModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabese.getDatabase().getProductDao().insertAll(items)
        }
    }

    fun getAllDBProducts() {
        productsData.value = AppDatabese.getDatabase().getProductDao().getAllProducts()
    }

}

















