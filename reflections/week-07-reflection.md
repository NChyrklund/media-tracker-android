# Week {{7}} Reflection

**Name:** Nick Chyrklund
**Date:** 07/02/2026

---

## Commits This Week

**Link:** https://github.com/NChyrklund/media-tracker-android/commits/week-07/

---

## Code Review

**Reviewed:** *Sadiq Ahmed*
**Link to my review:** https://github.com/ahmedsadiq04/media-tracker-android/pull/9

---

### What I Looked At
I looked at Sadiq's work in `Media.kt`, and `MediaDetailScreen.kt`. I focused on the elements he had added (**columns** / **rows** / **buttons**), as well as the *modifiers* supplied for each. I also looked at the fake media entry made in `MediaDetailScreen.kt` for testing purposes.

---

### What I Noticed
Since the fake media source is built directly into the `MediaDetailScreen.kt` file, it cannot easily be swapped in / out for testing purposes in the future; he will have to remember to remove the fake media in its entirety to prepare the app for a public release in the future, and will need to reinvent it again if he wishes to do more API-less testing.
His **main column** fills to the **max size** of the container, but does not provide any padding. This would mean that the elements likely contact the edges of the phone, which can be an issue with the various differing phone designs seen today. It would be a good idea to include horizontal padding to ensure there is space between the elements and the edges.
His buttons (**overflow button**, **Save**) do not have functionality yet, as seen in lines 104 + 197 of `MediaDetailScreen.kt`. This is to be expected, as the focus of class was strictly on the visual aspects of the media screen. He did, however, include the functionality of the back button, as it is one we have fleshed out before and could easily be re-obtained. This was a good move on his part.
He was careful to match the fonts / theming in the wireframes with his Texts, using modifiers for the **styling**, **coloring**, **overflow**, and **padding** to match them.

---

### Comments I Left
I left comments regarding the use of the embedded fake media source in his `MediaDetailScreen.kt`, as well as how much progress he made in (what I consider) a very short amount of time for this screen; All the way down to the `Write Review` portion of the screen. I also applauded his attention to detail with the text modifications / font altercations, and approved the merge to his main.

---

## One Thing I Understood More Deeply
The use of columns and rows to organize the screen layout as desired. Putting together the media screen was a bit more in-depth than the screens we've completed in the past. This one required utilizing a single **column** which centers / expands the size of every **row** in it, while each row has its own unique modifications that needed to be made. For instance, the **back button** and **overflow button** were on their own row in `MediaDetailScreen.kt`, yet the two buttons had to be on opposing sides of the screen; as far away from each other as they can. This required the use of the `horizontalArrangement = Arrangement.SpaceBetween` command. In contrast, most of the alignments we've been using involved either even-spacing (such as the `+ want to` and `save` button row), or aligning everything to the left such as with the cards in `SearchComponents.kt`. This week has, in general, forced me to get a bit more involved in the formatting of UI in **Jetpack Compose**, and having the tips on where to look for references in the provided handout was very helpful!

---

## One Thing I'm Still Confused About
I have difficulty with proper testing of my code. For instance, this week I created `FakeMedia.kt` as a pseudo-media source that is shown regardless of what media source is picked. In order to use it, I ended up adding a `media` input parameter to my `MediaDetailScreen.kt` class, which then involved adding an input in the `NavGraph.kt` for it (which the handout explicitly said we should **NOT** have to do). That being said, I'd appreciate us delving a little further into how we produce more testable code, where test-data and real data can be swapped for each other with minimal changes to the code.

---

## Anything Else *(optional)*
I appreciate the handout this week for giving not only the goal of what we are to do, but also pointers on how to achieve it. While I understand the purpose of sort of sending us all off blind to figure out the solution, when you have difficulty in finding a starting point it can certainly be demoralizing.

---

## Rubric

*You don't need to self-assess — this is here so you know what I'm looking at.*

| Section | Points | Full Credit | Half Credit | No Credit |
|:---|:---:|:---|:---|:---|
| **Reflection** | 10 | Specific, honest responses to "More Deeply" and "Still Confused" sections. Shows genuine thinking — not just "I learned X." | Responses are present but vague or generic ("I got better at Compose"). | Missing or one-word answers. |
| **Code Review** | 10 | Specific observation about the code with explanation of why it matters (or a substantive positive comment). Link to review present and verified. | A question or comment that shows you read the code, but lacks explanation. | "Looks good!" or equivalent. Missing link. Review not found on GitHub. |
| **Total** | **20** | | | |

**A note on the code review score:** I check that the review actually exists on GitHub before grading. The written summary here and the GitHub comment should match. If the review isn't there, the written summary can't earn credit.
