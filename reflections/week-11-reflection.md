# Week 11 Reflection — Bonus Feature Sprint (Week 1 of 2)

*This week's reflection is different from the standard template. We're not doing Profile this week — instead, this is the first of two weeks building your assigned bonus feature (Write Review, Quotes, or Priorities). See `reflection-instructions.md` for naming/submission rules, which are unchanged; only the content below differs.*

**Name:** Nick Chyrklund
**Date:** 07/30/2026
**My assigned bonus feature:** *Review*

---

## Commits This Week

<!-- Paste a link to your commits for this week. -->

**Link:** https://github.com/NChyrklund/media-tracker-android/pull/11/commits

---

## Code Review

<!-- Code review continues as normal — same pod rotation, regardless of which bonus feature you or your pod mate are building. -->

**Reviewed:** *Ilyas Ibrahim*
**Link to my review:** https://github.com/Ilyas9805/media-tracker-android/pull/11#pullrequestreview-4824942389

### What I Looked At
I looked at ```Priority.kt```, ```DefaultMediaRepository.kt```, ```PriorityRequest.kt```, ```PriorityScreen.kt```, ```and PriorityViewModel.kt```.

For the data classes such as ```Priority.kt``` and ```PriorityRequest.kt```, I verified that the fields being stored matched their respective **API doc** requirements.

For the UI ```PriorityScreen.kt``` class, I checked that the expected elements appeared that match what is shown in the Priority Wireframe; that the TopAppBar was there, the priority sorter buttons were there, and the media cards were present.

For the ```DefaultMediaRepository.kt``` and ```and PriorityViewModel.kt```, I was checking that they contained the necessary functions for interacting with the API by using the new data classes. 

### What I Noticed
```Priority.kt``` and ```PriorityRequest.kt``` both contained the necessary fields for their relative API requests, according to the **API docs**. ```PriorityRequest.kt``` has the necessary five fields for the API request input to change a priority of a piece of media for the user (**PUT /priorities**), while ```Priority.kt``` has the necessary six fields for storing the result of obtaining a Priority item (**GET /priorities**).

The UI component, ```PriorityScreen.kt```, contains the correct *TopAppBar* and *card* elements for displaying the media cards, however I was unable to find the sorting buttons for the **priority categories** (*low, medium, high*). This is likely a feature to be implemented next week, likely utilizing similar logic made for our library screen (sorting the *want to*, *in progress*, *finished* items).

```DefaultMediaRepository.kt``` and ```and PriorityViewModel.kt``` were given new functions to make the Priority API calls, such as ```getPriorities(): List<Priority>``` and 
```setPriorities(priorities: List<PriorityRequest>): List<Priority>```. I noticed that these functions are set up to catch exceptions, yet do not give an idicator of one; rather, they simply return an empty list if an exception occurs, leaving the user to assume their list is empty even if the exception was due to a server issue / request issue. ```PriorityViewModel.kt``` seems to be fleshed out well, communicating with ```DefaultMediaRepository.kt``` to obtain the **media** / **priority** details for giving to the UI.

### Comments I Left
I left comments regarding what I had noticed above, and a comment on the lack of proper exception handling. Otherwise, my notes were positive in how much progress was made in our class period.

---

## Bonus Feature Progress

<!-- This is the most important section this week. Be concrete: which endpoint(s) did you wire?
     What's actually showing on screen with real data? What's still stubbed or fake?
     "I worked on my bonus feature" is not an answer. "I got POST /quotes working from Media Detail
     and quotes show up in a list on my profile, but I haven't wired edit or delete yet" is. -->

**What's working:**
I managed to get the stateflow for the mediaReviewUIState set up, as well as creating the ```load(mediaId: Int)``` function in ```WriteReviewViewModel.kt```. This function serves to obtain the media details necessary for displaying the current piece of media the user is viewing. Namely, it is needed to display the *cover art*, *media title*, and *author*. The state is correctly altered from *loading* to *successful* if it finds the media, or returns an *exception* and informs the user that it could not find the media.

As for the screen itself, ```WriteReviewScreen.kt```, I've gotten the TopAppBar to display correctly with the **close** icon to the left, centered **title**, and **post button** to the right. I've also linked the screen to the viewModel, as well as linking the **uistate** to obtain the **MediaDetail**. I've started on the ```MediaCover(detail: MediaDetail)``` function for obtaining the cover art, but may decide to pluck that function from the ```MediaDetailScreen.kt``` since it functions the exact same and make it a seperate file to be used by both. This was also reccomended by **Sadiq**.

**What's still stubbed, fake, or not started:**
I have not fleshed out the logic of the **Post** button, nor have I gotten the screen to display the *media details*, *review text box*, or *star-rating system*. It's kind of funny, but I was stuck on a dumb little "bug" where my **post button** was not centering the text vertically; it was putting "post" at the top of the button. It turns out that when I used the IDE UI prompt to create the string resource for ```post_button_text```, I had hit *Enter* on accident, and THEN submitted it to the resource file. This appended a newline character to the resource, and therefore the button was fitting "post" + a new-line.

**What I'm blocked on, if anything:**
As of now, I don't believe I am blocked on anything; I certainly need to catch up this weekend on the goal of the week, as stated in the handout for the Reviews (create the star-rating component, make it function, wire POST /reviews, show real data in ```MediaDetail.kt```).
---

## One Thing I Understood More Deeply
I'm getting a better feel for connecting with the API. What helped massively was actually earlier this week, filling out a word document in which I viewed the instructor's code on wiring the library to the API, and writing down the flow from pressing the button -> sending API request -> Obtaining API response -> altering UI based on response. As for today, I've got a better grasp on the *stateflow* system we've been using. How on success, we supply the data we want to save (in this case, **MediaDetail**), then obtain the data from that state as a field when we need it.

---

## One Thing I'm Still Confused About
I haven't gotten to that point yet, but I'm wondering how I'll enforce the **500 character limit** shown in the ```MediaDetailScreen.kt``` wireframe for the review textbox. My first guess is a simple one; for every character typed, check that the total length of the string has exceeded 500. While checking for each character press seems obsessive to me, it would make sense given the **0/500** element on the wireframe; we're expected to keep count AS the user is typing, anyway. So this check can be included in that function.

---

## Anything Else *(optional)*

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Concrete progress report (what's wired, what's not) plus specific, honest "Understood More Deeply" and "Still Confused" sections. | Present but vague — "I worked on my feature" with no specifics on what's actually working. | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match.
