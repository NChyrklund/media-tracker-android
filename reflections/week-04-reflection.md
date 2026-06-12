# Week {{4}} Reflection

**Name:** Nick Chyrklund
**Date:** 06/11/2026

---

## Commits This Week

<!-- Paste a link to your commits for this week. The easiest way: go to your repo on GitHub,
     click "commits", and copy the URL after filtering by your name or branch. -->

**Link:** https://github.com/benjamincassidymetro/media-tracker-android/compare/main...NChyrklund:media-tracker-android:week-04

---

## Code Review

**Reviewed:** *(Sadiq Ahmed)*
**Link to my review:** https://github.com/ahmedsadiq04/media-tracker-android/pull/6

### What I Looked At

I looked at `RegisterScreen.kt`, `AuthViewModel.kt`, and `LoginScreen.kt`. `LoginScreen.kt` was modified to include the `smart_display.xml` logo, which was also imported during this week as part of the pull request.

### What I Noticed

In speaking with Sadiq, he had mentioned that he preferred to conjoin his `RegisterViewModel.kt` with his `AuthViewModel.kt` (hence why `RegisterViewModel.kt` does not exist). His preference is to maintain logic of both screens in one file. However, I did not find the changes he had mentioned in his pull request; the `AuthViewModel.kt` did not contain fields for a username, displayname, or passwordConfirmation, nor did it have the logic to return errors on the registration screen. We had departed before I could ask him if there was an error (my mistake for leaving early!), and will address it with him next session. 

### Comments I Left

I left comments on the three files mentioned above, and made the same point on how I could not find the fields for the Registration Screen in `AuthViewModel.kt`. I also made a note that the `smart_display.xml` logo was imported in this pull, but that is not an issue. Only something I noticed.

---

## One Thing I Understood More Deeply

While I struggled a little bit with the comparing *password* / *confirmPassword* (not the actual comparison, but the best practice of doing so as written below in the next section), I do feel more confident in ensuring logic and views are seperated. We originally started with our blank field and password validation in the `RegisterScreen.kt`, but I managed to pull it out and move it over to the `RegisterViewModel.kt` where it belongs. It ended up being a single If-statment that checks if each field is filled / password validated, and changes out an error code based on what the issue is.

---

## One Thing I'm Still Confused About

I struggled a bit with understanding how the comparison of *password* and *confirmPassword* should be handled in `RegisterViewModel.kt`. I had posted some potential ideas in the padlet, including storing the confirmPassword as another field in the `RegisterViewModel.kt`, but that doesn't sound right to me as it is not actually something we care to store; it is only needed for a moment. My other idea was to have the **onSignUpClicked** function take an input paramater of the *confirmPassword* value, which so far makes the most sense to me. Will update this portion if I get an answer later in class.

**UPDATE:** We've settled on including the *confirmPassword* as a field in the `RegisterViewModel.kt`.

---

## Anything Else *(optional)*

I was going through some frustration with getting the `RegisterScreen.kt` to **not crash** when being opened through the registration link. I used logcat to try and trace the issue, and tried multiple different ideas; removing the attempt to connect to an API, altering the `AuthViewModel.kt`, and adding annotated classes to 'ApiService.kt`. When none of these fixed the issue, I tried creating a new temporary branch, downloading the zip file of the instructor's week-03.zip, and swapping out the files of the new branch with those files to see if there was something I had missed. But that also did not work, and I ended up having to wait for the next push (*01-basic-register-screen* branch) to fix my issue. I'm still unsure what went wrong.

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
