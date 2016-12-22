# Popular Movies 
It is a first project in Udacity's Android Nanodegree where you get to learn basic of Android while creating an app that displays list of movies along with their information.

### setup
* Download and import project in Android Studio.
* get api-key of [TMDB (The Movie Database).](https://developers.themoviedb.org/3/getting-started)
* add your TMDB api-key to `gradle.properties`

### Functions
* `GridLayout` for all movie posters sorted by most popular, highest rated.
* Every poster corresponds to movie detail.
* use of `onSaveInstanceState` and `onRestoreInstanceState`.
* implementation of `parcelable` to movie info Class. 
* creating custom `ArrayAdapter`.
* Use of `SQLite` database to store movie information and minimize network calls.
* creation of `ContentProvider` to serve `CRUD` operations.
* display trailers in movie detail.
* provide functionality to create favourite movie list, locally.
