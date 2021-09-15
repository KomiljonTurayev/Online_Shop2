package com.example.onlineshop2.screen.card

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop2.R
import com.example.onlineshop2.model.ProductModel
import com.example.onlineshop2.screen.MainViewModel
import com.example.onlineshop2.screen.makeorder.MakeOrderActivity
import com.example.onlineshop2.utils.Constants
import com.example.onlineshop2.utils.PrefUtils
import com.example.onlineshop2.view.CardAdapter
import kotlinx.android.synthetic.main.fragment_card.*
import java.io.Serializable

class CardFragment : Fragment() {
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.progress.observe(requireActivity(), Observer {
            swipe.isRefreshing = it
        })

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.productsData.observe(requireActivity(), Observer {
            recycler.adapter = CardAdapter(it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        swipe.setOnRefreshListener {
            loadData()
        }

        btnMakeOrder?.setOnClickListener {
            val intent = Intent(requireActivity(), MakeOrderActivity::class.java)
            intent.putExtra(
                Constants.EXTRA_DATA,
                (viewModel.productsData.value ?: emptyArray<ProductModel>()) as Serializable
            )

            startActivity(intent)
        }

        loadData()
    }

    fun loadData() {
        viewModel.getProductsByIds(PrefUtils.getCartList().map { it.product_id })
    }

    companion object {

        @JvmStatic
        fun newInstance(

        ) =
            CardFragment()
    }
}