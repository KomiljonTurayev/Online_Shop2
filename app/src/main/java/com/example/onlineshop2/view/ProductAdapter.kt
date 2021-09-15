package com.example.onlineshop2.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop2.R
import com.example.onlineshop2.model.CategoryModel
import com.example.onlineshop2.model.ProductModel
import com.example.onlineshop2.screen.productdetail.ProductDetailActivity
import com.example.onlineshop2.utils.Constants
import kotlinx.android.synthetic.main.category_item.view.*
import kotlinx.android.synthetic.main.product_item.view.*

//77 44 11 88

class ProductAdapter(val items: List<ProductModel>): RecyclerView.Adapter<ProductAdapter.ItemHolder>() {

    class ItemHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, item)
            it.context.startActivity(intent)
        }

        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.image).into(holder.itemView.imgProduct)
        holder.itemView.tvName.text = item.name
        holder.itemView.tvPrice.text = item.price
    }
}