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

    fun save(name: String, review: String, photoPath: String?, thumbnailBytes: ByteArray?): Review {
        val entity = Review(
            UUID.randomUUID().toString(),
            name,
            review,
            photoPath,
            thumbnailBytes
        )
        reviewDao.save(entity)
        return entity
    }

    fun listAll(): List<Review> {
        return reviewDao.listAll()
    }

    fun delete(review: Review) {
        reviewDao.delete(review)
    }

    fun update(id: String, name: String, review: String): Review {
        val entity = Review(id, name, review)
        reviewDao.update(entity)
        return entity
    }

    fun updateLocation(entity: Review, lat: Double, long: Double) {
        entity.latitude = lat
        entity.longitude = long
        reviewDao.update(entity)
    }


}
