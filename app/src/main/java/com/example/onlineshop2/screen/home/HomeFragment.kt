package com.example.onlineshop2.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.onlineshop2.R
import com.example.onlineshop2.model.CategoryModel
import com.example.onlineshop2.model.OfferModel
import com.example.onlineshop2.screen.MainViewModel
import com.example.onlineshop2.utils.Constants
import com.example.onlineshop2.view.CategoryAdapter
import com.example.onlineshop2.view.CategoryAdapterCallback
import com.example.onlineshop2.view.ProductAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
//    var images = arrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)

    var offers: List<OfferModel> = emptyList()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe.setOnRefreshListener {
            loadData()
        }

        recyclerCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerProducts.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        })

        viewModel.progress.observe(requireActivity(), Observer {
            swipe.isRefreshing = it
        })

        viewModel.offersData.observe(requireActivity(), Observer {
            carouselView.setImageListener { position, imageView ->
                Glide.with(imageView).load(Constants.HOST_IMAGE + it[position].image)
                    .into(imageView)
            }

            carouselView.pageCount = it.count()
        })

        viewModel.categoryData.observe(requireActivity(), Observer {
            recyclerCategories.adapter = CategoryAdapter(it, object : CategoryAdapterCallback {
                override fun onClickItem(item: CategoryModel) {
                    viewModel.getProductsByCategory(item.id)
                }

            })

        })

        viewModel.productsData.observe(requireActivity(), Observer {
            recyclerProducts.adapter = ProductAdapter(it)

            Thread {
                var i = 1000000000000000000L
                while (i > 0) {
                    i--
                }
            }.start()

        })


        loadData()
    }

    fun loadData() {
        viewModel.getOffers()
        viewModel.getCategories()
//        viewModel.getAllDBProducts()
        viewModel.getTopProducts()

    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()


    }
}