package edu.metrostate.ics342.mediatracker.ui.review

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.MediaDetail
import edu.metrostate.ics342.mediatracker.data.model.MediaType
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit
import edu.metrostate.ics342.mediatracker.theme.MovieContainer
import edu.metrostate.ics342.mediatracker.theme.OnMovieContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteReviewScreen(
    mediaId: Int,
    reviewId: Int,
    onNavigateBack: () -> Unit,
    viewModel: WriteReviewViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val actionError by viewModel.actionError.collectAsState()
    val reviewPosted by viewModel.reviewPosted.collectAsState()
    val rating by viewModel.rating.collectAsState()
    val shareToFeed by viewModel.shareToFeed.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(mediaId, reviewId) {
        viewModel.load(mediaId, reviewId)
    }

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
                        onClick = {viewModel.submitReview(reviewId)}
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
                            Button(onClick = { viewModel.load(mediaId, reviewId) }) {
                                Text(stringResource(R.string.detail_retry))
                            }
                        }
                    }
                }

                is WriteReviewUiState.Success -> {
                    val reviewText by viewModel.reviewText.collectAsState()

                    val edittingReview = if (reviewId > 0) true else false

                    SuccessContent(
                        state = state,
                        reviewText = reviewText,
                        onReviewTextChange = viewModel::onReviewTextChange,
                        onRatingChange = viewModel::onRatingChange,
                        rating = rating,
                        reviewCharLimit = viewModel.reviewCharLimit,
                        shareToFeed = shareToFeed,
                        onShareToFeedToggle = viewModel::onShareToFeedToggle,
                        edittingReview = edittingReview,
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
    onReviewTextChange: (String) -> Unit,
    onRatingChange: (Int) -> Unit,
    rating: Int,
    reviewCharLimit: Int,
    edittingReview: Boolean,
    shareToFeed: Boolean,
    onShareToFeedToggle: (Boolean) -> Unit
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
            onRatingChange = onRatingChange,
            rating = rating
        )}


        Spacer(Modifier.height(14.dp))

        SectionCaption(stringResource(R.string.review_your_review))

        Spacer(Modifier.height(14.dp))

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

        Text(
            text = "${reviewText.length} / ${reviewCharLimit}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            textAlign = TextAlign.End,
            style      = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(14.dp))

        if (!edittingReview) {
            shareCheckbox(
                share = shareToFeed,
                onShareToFeedToggle = onShareToFeedToggle
            )
        }
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
private fun shareCheckbox(
    share: Boolean,
    onShareToFeedToggle: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onShareToFeedToggle(!share)
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = share,
            onCheckedChange = onShareToFeedToggle
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.review_toggle_share),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun StarRow(
    rating: Int,
    onRatingChange: (Int) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for (star in 1..5) {

            Icon(
                imageVector = if (star <= rating)
                    Icons.Filled.Star
                else
                    Icons.Outlined.StarBorder,

                contentDescription = "$star stars",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onRatingChange(star)
                    }
            )
        }
    }
}