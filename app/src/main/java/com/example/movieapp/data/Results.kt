package com.example.movieapp.data

import com.example.movieapp.ApiCredentials
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class Results(
    val poster_path: String?,
    val release_date: String?,
    val id: Int,
    val original_title: String?,
    val title: String?
){
    fun getImageUrl() = ApiCredentials.imageUrl + poster_path
    val dateFormatted = formatDate(release_date)

    private fun formatDate(date: String?): String? {
        val dateFormatted: String?
        val DATE_PATTERN = "yyyy-MM-dd"
        val DATE_PATTERN_CARD = "dd/MM/yyyy"
        val dateFormartter = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        val dateCardFormatter = SimpleDateFormat(DATE_PATTERN_CARD, Locale.getDefault())

        dateFormatted = dateFormartter.parse(date)?.let { date ->
            dateCardFormatter.format(date)
        }

        return dateFormatted

    }
}
