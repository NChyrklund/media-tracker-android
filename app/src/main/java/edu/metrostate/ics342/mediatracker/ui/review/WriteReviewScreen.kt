package edu.metrostate.ics342.mediatracker.ui.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.model.MediaType
import edu.metrostate.ics342.mediatracker.theme.MediaTrackerTheme
import edu.metrostate.ics342.mediatracker.theme.MovieContainer
import edu.metrostate.ics342.mediatracker.theme.OnMovieContainer

// ── STUB — Students build this in Week 8 ─────────────────────────────────────
//
// Week 8 task: Build the Write Review screen.
//   1. Show a media summary at the top (cover thumbnail + title).
//   2. Build a StarRatingRow composable: 5 tappable stars, tapping star N sets rating = N.
//      Extract it as a reusable composable with (rating: Int, onRatingChange: (Int) -> Unit).
//   3. Add a multiline text field (max 500 chars) with a character counter.
//   4. Add a "Share to activity feed" checkbox (checked by default).
//   5. Disable "Post Review" until at least one star is selected.
//   6. Wire to POST /reviews on submit; navigate back on success.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteReviewScreen(
    mediaId: Int,
    onNavigateBack: () -> Unit,
    onPost: () -> Unit = {},
    viewModel: WriteReviewViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(mediaId) { viewModel.load(mediaId) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.Review_Screen_Title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.action_close)
                        )
                    }
                },
                actions = {
                    Button(
                        onClick  = onPost,
                    ) {
                        Text(
                            text = stringResource(R.string.post_button_text),
                        )
                    }
                }
            )

            Spacer(Modifier.height(14.dp))

            //TODO: use MediaCover to display the media art

        }
    }
}

@Composable
private fun MediaCover(detail: MediaDetail) {
    val containerColor = when (detail.mediaType) {
        MediaType.BOOK  -> MaterialTheme.colorScheme.primaryContainer
        MediaType.MOVIE -> MovieContainer
        else            -> MaterialTheme.colorScheme.secondaryContainer
    }
    val iconTint = when (detail.mediaType) {
        MediaType.BOOK  -> MaterialTheme.colorScheme.onPrimaryContainer
        MediaType.MOVIE -> OnMovieContainer
        else            -> MaterialTheme.colorScheme.secondary
    }
    val placeholder = when (detail.mediaType) {
        MediaType.BOOK  -> R.drawable.menu_book_24px
        MediaType.MOVIE -> R.drawable.movie_24px
        else            -> R.drawable.tv_24px
    }

    Box(
        modifier          = Modifier
            .size(width = 110.dp, height = 160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor),
        contentAlignment = Alignment.Center
    ) {
        if (detail.coverUrl != null) {
            AsyncImage(
                model            = detail.coverUrl,
                contentDescription = detail.title,
                contentScale     = ContentScale.Crop,
                modifier         = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                painter           = painterResource(placeholder),
                contentDescription = null,
                modifier           = Modifier.size(52.dp),
                tint               = iconTint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WriteReviewScreenPreview() {
    MediaTrackerTheme {
        WriteReviewScreen (
            mediaId = 1,
            onNavigateBack = {},
            onPost = {}
        )
    }
}


