package com.example.e_commercekotlin.presentation.screens

import Address
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.ShippingAddress
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.data.model.CartItem
import com.example.e_commercekotlin.databinding.FragmentShippingAddressBinding
import com.example.e_commercekotlin.presentation.adapter.AddressAdapter
import com.example.e_commercekotlin.presentation.viewmodels.SharedCartViewModel


class ShippingAddressFragment : Fragment() {

    private lateinit var adapter: AddressAdapter
    private lateinit var addressList: MutableList<Address>
    private var _binding: FragmentShippingAddressBinding? = null
    private val binding get() = _binding!!
    private val sharedCartViewModel: SharedCartViewModel by activityViewModels()

    private val args: ShippingAddressFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShippingAddressBinding.inflate(inflater, container, false)
        activity?.setBottomNavVisibility(visible = false)
        val view = binding.root

        val cartItem: CartItem? = args.cartData
        if (cartItem == null) {
            Log.e("ShippingAddressFragment", "Cart data is null!")
            return view
        }

        val subtotal = cartItem.totalCartPrice

        addressList = ShippingAddress.addresses
        adapter = AddressAdapter(addressList)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        binding.totalPrice.text = "$${String.format("%.2f", subtotal)}"

        val includeLayout = view.findViewById<View>(R.id.continue_to_purchase_button_layout)
        val continueToPurchaseButton = includeLayout.findViewById<TextView>(R.id.button_tv)
        continueToPurchaseButton.setOnClickListener {
            val action = ShippingAddressFragmentDirections.actionShippingAddressFragment2ToPurchase(cartItem)
            findNavController().navigate(action)
        }

        val addAddressLayout: LinearLayout = view.findViewById(R.id.add_address_layout)
        addAddressLayout.setOnClickListener {
            showAddAddressDialog()
        }

        addAddressLayout.background = ContextCompat.getDrawable(requireContext(), R.drawable.show_more_less_background)
        val addAddressTextView: TextView = addAddressLayout.findViewById(R.id.button_tv)
        addAddressTextView.text = "Add Address"
        addAddressTextView.setTextColor(getResources().getColor(R.color.text_on_label))
        addAddressTextView.typeface = ResourcesCompat.getFont(requireContext(), R.font.semibold)


        continueToPurchaseButton.text= "Continue"
        continueToPurchaseButton.setTextColor(Color.WHITE)
        continueToPurchaseButton.typeface = ResourcesCompat.getFont(requireContext(), R.font.semibold)

        return view
    }

    private fun showAddAddressDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_address, null)
        val addressEditText: EditText = dialogView.findViewById(R.id.edit_text_address)
        val addressNameEditText: EditText = dialogView.findViewById(R.id.edit_text_address_name)
        builder.setView(dialogView)
            .setTitle("Add New Address")
            .setPositiveButton("Add") { _, _ ->
                val newAddressText = addressEditText.text.toString().trim()
                val addressName = addressNameEditText.text.toString().trim()
                if (newAddressText.isNotEmpty()) {
                    val newAddress = Address(
                        addressName = addressName,
                        address = newAddressText,
                        arrivalEstimate = "Arrival est: 2 days",
                        shippingCost = "$5 Shipping"
                    )
                    ShippingAddress.addresses.add(newAddress)
                    adapter.setAddressList(ShippingAddress.addresses)
                    binding.recyclerView.scrollToPosition(addressList.size - 1)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedCartViewModel.subtotal.observe(viewLifecycleOwner) { updatedSubtotal ->
            binding.totalPrice.text = "$${String.format("%.2f", updatedSubtotal)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}