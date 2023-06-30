package com.radiusagent.assignment.data.cache.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity
@JsonClass(generateAdapter = true)
data class FacilityCache(
    @PrimaryKey
    val id: String = "default",
    @TypeConverters(FacilityListConverter::class)
    val facilities: List<Facility>
) {
    data class Facility(
        val facilityId: String,
        val name: String,
        val option: List<Option>
    ) {
        data class Option(
            val name: String,
            val icon: String,
            val id: String,
            val disabled: List<String>
        )
    }

    class FacilityListConverter {
        private val moshiAdapter: JsonAdapter<List<Facility>> by lazy {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            moshi.adapter(Types.newParameterizedType(List::class.java, Facility::class.java))
        }

        @TypeConverter
        fun fromListToJson(optionList: List<Facility>): String {
            return moshiAdapter.toJson(optionList)
        }

        @TypeConverter
        fun fromJsonToList(optionListString: String): List<Facility> {
            return moshiAdapter.fromJson(optionListString) ?: emptyList()
        }
    }
}