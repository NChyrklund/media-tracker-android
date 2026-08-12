package edu.metrostate.ics342.mediatracker.ui.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface WriteReviewUiState {
    data object Loading : WriteReviewUiState
    data object NotFound : WriteReviewUiState
    data class Error(val message: String) : WriteReviewUiState
    data class Success(
        val detail: MediaDetail
    ) : WriteReviewUiState
}

class WriteReviewViewModel @JvmOverloads constructor(
    application: Application,
    private val repository: DefaultMediaRepository =
        DefaultMediaRepository(DefaultSessionRepository(application))
) : AndroidViewModel(application) {
    val reviewCharLimit = 500
    private val _uiState = MutableStateFlow<WriteReviewUiState>(WriteReviewUiState.Loading)
    val uiState: StateFlow<WriteReviewUiState> = _uiState.asStateFlow()

    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int> = _rating

    private val _actionError = MutableStateFlow<String?>(null)
    val actionError: StateFlow<String?> = _actionError.asStateFlow()

    private val _reviewText = MutableStateFlow("")
    val reviewText: StateFlow<String> = _reviewText.asStateFlow()

    private val _reviewPosted = MutableStateFlow(false)
    val reviewPosted: StateFlow<Boolean> = _reviewPosted.asStateFlow()

    private val _shareToFeed = MutableStateFlow<Boolean>(true)
    val shareToFeed: StateFlow<Boolean> = _shareToFeed.asStateFlow()

    private var currentMediaId: Int? = null

    fun load(mediaId: Int, reviewId: Int) {
        currentMediaId = mediaId
        _uiState.value = WriteReviewUiState.Loading

        viewModelScope.launch {
            try {
                val detail = repository.getMediaDetail(mediaId)

                // Edit review
                if (reviewId > 0) {
                    val review = repository
                        .getReviews(mediaId)
                        ?.find { it.id == reviewId }

                    if (review != null) {
                        _rating.value = review.rating
                        _reviewText.value = review.reviewText ?: ""
                    }
                } else {
                    // New review
                    _rating.value = 0
                    _reviewText.value = ""
                }

                _uiState.value = WriteReviewUiState.Success(detail)
            }
            catch (e: Exception) {
                _uiState.value = WriteReviewUiState.Error(e.message ?: "Failed to fetch media details")
            }
        }
    }

    fun onShareToFeedToggle(share: Boolean) {
        _shareToFeed.value = share
    }

    fun onReviewTextChange(newText: String) {
        if (newText.length <= 500) {
            _reviewText.value = newText
        }
    }

    fun clearActionError() {
        _actionError.value = null
    }

    fun submitReview(reviewId: Int) {
        val mediaId = currentMediaId ?: return

        viewModelScope.launch {
            try {
                if (reviewId > 0) {
                    repository.updateReview(
                        reviewId = reviewId,
                        rating = _rating.value,
                        reviewText = _reviewText.value
                    )
                } else {
                    repository.writeReview(
                        mediaId = mediaId,
                        rating = _rating.value,
                        reviewText = _reviewText.value,
                        shareToFeed = _shareToFeed.value
                    )
                }

                _reviewPosted.value = true

            } catch (e: Exception) {
                _actionError.value = e.message
                e.printStackTrace()
            }
        }
    }

    fun onRatingChange(newRating: Int) {
        _rating.value = newRating;
    }
}
