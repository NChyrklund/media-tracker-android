# Week {{3}} Reflection

**Name:** Nick Chyrklund
**Date:** 06/04/2026

---

## Commits This Week

**Link:** https://github.com/NChyrklund/media-tracker-android/commits/week-03/

---

## Code Review

**Reviewed:** *Ilyas Ibrahim*
**Link to my review:** 

### What I Looked At

I looked through a few of the altered files, such as the newly made ApiService.kt file. This will eventually become a class for accessing the Api, but at the moment it does not function. The CreateUserResponse class built in has no functionality; same as the TokenResponse class. The annotated classes he created were simply to clear the error made by those classes otherwise not existing, but being referenced. The User Repository class was also created based on the instructor's instruction. The CreateAccount function was made correctly, calling for the displayName, username, email, and password.

### What I Noticed

As expected, a lot more files were altered during this pull request; this is due to a restructuring of the class schedule, so all files that have been updated / pushed by the instructor have been copied / pasted over Ilyas's own files to be in line with where the class is at. For instance, the *filter_comics* and *filter_albums* strings were added to the strings.xml file, which may of been an updated made by the professor before class as I do not recall those changes being made during.

### Comments I Left

I commented mostly on my understanding that the course this project is built on is going through some restructuring, and as such, this merge is rather more significant than others. I also mentioned that the ApiService.kt will have to be fixed in the future, as annotated classes were added to remove an error that may not be used in the future.

---

## One Thing I Understood More Deeply

I understand how to better navigate / add to someone else's project, and adhere to their set style / themeing. For instance, while creating the registration page, I noticed that a lot of the functionality it requires is already in the login page; just needs to be slightly modified to fit the act of registering instead of logging in. So, my first step was to copy the entirety of the login page code into the RegisterScreen.kt, then analyze each line to determine if it needs to be modified for the purpose of the registration screen, or kept the same. The email / password fields could be kept as is, as they are required for both screens. However, Username, Display name, and confirm password fields would have to be added in. I was able to copy / paste the code that made up the email field for the username / display name, then did the same for the password / confirm password section since those were a bit more special (characters are hidden when typed).

---

## One Thing I'm Still Confused About

I think one thing I'm still a little confused on is whether or not combining the logic (models) of multiple screens into one model file is the right choice, versus breaking them into seperate model files. For instance, at the time of writing this, my breakout team opted to insert all of the registration data (username, displayName, confirmPassword) into the same AuthViewModel.kt file that the login data is stored in. Though I am unsure as of yet if that is the correct choice (I'm writing this early into the class period).

---

## Anything Else *(optional)*

Given that we are copying / pasting a lot of code already, such as for the text fields, I wonder if best practice would be to create a static method as a resource that generates the text fields with a limited number of inputs? For instance, a lot of the fields use these lines:
singleLine = true
modifier = Modifier.fillMaxWidth()


---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
