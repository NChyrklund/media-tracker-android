package edu.metrostate.ics342.mediatracker.ui.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.DuplicateLibraryException
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import edu.metrostate.ics342.mediatracker.ui.detail.MediaDetailUiState
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

    class WriteReviewViewModel : ViewModel() {
        // TODO (Week 8): Add rating StateFlow, reviewText StateFlow, shareToFeed StateFlow.
        // Wire to POST /reviews on submit.
        private val _rating = MutableStateFlow(0)
        val rating: StateFlow<Int> = _rating.asStateFlow()

        fun onRatingChange(value: Int) {
            _rating.value = value
        }
    }
    private val _uiState = MutableStateFlow<WriteReviewUiState>(WriteReviewUiState.Loading)
    val uiState: StateFlow<WriteReviewUiState> = _uiState.asStateFlow()

    private val _actionError = MutableStateFlow<String?>(null)
    val actionError: StateFlow<String?> = _actionError.asStateFlow()

    private val _reviewText = MutableStateFlow("")
    val reviewText: StateFlow<String> = _reviewText.asStateFlow()

    private val _reviewPosted = MutableStateFlow(false)
    val reviewPosted: StateFlow<Boolean> = _reviewPosted.asStateFlow()

    private var currentMediaId: Int? = null

    fun load(mediaId: Int) {
        currentMediaId = mediaId
        _uiState.value = WriteReviewUiState.Loading
        viewModelScope.launch {
            try {
                val detail = repository.getMediaDetail(mediaId)
                _uiState.value = WriteReviewUiState.Success(detail)
            }
            catch (e: Exception) {
                _uiState.value = WriteReviewUiState.Error(e.message ?: "Failed to fetch media details")
            }
        }
    }

    fun onReviewTextChange(value: String) {_reviewText.value = value }

    fun clearActionError() {
        _actionError.value = null
    }

    fun createReview() {
        val mediaId = currentMediaId ?: return
        viewModelScope.launch {
            try {
                repository.writeReview(mediaId, 2, _reviewText.value, true)
                _reviewPosted.value = true
            }
            catch (e: Exception) {
                _actionError.value = e.message
                e.printStackTrace()
            }

        }
    }
}
