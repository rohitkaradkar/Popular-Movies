# Popular Movies 
### setup

Add your TMDB api_key to ```gradle.properties``` 
```
MyTmdbApiKey = "replace-your-api-key-here"
```
>I am using ```Android Studio 2.1 preview X``` for instant run support. You can change your classpath dependencies from project level ```build.gradle``` to match your version before importing project.

### Stage 1 
>Status: Complete
* `GridLayout` for all movie posters sorted by most popular, highest rated
* Every poster corresponds to movie detail
* use of `onSaveInstanceState` and `onRestoreInstanceState`
* implementation of `parcelable` to movie info Class 
* creating custom `ArrayAdapter`

<img src="http://i.imgur.com/7knNr8A.png?2" alt="Drawing" width="300"/> <img src="http://i.imgur.com/a6LRbYx.png?1" alt="Drawing" width="300"/>

### Stage 2
>Status: Working
* Use of `SQLite` database to store movie information and minimize network calls
* creation of `ContentProvider` to serve `CRUD` operations
* display trailers in movie detail
* provide functionality to create favourite movie list, locally.

