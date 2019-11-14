package com.example.opiniaodetudo.pages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opiniaodetudo.domain.Review

class EditReviewViewModel : ViewModel() {
    var data:MutableLiveData<Review> = MutableLiveData()
}
