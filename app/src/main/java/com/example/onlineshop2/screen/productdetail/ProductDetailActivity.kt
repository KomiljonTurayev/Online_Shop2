package com.example.onlineshop2.screen.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.onlineshop2.R
import com.example.onlineshop2.model.ProductModel
import com.example.onlineshop2.utils.Constants
import com.example.onlineshop2.utils.PrefUtils
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        cardViewBack.setOnClickListener {
            finish()
        }

        cardViewFavorite.setOnClickListener {
            PrefUtils.setFavourite(item)

            if (PrefUtils.checkFavourite(item)){
                imgFavorite.setImageResource(R.drawable.ic_heart)
            }else{
                imgFavorite.setImageResource(R.drawable.ic_love)
            }

        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        tvTitle.text = item.name
        tvProductName.text = item.name
        tvProductPrice.text = item.price

        if (PrefUtils.getCartList(item) > 0){
            btnAdd2Cart.visibility = View.GONE
        }

        if (PrefUtils.checkFavourite(item)){
            imgFavorite.setImageResource(R.drawable.ic_heart)
        }else{
            imgFavorite.setImageResource(R.drawable.ic_love)
        }

        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(imgProduct)
//
        btnAdd2Cart.setOnClickListener {
            item.cartCount = 1
            PrefUtils.setCard(item)
            Toast.makeText(this, "Product added to cart!", Toast.LENGTH_LONG).show()
            finish()
        }

    }
}