package com.example.onlineshop2.api

import com.example.onlineshop2.model.*
import com.example.onlineshop2.model.request.GetProductsByIdsRequest
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("get_categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts(): Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id") category_id: Int):Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductsByIds(@Body request: GetProductsByIdsRequest): Observable<BaseResponse<List<ProductModel>>>


}
