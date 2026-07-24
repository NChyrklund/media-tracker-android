# Week {{10}} Reflection

**Name:** Nick Chyrklund
**Date:** 07/23/2026

---

## Commits This Week

**Link:** https://github.com/NChyrklund/media-tracker-android/pull/10/commits

---

## Code Review

**Reviewed:** *Ilyas Ibrahim*
**Link to my review:** https://github.com/Ilyas9805/media-tracker-android/pull/10#pullrequestreview-4769681060

---

### What I Looked At
I looked at four files: ```FavoriteEntry.kt``` & ```LibraryEntry``` (*simple data classses*), ```AddLibraryRequest.kt``` (*series of data classes in one file, used to store data for API calls*), and ```WriteReviewViewModel.kt``` (*child class of ViewModel, which includes functions **onRatingChange*** & **OnReviewTextChange** for handling the communication between the ```WriteReview.kt``` UI and the API).

---

### What I Noticed
```FavoriteEntry.kt``` & ```LibraryEntry.kt``` were implemented correctly; I cross-referenced the fields in both with the API wireframe provided to confirm that they would store the data of the responses correctly. For instance,  ```FavoriteEntry.kt``` has fields for **userId**, **mediaId**, **createdAt**, and **media**. This lines up with the ```get /favorites``` section of the API wirefram.

For ```AddLibraryRequest.kt```, three Serializable objects serve as the platters for delivering API requests. **UpdateLibraryRequest** stores the *status* of a library request for adding / removing an item from the library (is the *status* what should be stored? Or the *mediaId* and an *action*?), **AddFavoriteRequest** stores a *mediaId* for marking a media as a favorite, and **AddLibraryRequest** stores both a *mediaId* & *status*
(UPDATE: *After viewing the wireframe for ```post /library```, I understand now that Ilyas had correctly chosen his two fields for the purpose of adding books to the libary. The status is essentially the action in this case; telling the API how to categorize the book in the library, AKA the three statuses.* )

```WriteReviewViewModel.kt``` was supplied the **DefaultMediaRepository**, so it can make calls to the API. It properly implements the **viewModelScope.launch** function, and obtains the corresponding media details from a submitted *mediaId*. Two functions were also created for passing to ```WriteReviewScreen.kt```; **onRatingChange** and **onReviewTextChange**

---

### Comments I Left
The comments I left are the summarized version of what I had noticed above; namely, aknowledging the correct implemnentations of the data classes against the wireframes, my comment on ```AddLibraryRequest.kt``` possibly needing a field for an action to be performed, and an extra note about how the String being used to store the library status should be changed to an enum / object in the future for type-safety.

---

## One Thing I Understood More Deeply
With the instructor's assistance, I traced the interactions between differnent files we had completed previously; ```SearchScreen.kt```, ```SearchViewModel.kt```, ```NavGraph.kt```, ```RetrofitInstance.kt```, and ```MediaApiService```. I wanted to follow the steps from pressing *Enter* on the search screen, updating the screen and showing data from the API.

From this excercise, I was able to better piece together how these different parts are functioning together. How the functionality of pressing *enter* was given to ```SearchScreen.kt``` by ```NavGraph.kt```, which then directs the user to the ```SearchResultsScreen.kt``` while passing along their entered query, which utiziles ```SearchResultsViewModel.kt``` to get results to then display on the ```SearchResultsScreen.kt``` screen.

---

## One Thing I'm Still Confused About
Even after doing the above excercise, I still felt lost on using the API to connect to the ```POST /library```. Even after consulting my squad mates and the instructor, as well as viewing the API wireframe of ```POST /library```, I couldn't figure out the implementation. And I couldn't even determine where was the best starting spot. I spent so much time in ```MediaApiService.kt``` trying to understand what work we have already done, and how this new task of adding books to our own personal libraries would differ / be the same. Only after reviewing Ilyas's code did I get a better grasp on where to focus; start by making my data objects for storing request data, such as Ilyas's ```LibraryRequest.kt``` class to match what is needed for the API calls in the wireframes. Then make a class to store what the API will return; ```LibraryEntry.kt```. THEN look to ```MediaApiService.kt``` to implement the use of those ```LibraryRequest.kt``` objects for making calls, and ```LibraryEntry.kt``` for storing and deserializing the recieved data.

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
