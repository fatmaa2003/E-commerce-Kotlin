package com.example.e_commercekotlin.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedCartViewModel : ViewModel() {
    private val _subtotal = MutableLiveData<Double>()
    val subtotal: LiveData<Double> = _subtotal

    fun updateSubtotal(newSubtotal: Double) {
        _subtotal.value = newSubtotal
    }
}
