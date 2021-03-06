package com.example.onlineshop2.screen.makeorder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlineshop2.MapsActivity
import com.example.onlineshop2.R
import com.example.onlineshop2.model.AddressModel
import com.example.onlineshop2.model.ProductModel
import com.example.onlineshop2.utils.Constants
import kotlinx.android.synthetic.main.activity_make_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {
    var address: AddressModel? = null
    lateinit var items: List<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)

        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        tvTotalAmount.text = items.sumByDouble {
            it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0)
        }.toString()
        edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }


    @Subscribe
    fun onEvent(address: AddressModel) {
        this.address = address
        edAddress.setText("${address.latitude}, ${address.longitude}")

    }

}