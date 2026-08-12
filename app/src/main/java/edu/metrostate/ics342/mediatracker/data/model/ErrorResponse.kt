package edu.metrostate.ics342.mediatracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String,
    val message: String
)

class MediaNotFoundException(message: String) : Exception(message)

/** 409: the item is already favorited. */
class DuplicateFavoriteException : Exception("Already favorited")

/** 409: the item is already in the library. */
class DuplicateLibraryException : Exception("Already in library")

class AlreadyReviewedException : Exception("This media was already reviewed by this user")