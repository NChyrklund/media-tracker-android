package edu.metrostate.ics342.mediatracker.ui.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.model.Review
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import edu.metrostate.ics342.mediatracker.ui.detail.MediaDetailUiState
import edu.metrostate.ics342.mediatracker.ui.library.LibraryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface MediaReviewUiState {
    data object Loading : MediaReviewUiState
    data object NotFound : MediaReviewUiState
    data class Error(val message: String) : MediaReviewUiState
    data class Success(
        val detail: MediaDetail
    ) : MediaReviewUiState
}

class WriteReviewViewModel @JvmOverloads constructor(
    application: Application,
    private val repository: DefaultMediaRepository =
        DefaultMediaRepository(DefaultSessionRepository(application))
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<MediaReviewUiState>(MediaReviewUiState.Loading)
    val uiState: StateFlow<MediaReviewUiState> = _uiState.asStateFlow()

    private var currentMediaId: Int? = null

    fun load(mediaId: Int) {
        currentMediaId = mediaId
        _uiState.value = MediaReviewUiState.Loading
        viewModelScope.launch {
            try {
                val detail = repository.getMediaDetail(mediaId)
                _uiState.value = MediaReviewUiState.Success(detail)
            }
            catch (e: Exception) {
                _uiState.value = MediaReviewUiState.Error(e.message ?: "Failed to fetch media details")
            }
        }
    }

    class WriteReviewViewModel : ViewModel() {
        // TODO (Week 8): Add rating StateFlow, reviewText StateFlow, shareToFeed StateFlow.
        // Wire to POST /reviews on submit.
        private val _rating = MutableStateFlow(0)
        val rating: StateFlow<Int> = _rating.asStateFlow()

        fun onRatingChange(value: Int) {
            _rating.value = value
        }
    }
}
