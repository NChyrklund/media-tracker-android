package edu.metrostate.ics342.mediatracker.ui.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.StarHalf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.model.MediaType
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit
import edu.metrostate.ics342.mediatracker.theme.MediaTrackerTheme
import edu.metrostate.ics342.mediatracker.theme.MovieContainer
import edu.metrostate.ics342.mediatracker.theme.OnMovieContainer
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteReviewScreen(
    mediaId: Int,
    onNavigateBack: () -> Unit,
    viewModel: WriteReviewViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val actionError by viewModel.actionError.collectAsState()
    val reviewPosted by viewModel.reviewPosted.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(mediaId) { viewModel.load(mediaId) }

    LaunchedEffect(reviewPosted) {
        if (reviewPosted) {
            onNavigateBack()
        }
    }

    LaunchedEffect(actionError) {
        actionError?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearActionError()
        }
    }

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
                        onClick = {viewModel.createReview()}
                    ) {
                        Text(
                            text = stringResource(R.string.post_button_text),
                        )
                    }
                }
            )

            when (val state = uiState) {
                is WriteReviewUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is WriteReviewUiState.NotFound -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.detail_not_found),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                is WriteReviewUiState.Error -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(16.dp))
                            Button(onClick = { viewModel.load(mediaId) }) {
                                Text(stringResource(R.string.detail_retry))
                            }
                        }
                    }
                }

                is WriteReviewUiState.Success -> {
                    val reviewText by viewModel.reviewText.collectAsState()

                    SuccessContent(
                        state = state,
                        reviewText = reviewText,
                        onReviewTextChange = viewModel::onReviewTextChange
                    )
                }
            }
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

@Composable
private fun SuccessContent(
    state: WriteReviewUiState.Success,
    reviewText: String,
    onReviewTextChange: (String) -> Unit
) {
    val detail = state.detail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // ── Cover + title + credit + rating ──────────────────────────────
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MediaCover(detail)

            Spacer(Modifier.height(14.dp))

            Text(
                text       = detail.title,
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text  = detail.creatorCredit(LocalContext.current),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(14.dp))

        SectionCaption(stringResource(R.string.review_your_rating))

        Spacer(Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) { StarRow(
            onRatingChange = { }
        )}


        Spacer(Modifier.height(14.dp))

        SectionCaption(stringResource(R.string.review_your_review))

        Spacer(Modifier.height(14.dp))

        //TODO: REVIEW BOX
        OutlinedTextField(
            value = reviewText,
            onValueChange = onReviewTextChange ,
            placeholder = { Text(stringResource(R.string.review_your_thoughts)) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 150.dp)
        )



        Spacer(Modifier.height(14.dp))
    }
}

@Composable
private fun SectionCaption(text: String, modifier: Modifier = Modifier) {
    Text(
        text       = text.uppercase(),
        style      = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
        color      = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier   = modifier
    )
}


@Composable
private fun StarRow(onRatingChange: (Int) -> Unit) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 1..5) {
            Icon(
                imageVector        = Icons.Outlined.StarBorder,
                contentDescription = null,
                modifier           = Modifier.size(48.dp),
                tint               = MaterialTheme.colorScheme.secondary
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
            onNavigateBack = {}
        )
    }
}