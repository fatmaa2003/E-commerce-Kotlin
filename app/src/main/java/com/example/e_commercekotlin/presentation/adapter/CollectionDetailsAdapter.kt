import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.CollectionDetailsHorizontalBinding
import com.example.e_commercekotlin.databinding.CollectionDetailsVerticalBinding

class CollectionDetailsAdapter(private val dataList: List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HORIZONTAL = 1
        private const val TYPE_VERTICAL = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is String -> TYPE_HORIZONTAL
            is Item -> TYPE_VERTICAL
            else -> throw IllegalArgumentException("Invalid data type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HORIZONTAL -> {
                val binding = CollectionDetailsHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HorizontalViewHolder(binding)
            }
            TYPE_VERTICAL -> {
                val binding = CollectionDetailsVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> holder.bind(dataList[position] as String)
            is VerticalViewHolder -> holder.bind(dataList[position] as Item)
        }
    }

    override fun getItemCount() = dataList.size

    inner class HorizontalViewHolder(private val binding: CollectionDetailsHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.collectionName.text = text
        }
    }

    inner class VerticalViewHolder(private val binding: CollectionDetailsVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.imageview.setImageResource(item.imageResId)
            binding.name.text = item.name
        }
    }

    data class Item(val imageResId: Int, val name: String)
}
