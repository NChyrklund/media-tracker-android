# Week {{8}} Reflection

**Name:** Nick Chyrklund
**Date:** 07/09/2026

---

## Commits This Week

**Link:** https://github.com/NChyrklund/media-tracker-android/commits/week-08/

---

## Code Review

**Reviewed:** *Sadiq Ahmed*
**Link to my review:** https://github.com/ahmedsadiq04/media-tracker-android/pull/10#pullrequestreview-4668168912

---

### What I Looked At
I looked at two functions in `DefaultMediaRepository.kt` (`media` & `reviews`), `MediaApiService.kt` (interface) with its newest addition being the two new **GET**'s (`media/{media_id}` & `reviews`), and the `MediaDetailViewModel.kt`. Namely, I was looking at the implmentation of `fun setMediaId(id: Int)`.

---

### What I Noticed
`media` & `reviews` in `DefaultMediaRepository.kt` were implemented correctly. `media` is a simple *get media details from an ID* function that makes an **API call** using its **API field**. `reviews` performs a similar action, however it also expected two extra inputs due it returning a list; **limit** and **after** to assist in only loading necessary data / avoid using too many resources.

`MediaApiService.kt` (interface) got two new **GET**'s (`media/{media_id}` & `reviews`). `media/{media_id}` is the interface function of the api call that obtains details of a piece of media based on an ID; it correctly contains what is necessary for making the call, and with the nature of being an interface, allows for easy implementation across various API's for testing / production use (actual API calls). `reviews` does the same for retreiving reviews of the media, and again correctly includes filters (*limit, after*) to ensure not too many resources are used if there are far too many reviews to load.

Finally, I looked at `MediaDetailViewModel.kt`, and the function within it `fun setMediaId(id: Int)`. What I noticed was that the function seemed to be doing much more work than the name suggests; one would expect `setMediaId` to simply change the value of a field after some vetting, but this is actually reloading the **mediaRepository** as well as performing error-handling. I feel these would be best done as a seperate function, and maybe a third public function that calls both to ensure each function only does what is expected of it.

---

### Comments I Left
The comments I left summarize / greatly refelect what is written in the **What I Noticed** portion of this reflection. I called out the proper implementation of `media` & `reviews`, the proper implementation of `media/{media_id}` & `reviews` (along with pointing out the benefit of including limits on a query that gets a list-result), as a small note for `MediaDetailViewModel.kt` that explains the lack of proper error handling in the `setMediaId`. Currently, that function silently drops the error without feedback to the user.

---

## One Thing I Understood More Deeply
I've always struggled with connecting to API's; especially when doing so is spanned across several different objects / classes / interfaces. But this week, I feel I have a better understanding of how these various parts are working together.

`MediaDetailScreen.kt` (UI class) takes in a `MediaDetailViewModel` as a parameter, which it uses to interact with / fetch details of the media piece via a `LaunchedEffect` function . It sends the `mediaId` of the media piece that was touched to the `setMediaId` function of the **ViewModel**.

From there, `MediaDetailViewModel.kt` contains a `DefaultMediaRepository`. It will set the **mediaId** as requested, then **launches** the coroutine. Essentially, it's updating the data that is being sent to the screen. In this function, the details are obtained by calling the `getMediaDetails(id)` function of the `mediaRepository`.

At `DefaultMediaRepository.kt`, a `RetrofitInstance` **api** is stored as a field. the new `getMediaDetails(mediaID: Int): MediaDetail?` uses this api to submit the mediaId to the database, and recieve the details of the media that it requested. It can return a `MediaDetail` object because it is **Serializable**.

---

## One Thing I'm Still Confused About
I struggle with the **null-safety** aspect of the fields we're using. I understand the basic idea of *Add a questionmark to a field to say it's safe to make null*. We use this for fields that may not have a known or existing value yet (such as a piece of media with no known author, or how a movie would have no page count). When setting the `detail` field in `MediaDetailScreen.kt` to the actual API data, suddenly I was met with about 20 red lines! I ended up going through each error, which were mostly field requests to my `detail` field. Some of the requests involved adding a question mark to aknowledge the null-safety, others needed to be followed with a `let` command, which essentially replaces *if (obj != null)* (as long as an object is not null). On some level, I understand the purpose of the null-safety. But in actual practice, I feel I fail to use it when necessary, and struggle at understanding the impact it's having on the rest of the code. 

---

## Anything Else *(optional)*


---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
