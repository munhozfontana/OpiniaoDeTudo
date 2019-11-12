package com.example.opiniaodetudo.infra.dao

import androidx.room.*
import com.example.opiniaodetudo.domain.Review
import com.example.opiniaodetudo.infra.dao.ReviewTableInfo

@Dao
interface ReviewDao {
    @Insert
    fun save(review:Review)

    @Query("SELECT * from ${ReviewTableInfo.TABLE_NAME}")
    fun listAll():List<Review>

    @Delete
    fun delete(item: Review)

    @Update
    fun update(review: Review)

}