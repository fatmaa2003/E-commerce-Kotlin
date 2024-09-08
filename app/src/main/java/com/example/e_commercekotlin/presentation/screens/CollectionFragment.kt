package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.ApiService
import com.example.e_commercekotlin.data.model.Collection
import com.example.e_commercekotlin.data.model.FrequentlyVisitedItems
import com.example.e_commercekotlin.presentation.adapter.CollectionPageAdapter
import com.example.e_commercekotlin.presentation.adapter.FrequentlyVisitedAdapter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CollectionFragment : Fragment() {

    private lateinit var collectionPageAdapter: CollectionPageAdapter
    private lateinit var frequentlyVisitedAdapter: FrequentlyVisitedAdapter
    private lateinit var toggleTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemRecyclerView: RecyclerView = view.findViewById(R.id.collection_recycler_view)
        val freqVisitedRecyclerView: RecyclerView = view.findViewById(R.id.frequently_visited_recycler_view)
        toggleTextView = view.findViewById(R.id.toggle_text_view)

        itemRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        freqVisitedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        collectionPageAdapter = CollectionPageAdapter()
        frequentlyVisitedAdapter = FrequentlyVisitedAdapter()

        itemRecyclerView.adapter = collectionPageAdapter
        freqVisitedRecyclerView.adapter = frequentlyVisitedAdapter

        lifecycleScope.launch {
            try {
                val collection = getCollectionsFromApi()
                collectionPageAdapter.setCollectionList(collection)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        }



        // Toggle text view logic
        toggleTextView.setOnClickListener {
            collectionPageAdapter.toggleShowItems()
            updateToggleTextView()
        }
    }

    private fun updateToggleTextView() {
        if (collectionPageAdapter.isShowingAllItems()) {
            toggleTextView.text = "Show Less"
            toggleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.showless, 0, 0, 0)
        } else {
            toggleTextView.text = "Show More"
            toggleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.showmore, 0, 0, 0)
        }
    }

    private suspend fun getCollectionsFromApi(): List<Collection> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://freetestapi.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)
        return api.getCollections()
    }


}
