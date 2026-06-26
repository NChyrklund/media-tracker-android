# Week {{5}} Reflection

**Name:** Nick Chyrklund
**Date:** 06/25/2026

---

## Commits This Week

**Link:** https://github.com/NChyrklund/media-tracker-android/commits/week-06/

---

## Code Review

**Reviewed:** *Sadiq Ahmed*
**Link to my review:*https://github.com/ahmedsadiq04/media-tracker-android/pull/8#pullrequestreview-4576356016* 

---

### What I Looked At
I looked at the **drawable resource** Sadiq provided, `AuthViewModel.kt`, and `MediaApiService.kt`. However, as mentioned in Sadiq's commit messages, he had to change many more files than normal due to too much variation between the reference code and his one, making the project difficult to continue through his own methods.

---

### What I Noticed
There were **drawable resources** missing from the resource folder. Namely, `Smart_display.xml`, `ic_launcher_background.xml`, and `ic_launcher_foreground.xml`. These are necessary for matching the wireframes on the login and registration pages, which are currently missing those logos.
`AuthViewModel.kt` was properly seperated from `RegisterViewModel.kt`, which was something he had been trying to keep as one file until now. With them seperated, they now provide looser coupling and a better seperation of concerns.
`MediaApiService.kt` was correctly implemented as an interface with various query types; `query`, `type`, `limit`, and `after`.

---

### Comments I Left
The comments I left behind were the things that I had noticed as written above; about the missing resources, proper seperation of `AuthViewModel.kt` from `RegisterViewModel.kt`, and `MediaApiService.kt` being properly implemented. I kept my comment limited to only a few file changes, as he mentioned during our breakout time of needing to refactor many more files than what should reasonably be reviewed in our short time. 

---

## One Thing I Understood More Deeply
The difference between a **Column** and a **LazyColumn**, as seen in `SearchResultsScreen.kt` when creating the media card area. Essentially, a standard **Column** will fully generate all of the items being requested immediatly; using excessive resources to generate elements that may or may not be necessary. By contrast, the **LazyColumn** only generates elements that are visible on the screen. In our case, this means that the media card elements being generated are not using up resources unless they are currently on screen, thereby being actively generated when needed and disposed of when no longer needed.

---

## One Thing I'm Still Confused About
The use of `RetrofitInstance.kt` to connect to web services with the `mediaApiService` function. In our case, we're using it to send HTTP requests, as well as intercept the request before it goes out using **.addInterceptor(AuthInterceptor(sessionRepository))** and **addInterceptor(loggingInterceptor())**. I wasn't sure what the purpose of these were beyond being able to modify requests before they go out, or a logging tool to show what is being sent to / from the server for troubleshooting. It's less about what's going on (as the IDE will tell you exactly what each function call is doing, such as `.client` or `.baseURL(ApiConstants.BASE_URL)`), and more about what it's providing for our project. I believe it is simplifying what would otherwise be a more customized API calling system by providing all the needed functionality for this particular use-case, and the instructor did mention that it is used due to being robust / reliable for network outages, but I'm still a bit unsure on how those benefits work for our use case. For instance, what if a user using our app has a network outage?

---

## Anything Else *(optional)*
When we first went into our breakout rooms to create the Search screen, the first issue I came across was that the search bar I had made was too high up on the android screen; it was being blocked by the camera, since the camera is technically *inside* the screen. Turns out, through using the **TopAppBar** command to add space at the top, and ensure the rest of the app is in the expected usable area of the phone.

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
