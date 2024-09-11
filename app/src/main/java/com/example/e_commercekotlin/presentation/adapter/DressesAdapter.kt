import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.data.model.CategoryDetails.CategoryDetailsItem
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.databinding.DressesHorizontalBinding
import com.example.e_commercekotlin.databinding.DressesVerticalBinding
import com.squareup.picasso.Picasso

class DressesAdapter(private val dataList: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        private const val TYPE_HORIZONTAL = 1
        private const val TYPE_VERTICAL = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is String -> TYPE_HORIZONTAL
            is CategoryDetailsItem -> TYPE_VERTICAL
            else -> throw IllegalArgumentException("Invalid data type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HORIZONTAL -> {
                val binding = DressesHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HorizontalViewHolder(binding)
            }
            TYPE_VERTICAL -> {
                val binding = DressesVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> holder.bind(dataList[position] as String)
            is VerticalViewHolder -> holder.bind(dataList[position] as CategoryDetailsItem)
        }
    }

    override fun getItemCount() = dataList.size

    inner class HorizontalViewHolder(private val binding: DressesHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.collectionName.text = text
        }
    }

    inner class VerticalViewHolder(private val binding: DressesVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryDetailsItem) {


            }


        }
    }

