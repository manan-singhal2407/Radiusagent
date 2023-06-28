package com.radiusagent.assignment.data.cache.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class UserCache(
    @PrimaryKey
    val userId: String,
    val createdAt: String,
    val userName: String,
    val emailId: String,
    val emailVerified: Boolean,
    val phoneNumber: String,
    val phoneCode: String,
    val referralCode: String,
    val referralTo: String,
    val profileUrl: String,
    val earnedMoney: Double,
    val withdrawalMoney: Double,
    val referralMoney: Double,
    val dateUptoUpdated: String,
    val status: String,
)