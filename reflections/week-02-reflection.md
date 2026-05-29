# Week {{N}} Reflection

**Name:** Nick Chyrklund
**Date:** 05/28/2026

---

## Commits This Week

**Link:** https://github.com/NChyrklund/media-tracker-android/pull/2/commits

---

## Code Review

<!-- Every week you leave a review on a pod mate's pull request. Fill in both parts below.
     Part 1 is the link — I will verify the review exists on GitHub.
     Part 2 is your written assessment — what you actually looked at and what you found. -->

**Reviewed:** *Ilyas Ibrahim*
**Link to my review:** https://github.com/Ilyas9805/media-tracker-android/pull/2#pullrequestreview-4386190358

### What I Looked At

I looked at the latest commits to see what bugs had been addressed. In this case, the filter-loss-on-rotation was handled, as well as the freezing of the app via removing the thread.sleep(800) line.

### What I Noticed

The MyProfileScreen.kt was properly updated with the collectAsStateWithLifecycle method to ensure the data did not change on de-focus.
LibraryViewModel.kt was modified to ensure the selected state would remain on rotation, by using a viewModel state.

### Comments I Left

I left comments exactly as written above, calling out the MyProfileScreen.kt and LibraryViewModel.kt changes. These changes have fixed user-submitted bugs.

---

## One Thing I Understood More Deeply

How disjointed the code in Kotlin can be when made professionally; all hard-coded data is broken into the specific Strings.xml class, or given a class seperate from the object it will populate. I also better understand how to work my way back to find the source of a bug, using logcat and searching for usages.

---

## One Thing I'm Still Confused About

A lot of the syntax of Kotlin is still confusing to me, and the way that the various pieces fit together to make the entire funcitoning system.
 In other words, connecting the pieces (classes) to function together in harmony.

---

## Anything Else *(optional)*

Nothing today, mostly due to time restraint.

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
