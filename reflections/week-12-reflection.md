# Week 12 Reflection — Bonus Feature Sprint (Week 2 of 2, Final)

*Second and last week of bonus feature work. Week 13 has no build time — this is the last chance to get your feature demo-ready before Week 14. This template replaces the standard weekly reflection, same as last week.*

**Name:** Nick Chyrklund
**Date:** 06AUG2026
**My assigned bonus feature:** *Review*

---

## Commits This Week

**Link:** https://github.com/NChyrklund/media-tracker-android/pull/12/commits

---

## Code Review

**Reviewed:** *Sadiq Ahmed*
**Link to my review:** https://github.com/ahmedsadiq04/media-tracker-android/pull/13#pullrequestreview-4879399391

### What I Looked At
I looked at four items in particular:
- ```Favorite.kt``` and the data classes it contains (```FavoriteItem``` & ```FavoriteRequest```)
- ```LibraryItem.kt``` and the data class included (```LibraryRequest```)
- ```QuoteModal.kt``` and the new data fields it contains (*initialQuoteText, initialPageNumber, initialIsPublic*)
- ```QuoteScreen.kt``` and its newest functions

### What I Noticed
The fields of the data classes in ```Favorite.kt``` match the inputs and outputs of ```GET /favorites/{mediaId}``` from the API docs, allowing the user to mark medias as favorites with one of three statuses. ```LibraryItem.kt``` was similarly updated with the ```LibraryRequest``` data class, allowing the user to make API calls to add an item to their library using the ```POST /library``` call.

```QuoteModal.kt``` was modified to include a few data fields in the QuoteModal function for storing quote examples, and displaying them. This file is currently stored in the **ui/detail** folder, as it seems to be intended to show summarized views of quotes from users, similar to how our **media** is shown in the **search** feature.

```QuoteScreen.kt``` contains various helper functions for interacting with quotes. I feel that the ideal place for these functions is within the viewmodel, as they seem to be unrelated to the showing of the UI.

While writing about ```QuoteScreen.kt```, I noticed in ```QuotesViewModel.kt``` that the function ```getMyQuotes``` does no validation for an unsuccessful response; it is ignored / silently dropped.

### Comments I Left
The comments I left are a summary of what I had noticed / looked at above, mostly showing that the data classes matched with the API docs, and my concern with the logic in the ```QuoteScreen.kt``` file.

---

## Bonus Feature — Final Status

<!-- Be concrete and honest. This is your last chance to flag something before demos.
     What does your feature actually do, end to end, right now? What's polished vs. rough?
     Is there anything you know is broken or half-done that you want on my radar before Week 14? -->

**What works end-to-end, right now:**
As of now, a user is able to:
- Open the review screen on a piece of media
- give it a rating by clicking the star-rating row
- Enter up to 500 characters to summarize their review
- Check a box to determine if they want the review to appear on their feed
- Post the review, which IS wired to the API
- View their review on the piece of media
- See their review above all others, while the others are shown newest-to-oldest

**Tests written for this feature:**
I do not have any tests currently stored for this feature. Rather, I've been re-running the app, logging in, and attempting to post multiple reviews to the same piece of media / trying to exceed the character limit / trying to leave a rating of 0.

**Known gaps or rough edges going into demos:**
- Since we haven't wired up the feed screen yet, choosing to show the review on your feed currently does nothing beyond marking it as "true" in the database.
- Currently do not have the edit / delete feature built in. The review button is still there as well, which is meant to be hidden if a review has already been made by the user.
- ```ReviewViewModel.kt``` will have to be modified to allow both writing a NEW review with the **mediaId**, or updating an old review with the **reviewId**.

---

## One Thing I Understood More Deeply
I was confused on how I would be able to check whether a piece of media was created by the user, or someone else, given that we've hardly had to use the userId intentionally up to this point. From reserching this, I figured the ```DefaultMediaRepository.kt``` would be the place to make the connection, since it already pulls the ```UserSessionRepository``` in the constructor. I just had to make that input parameter available to the class; not just the constructor.

---

## One Thing I'm Still Confused About
I feel as though I'm bloating up the parameters of the ```SuccessContent``` function in WriteReviewScreen.kt. My research has shown me that I do NOT want to give the viewmodel to it from the ```WriteReveiwScreen``` function, and instead only pass the functions / fields required. But at this point it's taking in most of the viewmodel's functionality anyway; 8 different input parameters. I can't help but feel there is a better solution; maybe instead of using the ```SuccessContent``` function to make all future calls for the review screen, I should keep it to only displaying the media details (cover art, title, author), and handle the rest of the features from the ```WriteReveiwScreen``` function, which DOES have access to the viewmodel for good reason.

---

## Anything Else *(optional)*

<!-- Anything about the bonus feature sprint as a whole — the two-week format, being assigned a
     feature rather than choosing it, whatever's on your mind — is fair game here. -->

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Honest final-status report — what works end-to-end, what's rough, what's tested — plus a specific, genuine "Understood More Deeply" that reflects on the sprint as a whole, not just this week. | Present but vague, or only reports on this week rather than the feature's overall state. | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** same as every other week — I check the link before grading.
