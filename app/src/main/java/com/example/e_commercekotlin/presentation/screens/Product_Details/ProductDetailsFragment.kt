package com.example.e_commercekotlin.presentation.screens.Product_Details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
    private lateinit var productId: String
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

        //viewModel.fetchData(productId)



        binding.addToCartButton.root.setOnClickListener {
            val quantity = 1
            val productId = ProductDetailsFragmentArgs.fromBundle(requireArguments()).productId.toLong()
            viewModel.addToCart(productId, quantity)
        }

        observeData()
        observeAddToCartStatus()

        val productId = ProductDetailsFragmentArgs.fromBundle(requireArguments()).productId

        viewModel.fetchProductDetails(productId.toLong())
        observeData()

        binding.apply {
            tvTagsHeader1.setOnClickListener { toggleTagsVisibility(llTagsContent1, tvTagsHeader1, 1) }
            tvTagsHeader2.setOnClickListener { toggleTagsVisibility(llTagsContent2, tvTagsHeader2, 2) }
            tvTagsHeader3.setOnClickListener { toggleTagsVisibility(llTagsContent3, tvTagsHeader3, 3) }

            productImagesAdapter = ProductImagesAdapter().apply {
                setProductImages(productImages)
            }
            productAdapter = ProductAdapter()

            clothes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            clothes.adapter = productImagesAdapter

            completeOutfit.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            completeOutfit.adapter = productAdapter
        }
    }

    private fun observeAddToCartStatus() {
        viewModel.addToCartStatus.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Item added to cart!", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Error adding item to cart", Toast.LENGTH_SHORT).show()
                }
            }
        })
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
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    val productDetails = resource.data?.products?.first()
                    binding.progressBar.visibility = View.GONE
                    val image = productDetails?.mainImageUrl.orEmpty()
                    val shopName = productDetails?.name.orEmpty()
                    val description = productDetails?.description.orEmpty()
                    val price = productDetails?.price?.toString().orEmpty()
                    handleUiSuccessState(shopName, description, price, image)
                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun handleUiSuccessState(shopName: String, description: String, price: String, image: String) {
        binding.shopName.text = shopName
        binding.descriptiontext.text = description
        binding.price.text = "$$price"

        Glide.with(this).load(image).into(binding.shopImage.image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
