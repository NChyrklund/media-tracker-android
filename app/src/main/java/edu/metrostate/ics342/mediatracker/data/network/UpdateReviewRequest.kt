package edu.metrostate.ics342.mediatracker.data.network

import kotlinx.serialization.Serializable

@Serializable
data class UpdateReviewRequest (
    val rating: Int,
    val reviewText: String
) {
}