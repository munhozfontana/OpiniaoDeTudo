package com.example.opiniaodetudo.infra.repositories

import android.content.Context
import com.example.opiniaodetudo.infra.dao.ReviewDatabase
import com.example.opiniaodetudo.domain.Review
import com.example.opiniaodetudo.infra.dao.ReviewDao
import java.util.*


class ReviewRepository {

    private val reviewDao: ReviewDao

    constructor(context: Context) {
        val reviewDatabase = ReviewDatabase.getInstance(context)
        reviewDao = reviewDatabase.reviewDao()
    }

    fun save(name: String, review: String) {
        reviewDao.save(
            Review(
                UUID.randomUUID().toString(),
                name,
                review
            )
        )
    }

    fun listAll(): List<Review> {
        return reviewDao.listAll()
    }

    fun delete(item: Review) {
        reviewDao.delete(item)
    }

    fun update(review:Review) {
        reviewDao.update(review)
    }

}
