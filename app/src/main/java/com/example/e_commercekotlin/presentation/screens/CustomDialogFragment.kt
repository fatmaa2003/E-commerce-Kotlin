package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.CustomDialogBoxBinding

class CustomDialogFragment(private val onActionClick: (() -> Unit)? = null) : DialogFragment() {

    private var _binding: CustomDialogBoxBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CustomDialogBoxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getInt("productId")
        val productName = arguments?.getString("product_name")
        val productImage = arguments?.getString("product_image")
        val sourceFragment = arguments?.getString("source_fragment")

        binding.productName.text = productName
        Glide.with(this).load(productImage).into(binding.itemImage)

        binding.bottomLinearLayout.setOnClickListener {
            onActionClick?.invoke() // Execute the passed action
            dismiss()
        }

    }

    private fun closeDialog (){
        dialog?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}