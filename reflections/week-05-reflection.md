# Week {{5}} Reflection

**Name:** Nick Chyrklund
**Date:** 06/18/2026

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:** https://github.com/benjamincassidymetro/media-tracker-android/compare/main...NChyrklund:media-tracker-android:week-05

---

## Code Review

**Reviewed:** *Ilyas Ibrahim*
**Link to my review:** https://github.com/Ilyas9805/media-tracker-android/pull/5#pullrequestreview-4529762545

### What I Looked At

I looked at three files: `ApiConstants.kt`, `DefaultUserRepository.kt`, and `RegistrationRequest.kt`. These files showcase three different topics we had discussed in class today, such as storing secrets, mapping responses to response codes, and creating serialized classes.

### What I Noticed
`ApiConstants.kt` was used to store public and accessible information such as the **base url**, as well as the local locations of private information such as the **client_id / client_secret**. `DefaultUserRepository.kt` allowed Ilyas to map responses to the response codes for more human-readable error messages when they arise. These changes were made with good practice in mind; the client secret is properly inaccessible to myself / git. The response codes are mapped in a way that makes sense; you can tell by looking at the code that a **201** code means a **success**.

### Comments I Left

The comments I left were about the three files mentioned in what I looked out. Mostly just pointing out that good practices are being followed regarding data privacy, code readablility, seperation of concerns, and that Ilyas's knowledge of serialization was shown in his `RegistrationRequest.kt` class.

---

## One Thing I Understood More Deeply

As the instructor mentioned, we're defining a **UserRepository Inteface** instead of a **UserRepository Class**. The reason for this is for future testing and modification; we could define other types of **UserRepository** that implements this interface, and swap it in to try it out with minimal modification to the other classes. I also better understand the purpose of **serializable**, which I've heard often in the past but haven't had to use much. Essentially, it's forming a sort of *stream* of data into an object, or vice versa, so it can be easily passed from client to server and utilized as intended. For instance, we made a data class called `LoginRequest.kt` for storing data during an attempt to login.

---

## One Thing I'm Still Confused About

(*as of the beginning of class*) As someone with minimal API experience / practice, the use of **Payloads** and the **Reponse codes** is a little intimidating. I get the basic idea of the codes being used to either return data or an error message when something goes wrong, but it's difficult to create a class to call the API (such as `UserApiService.kt`) without an example / reference to go off of. The annotations such as **@POST** and **@Body** also throw me off, as it seems like we're treating them like function calls and variable types... But not completely. For an example from`UserApiService.kt`:

```
@POST("users")
suspend fun createUser(@Body body: RegisterRequest): Response<Unit>
```

---

## Anything Else *(optional)*

Sadiq was very helpful in getting my API client ID and Secret properly placed in the `local.properties` file (not found in github; it is purposely not added to the repository as it contains information not meant to be shared), as well as the reasoning behind naming **apiClientId** and **apiClientSecret** the way that we do; because `build.gradle.kts (:app)` names it as such.

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
