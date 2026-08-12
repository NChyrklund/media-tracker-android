package edu.metrostate.ics342.mediatracker

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import edu.metrostate.ics342.mediatracker.ui.review.StarRow
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReviewUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun tappingThirdStar_callsOnRatingChangeWithThree() {
        var selectedRating = 0

        composeTestRule.setContent {
            StarRow(
                rating = selectedRating,
                onRatingChange = { rating ->
                    selectedRating = rating
                }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("4 stars")
            .performClick()

        assertEquals(4, selectedRating)
    }
}