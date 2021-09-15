package com.example.onlineshop2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop2.R
import com.example.onlineshop2.model.CardModel
import com.example.onlineshop2.model.ProductModel
import com.example.onlineshop2.utils.Constants
import kotlinx.android.synthetic.main.cart_item.view.*

class CardAdapter(val items: List<ProductModel>) : RecyclerView.Adapter<CardAdapter.ItemHolder>() {


    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.itemView.tvPrice.text = item.price
        holder.itemView.tvName.text = item.name
        Glide.with(holder.itemView).load(Constants.HOST_IMAGE + item.image)
            .into(holder.itemView.imgProduct)

        holder.itemView.tvCount.text = item.cartCount.toString()

    }


}