# My Personal Project

*Name: Tina Nguyen* 

## Souvenir: A Digital Travel Journal

**Souvenir** which is synonymous to momento in English and means memory *en francais* (in French) is a digital travel 
journal set that you keep as a momento to track and save the memories from your trips and vacations. It is meant to be 
a centralized diary where you can add details of where you went, when you went, what you saw, who you went with, and 
a review about your experience. Examples of locations that you can manually include in 
**Souvenir** include:
- Restaurants or Cafes (eateries)
- Specific Cities/Villages/Prefectures/Towns/States/Provinces in Foreign Countries
- Tourist Sites and Attractions

Users of this project include:
- **Anyone who loves to travel** (whether it's their first time or if they're an avid traveler)
- Those who are very sentimental and would like to document their travel experience for future references
- Organized individuals that like to save their travel experiences in a centralized location

This project is of interest to me because I am all of the above! I have always loved to travel and am even more 
passionate to go on a few trips in the very near future since the current pandemic cancelled a lot of my plans.
However, my memory is terrible! I thought this would be a good project that I would most likely use in the future 
as during my last trip to California, San Fransisco, I lost a postcard *:(*. For context,I typically track my 
experiences by buying a postcard at a tourist attraction I loved and on the back, write notes to myself with the 
dates and names of specific places I visit. The hope with **Souvenir**, is that I won't be able to lose my travel 
*"journal"* and keep everything organized in one place so that in the future I can reminisce about all the places 
I have been to without too much of a hassle! 

## User Stories

As a user, I want to be able to:
- Add a location/trip entry with the place I visited and dates to my travel journal
- Add 2 categories of details: attractions visited/ activities done and restaurants/cafes (eateries) visited.
- Delete a location/trip entry
- Select a trip entry from the journal and view the trip details
- Save my entries in the journal
- Re-open the journal with all my saved entries and keep adding to it

## Phase 4: Task 2
*Example of a log entry when using Souvenir:*
Sat Nov 20 23:03:55 PST 2021 
Added journal entry for: 1 My trip to Japan (07/01/21-07/29/21)
Sat Nov 20 23:04:17 PST 2021
Added journal entry for: 2 My trip to Rome (09/10/21-10/04/21)
Sat Nov 20 23:04:34 PST 2021
Added attraction: (sang,Karaoke-doki!)
Sat Nov 20 23:04:48 PST 2021
Added attraction: (shopped,Harajuku District)
Sat Nov 20 23:05:08 PST 2021
Added eatery: (Mochi Ice Cream,Koi Sweets)
Sat Nov 20 23:05:11 PST 2021
Removed: (sang,Karaoke-doki!)
Sat Nov 20 23:09:06 PST 2021
Deleted journal entry for: 1 My trip to Japan (07/01/21-07/29/21)

## Phase 4: Task 3
If I had more time to work on the project, I would definitely refactor and reorganize a lot of the code written 
for phase 3 of the project because the methods in the class are quite messy. I would do the following:
- Extract more helper methods from the longer methods
  - Especially for the methods that are designing the visual aspects of a component (e.g. frame, menubar, button, etc.)
- Refactor the sound methods to be a class to reduce code duplication
- Change the colours to be public, so it only has to be declared once in one class instead of multiple times in 
different classes

Other than that, I did refactor the code for the categories at the beginning (after phase 2) to introduce an 
abstract class for the categories to reduce duplication.
