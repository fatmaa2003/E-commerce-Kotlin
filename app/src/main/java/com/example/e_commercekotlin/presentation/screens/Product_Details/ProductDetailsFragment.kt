package com.example.e_commercekotlin.presentation.screens.Product_Details

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.data.model.toProductItem
import com.example.e_commercekotlin.databinding.FragmentProductDetailsBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.model.Featured
import com.example.e_commercekotlin.presentation.adapter.ProductImagesAdapter

class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productImagesAdapter: ProductImagesAdapter
    private lateinit var productAdapter: ProductAdapter
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

        productId = ProductDetailsFragmentArgs.fromBundle(requireArguments()).productId.toString()

        binding.addToCartButton.root.setOnClickListener {
            val quantity = 1
            viewModel.addToCart(productId.toLong(), quantity)
        }
        binding.productDetailsFragmentToolbar.handleToolBarState(leftIconVisibility = false)
        observeData()
        observeAddToCartStatus()

        viewModel.fetchProductDetails(productId.toLong())
        observeData()
        observeProducts()

        val sizeTextView: TextView = view?.findViewById(R.id.size) ?: return
        sizeTextView.setOnClickListener {
            showSizeDialog()
        }
        val colorTextView: TextView = view?.findViewById(R.id.color) ?: return
        colorTextView.setOnClickListener {
            showColorDialog()
        }


        binding.apply {
            tvTagsHeader1.setOnClickListener { toggleTagsVisibility(llTagsContent1, tvTagsHeader1, 1) }
            tvTagsHeader2.setOnClickListener { toggleTagsVisibility(llTagsContent2, tvTagsHeader2, 2) }
            tvTagsHeader3.setOnClickListener { toggleTagsVisibility(llTagsContent3, tvTagsHeader3, 3) }

            productAdapter = ProductAdapter()

            productImagesAdapter = ProductImagesAdapter()
            clothes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            clothes.adapter = productImagesAdapter

            completeOutfit.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            completeOutfit.adapter = productAdapter
        }
    }

    private fun observeProducts() {
        viewModel.allProducts.observe(viewLifecycleOwner){ resources ->
            when(resources){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Log.e("TAG123", "observeProducts: " + resources.data.orEmpty())
                    resources.data?.map { it.toProductItem() }
                        ?.let { productAdapter.setProductList(it) }
                }
                is Resource.Error -> {}
            }

        }
    }

    private fun showSizeDialog() {
        val sizes = arrayOf("small", "medium", "large" , "x large")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Size")
        builder.setItems(sizes) { dialog, which ->
            // Handle the size selection
            val selectedSize = sizes[which]
            val sizeTextView: TextView = view?.findViewById(R.id.size) ?: return@setItems
            sizeTextView.text = selectedSize
        }
        builder.create().show()
    }

    private fun showColorDialog() {
        val colors = arrayOf("Black", "Pink", "White", "Red", "Orange", "Green", "Yellow" , "Blue")

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Color")

        builder.setItems(colors) { dialog, which ->
            val selectedColor = colors[which]
            val colorImageView: ImageView = view?.findViewById(R.id.color_image) ?: return@setItems
            when (selectedColor.lowercase()) {
                "black" -> colorImageView.setColorFilter(Color.BLACK)
                "pink" -> colorImageView.setColorFilter(Color.parseColor("#FFC0CB")) // Pink
                "white" -> colorImageView.setColorFilter(Color.WHITE)
                "red" -> colorImageView.setColorFilter(Color.RED)
                "orange" -> colorImageView.setColorFilter(Color.parseColor("#FFA500")) // Orange
                "green" -> colorImageView.setColorFilter(Color.GREEN)
                "yellow" -> colorImageView.setColorFilter(Color.YELLOW)
                 "blue"   -> colorImageView.setColorFilter(Color.BLUE)
            }
            val colorTextView: TextView = view?.findViewById(R.id.color) ?: return@setItems
            colorTextView.text = selectedColor
        }

        builder.create().show()
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
        viewModel.data.observe(viewLifecycleOwner) { resource ->
            when (resource) {

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    val productDetails = resource.data?.products?.first()
                    binding.progressBar.visibility = View.GONE
                    val image = productDetails?.mainImageUrl.orEmpty()
                    val shopName = productDetails?.name.orEmpty()
                    val description = productDetails?.description.orEmpty()
                    val price = productDetails?.price?.toString().orEmpty()
                    val imageList = productDetails?.imageUrls
                    handleUiSuccessState(shopName, description, price, image, imageList.orEmpty())
                    productImagesAdapter.setProductImages(imageList)
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }


    private fun handleUiSuccessState(shopName : String ,description:String, price:String, image:String,imageRecycler:List<String>) {
        binding.shopName.text = shopName
        binding.descriptiontext.text= description
        binding.price.text = "$$price"
        // with -> context
        // load -> image url
        // into -> actual image view
        Glide.with(this).load(image).into(binding.shopImage.image)
        productImagesAdapter.setProductImages(imageRecycler)

    }

    private fun showCartDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.cart_popup, null)
        val cartSizeTextView = dialogView.findViewById<TextView>(R.id.cartSizeTextView)
        val goToCartButton = dialogView.findViewById<Button>(R.id.goToCartButton)

        viewModel.cartSize.observe(viewLifecycleOwner) { cartSize ->
            Log.d("CartSize", "Observed cart size: ${cartSize.data}")
            cartSizeTextView.text = " ${cartSize.data}"
        }


        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        goToCartButton.setOnClickListener {
            alertDialog.dismiss()
            findNavController().navigate(R.id.action_product_details_to_cart)
        }

        alertDialog.show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}