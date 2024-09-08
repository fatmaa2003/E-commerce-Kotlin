import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.databinding.FragmentCollectionDetailsBinding
import com.example.e_commercekotlin.databinding.FragmentDressesDetailsBinding

class DressesDetails : Fragment() {

    private var _binding: FragmentDressesDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDressesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentDressDetailsToolbar.handleToolBarState(leftIconImage = R.drawable.back)
        val horizontalData = listOf("All", "T-shirts","Jackets","Sweater","Sweater","Sweater","Sweater")
        val verticalData = listOf(
            DressesAdapter.Item(R.drawable.cat1, "Prouduct 1", "$22.22"),
            DressesAdapter.Item(R.drawable.cat1, "Prouduct 2", "$89.28"),
            DressesAdapter.Item(R.drawable.cat1, "Prouduct 3", "$79.69"),
            DressesAdapter.Item(R.drawable.cat1, "Prouduct 4", "$69.51"),
            DressesAdapter.Item(R.drawable.cat1, "Prouduct 5", "$22.22")
        )
        binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.horizontalRecyclerView.adapter = DressesAdapter(horizontalData)
        binding.verticalRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.verticalRecyclerView.adapter = DressesAdapter(verticalData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}