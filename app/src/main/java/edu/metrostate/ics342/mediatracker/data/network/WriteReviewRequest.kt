package edu.metrostate.ics342.mediatracker.data.network

import kotlinx.serialization.Serializable
@Serializable
data class WriteReviewRequest(
    val mediaId: Int,
    val rating: Int,
    val reviewText: String?,
    val shareToFeed: Boolean?
)
{

}
