package edu.metrostate.ics342.mediatracker.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaDetailViewModel(application: Application) : AndroidViewModel(application) {
    // TODO (Week 7): Accept mediaId, call GET /media/{id}, expose MediaDetail state.
    // Also call GET /library to load current status for this item.

    private val mediaRepository = DefaultMediaRepository(DefaultSessionRepository(application))

    private val _mediaId = MutableStateFlow(-1)
    val mediaId: StateFlow<Int> = _mediaId.asStateFlow()



    private val _details = MutableStateFlow<MediaDetail?>(null);
    val details: StateFlow<MediaDetail?> = _details.asStateFlow()

    fun setMediaId(id: Int) {
        _mediaId.value = id
        viewModelScope.launch {
            try {
                val response = mediaRepository.getMediaDetails(id)
                    _details.value = response
            }
            catch (e: Exception) {

            }
        }
    }
}
