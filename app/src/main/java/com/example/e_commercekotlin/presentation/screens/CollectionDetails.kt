import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FragmentCollectionDetailsBinding

class CollectionDetails : Fragment() {

    private var _binding: FragmentCollectionDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // example lhad ma ne3ml integrat ma3 api
        val horizontalData = listOf("clothes 1", "Item 2", "Item 3")
        val verticalData = listOf(
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 1"),
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 2"),
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 3"),
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 4"),
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 5"),
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 6"),
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 7"),
            CollectionDetailsAdapter.Item(R.drawable.cat1, "Name 8")
        )
        binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.horizontalRecyclerView.adapter = CollectionDetailsAdapter(horizontalData)
        binding.verticalRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.verticalRecyclerView.adapter = CollectionDetailsAdapter(verticalData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
