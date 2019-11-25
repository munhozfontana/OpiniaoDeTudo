package com.example.opiniaodetudo.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Review(

    @PrimaryKey
    val id: String,

    val name: String,

    val review: String?,

    @ColumnInfo(name = "photo_path")
    val photoPath: String?,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val thumbnail: ByteArray?,

    @ColumnInfo(typeAffinity = ColumnInfo.REAL)
    var latitude: Double?,

    @ColumnInfo(typeAffinity = ColumnInfo.REAL)
    var longitude: Double?

) : Serializable {
    @Ignore
    constructor(
        id: String,
        name: String,
        review: String
    ) : this(
        id,
        name,
        review,
        null,
        null
    )

    @Ignore
    constructor(
        id: String,
        name: String,
        review: String,
        photoPath: String?,
        thumbnail: ByteArray?
    ) : this(id, name, review, photoPath, thumbnail, null, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Review

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

