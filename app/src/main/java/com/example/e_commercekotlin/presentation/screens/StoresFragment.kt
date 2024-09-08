package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.ApiService
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.data.model.StoreImages
import com.example.e_commercekotlin.presentation.adapter.CategoryAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.StoreImagesAdapter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoresFragment : Fragment() {

    private lateinit var itemAdapter: ProductAdapter
    private lateinit var storeImagesAdapter: StoreImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val itemRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val storeImagesRecyclerView: RecyclerView = view.findViewById(R.id.storeImagesRv)

        itemRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        storeImagesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        itemAdapter = ProductAdapter()
        storeImagesAdapter = StoreImagesAdapter()

        itemRecyclerView.adapter = itemAdapter
        storeImagesRecyclerView.adapter = storeImagesAdapter


//        lifecycleScope.launch {
//            try {
//                val storeImages = getStoreImagesFromApi()
//                storeImagesAdapter.setStoreImagesList(storeImages)
//
//                val items = getItemsFromApi()
//                itemAdapter.setProductList(items)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Toast.makeText(requireActivity(), "Failed to load data", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private suspend fun getStoreImagesFromApi(): List<StoreImages> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)
        return api.getStoreImages()
    }


//    private suspend fun getItemsFromApi(): List<ProductDetailsDto.Product> {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://api.escuelajs.co/api/v1/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val api = retrofit.create(ApiService::class.java)
//        return api.getItems()
//    }
}