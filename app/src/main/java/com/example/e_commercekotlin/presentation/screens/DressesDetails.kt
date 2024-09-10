import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.Util.showToast
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentDressesDetailsBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.CategoryDetailsViewModel

class DressesDetails : Fragment() {

    private var _binding: FragmentDressesDetailsBinding? = null
    private val binding get() = _binding!!
    private val categoryDetailsViewModel: CategoryDetailsViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDressesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeCategoryDetails()
    }

    private fun setupUI() {
        binding.fragmentDressDetailsToolbar.handleToolBarState(leftIconImage = R.drawable.back)

        productAdapter = ProductAdapter()

        binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.verticalRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.verticalRecyclerView.adapter = productAdapter
    }

    private fun observeCategoryDetails() {
        categoryDetailsViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let {

                        val productList = it.first().products.orEmpty()
                        val productCount = productList.size
                        val categoryName = it.first().name

                        binding.titleCategoryDetails.text = categoryName
                        binding.productCountText.text = "Products: $productCount"


                        productAdapter.setProductList(productList)
                    }
                }
                is Resource.Error -> {
                    Log.e("CategoryDetails", "Error: ${resource.message}")
                    binding.progressBar.visibility = View.GONE
                    showToast(message = resource.message.toString())
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
