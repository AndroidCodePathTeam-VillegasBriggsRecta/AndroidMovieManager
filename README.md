# Android Movie Manager

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
The Movie Manager allows a movie to organize their movie collection and determine which movies to add to their collection based on other users reviews.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Android Application, Organizer, Cataloger
- **Mobile:** Yes
- **Story:** None
- **Market:** Niche
- **Habit:** If you have a growing film collection, this might be a tool you frequent
- **Scope:** Who knows

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User sign up/sign in/sign out
* Present current DVD releases
* Search for a film, by title, present movie details and user reviews
* Add a film to a catalog
* View catalog, allow for sorting
* Add a film to a wishlist
* View wishlist, allow for sorting
* Review a film

**Optional Nice-to-have Stories**

* Get prices for DVDs from several retailers for price comparison
* Search for a film by genre/year/rating
* Send email notifications of new releases, catalog updates


### 2. Screen Archetypes

* Sign In Screen - See Wireframe Screen #1
* Sign Up Screen - See Wireframe Screen #2
* Main Screen (New Releases Fragment) - See Wireframe Screen #3
* Main Screen (Catalog/Wish List Screen) - See Wireframe Screen #4
* Search Results Screen - See Wireframe Screen #5
* Individual Film Screen - See Wireframe Screen #6
* User Profile Screen
  * As a user I would like to see my profile information and update it if need by
  * As a user I would like to see my most recent additions to my catalog and wishlist
  * As a user I would like to see an overview report of my lists (# of films in catalog/wishlist, # of reviews written)
  * As a user I would like to see a list of reviews I have written

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* [fill out your first tab]
* [fill out your second tab]
* [fill out your third tab]

**Flow Navigation** (Screen to Screen)

* [list first screen here]
   * [list screen navigation here]
   * ...
* [list second screen here]
   * [list screen navigation here]
   * ...

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="wireframe-sketch.PNG" width=600>

## Schema 
### Models
#### Post

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | author        | Pointer to User| image author |
   | image         | File     | image that user posts |
   | caption       | String   | image caption by author |
   | commentsCount | Number   | number of comments that has been posted to an image |
   | likesCount    | Number   | number of likes for the post |
   | createdAt     | DateTime | date when post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
