package com.example.movieapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.helper.ApiCredentials
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@Entity
@JsonClass(generateAdapter = true)
class Results() {

    var poster_path: String? = null
    var release_date: String? = null
    @PrimaryKey
    var id: Int? = null
    var original_title: String? = null
    var title: String? = null

    fun getImageUrl() = ApiCredentials.imageUrl + poster_path
//    var dateFormatted = formatDate()
//
//    private fun formatDate(): String {
//        return if (release_date.isNullOrBlank()) {
//            ""
//        } else {
//            val dateFormatted: String
//            val DATE_PATTERN = "yyyy-MM-dd"
//            val DATE_PATTERN_CARD = "dd/MM/yyyy"
//            val dateFormartter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
//            val dateCardFormatter = SimpleDateFormat(DATE_PATTERN_CARD, Locale.getDefault())
//
//            dateFormatted = dateFormartter.parse(release_date).let {
//                dateCardFormatter.format(it)
//            }
//            dateFormatted
//        }
//    }

}
