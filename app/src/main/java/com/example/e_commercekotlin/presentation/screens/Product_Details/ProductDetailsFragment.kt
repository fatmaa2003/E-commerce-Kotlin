package com.example.e_commercekotlin.presentation.screens.Product_Details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.databinding.FragmentProductDetailsBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.model.Featured
import com.example.e_commercekotlin.presentation.screens.ProductImage
import com.example.e_commercekotlin.presentation.screens.ProductImagesAdapter

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productImagesAdapter: ProductImagesAdapter
    private val productImages = mutableListOf<ProductImage>()
    private lateinit var productAdapter: ProductAdapter
    private val product = mutableListOf<ProductDetailsDto.Product>()
    private val features = mutableListOf<Featured>()
    private var tagsVisible1 = false
    private var tagsVisible2 = false
    private var tagsVisible3 = false
    private lateinit var productId : String
    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        productId =

        viewModel.fetchData(productId)


        binding.apply {
            tvTagsHeader1.setOnClickListener { toggleTagsVisibility(llTagsContent1, tvTagsHeader1, 1) }
            tvTagsHeader2.setOnClickListener { toggleTagsVisibility(llTagsContent2, tvTagsHeader2, 2) }
            tvTagsHeader3.setOnClickListener { toggleTagsVisibility(llTagsContent3, tvTagsHeader3, 3) }

            productImagesAdapter = ProductImagesAdapter().apply {
                setProductImages(productImages)
            }
            productAdapter = ProductAdapter().apply {
                setProductList(product)
            }

            clothes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            clothes.adapter = productImagesAdapter

            completeOutfit.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            completeOutfit.adapter = productAdapter
        }

        observeData()
    }

    private fun toggleTagsVisibility(tagsContent: LinearLayout, tagsHeader: TextView, section: Int) {
        val isVisible = when (section) {
            1 -> tagsVisible1.also { tagsVisible1 = !it }
            2 -> tagsVisible2.also { tagsVisible2 = !it }
            3 -> tagsVisible3.also { tagsVisible3 = !it }
            else -> false
        }

        tagsContent.visibility = if (isVisible) View.GONE else View.VISIBLE
        val drawable = if (isVisible) R.drawable.baseline_expand_more_24 else R.drawable.baseline_expand_less_24
        tagsHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    }

    private fun observeData() {
        viewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    binding.progressBar.visibility = View.GONE
                    binding.shopName.text = resource.data[0].products?.get(0)?.name
                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
