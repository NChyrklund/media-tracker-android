package edu.metrostate.ics342.mediatracker.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.theme.OnMovieContainer
import edu.metrostate.ics342.mediatracker.ui.search.SearchResultsViewModel
import kotlin.math.roundToInt

// ── STUB — Students build this in Week 7 ─────────────────────────────────────
//
// Week 7 task: Build the Media Detail screen.
//   1. Receive mediaId from the navigation argument (typed Int — see NavGraph).
//   2. Call GET /media/{mediaId} to load full details.
//   3. Display: cover image, title, creator credit, metadata grid, genre chips,
//      average rating, description, and a library status control.
//   4. Display the reviews list from GET /reviews?mediaId={id}.
//   5. Handle loading and error states (full-screen — no half-built screens).
@Composable
fun MediaDetailScreen(
    mediaId: Int,
    onBack: () -> Unit,
    onOverflow: () -> Unit,
    media: Media,
    viewModel: MediaDetailViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onWriteReview: (Int) -> Unit
) {

    val focusManager = LocalFocusManager.current

    val iconTint = when (media.mediaType) {
        "book"  -> MaterialTheme.colorScheme.onPrimaryContainer
        "movie" -> OnMovieContainer
        else    -> MaterialTheme.colorScheme.secondary
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Top navigation bar
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.action_back),
                    modifier = Modifier.weight(1f)
                )
            }

            IconButton(onClick = onOverflow) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.action_back)
                )
            }
        }

        //Icon
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(when (media.mediaType) {
                    "book"  -> R.drawable.menu_book_24px
                    "movie" -> R.drawable.movie_24px
                    else    -> R.drawable.tv_24px
                }),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = iconTint,

            )
        }

        Text(media.title)

        Spacer(Modifier.height(8.dp))

        media.author?.let { Text(it) }

        //Rating
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val numStars = media.averageRating.roundToInt()
            Text(
                text = buildString {
                    for(i in 1 until 6)
                        when {
                            i <= numStars -> append("★")
                            else -> append("☆");
                        }

                    append(" " + media.averageRating)
                    append(" " + media.ratingCount)
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        //Buttons
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { focusManager.clearFocus(); viewModel.onWishlistClick() },
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f)
            ) {
                Text(stringResource(R.string.media_wishlist_label))
            }
            OutlinedButton(
                onClick = { focusManager.clearFocus(); viewModel.onSaveClick() },
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f),
                )
             {
                Text(stringResource(R.string.media_save_label))
            }
        }

    }
}