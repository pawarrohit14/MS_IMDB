#RohitIMDB
Used MVVM , Dagger2 , LiveData and Room.

### Project Overview
In this project I have consumed IMDB APIs to build a simple app that have the following features
* Fetch Popular Movies
* Show movie details
* Cache data offline. 

## Model
* Model Represents the data and business logic of the app.
* Room db is used to cache the data locally and provides the data for the view.

## View
* MoviesActivity: Displays list of popular movies
* MovieDetailsActivity: Displays movie details
* MoviesAdapter: inflates and displays moviesList
* OnMovieClickListener: responsible for interaction between MoviesAdapter


## ViewModel
* MoviesViewModel: MoviesActivity listens to movies changes that will happen when MoviesRepository is invoked to fetch the data from Remote service / Local DB

## Repository 
* I have used repository pattern to interact with the remote service and our local db
* The repository saves results into the database.
* MoviesRepository: is being used by MoviesViewModel to fetch the list of movies.

## NetworkBoundResource
A helper class that is used to take the decision of loading the data from the local db or from remote service.

NetworkBoundResource is using the local db as it's single source of truth.For example It starts by observing the database for list of movies resource.
When the entry is loaded from the database for the first time, NetworkBoundResource checks whether the result is good enough to be dispatched or that it should be re-fetched from the network.

## Database 
* using room database
* MovieDao: is used to insert/fetch our list of movies
* MovieDatabase: is responsible to provide us one and only object to access our db.
* AppExecutors: is used to write and read from db.

## Api and Base classes
* ApiResponse: a generic class for handling responses from retrofit 
* RestApiService: provides and end point for our remote service
* BaseActivity: is extended by any activity in the app. it declares unified structure and contains common methods for our activities.
* BaseViewHolder: the same as BaseActivity but for ViewHolder.

## Utils
* LiveDataCallAdapter and LiveDataCallAdapterFactory: is used to get LiveData as call back from retrofit.
* RefreshRateLimiter: is a factor in refreshing data decision.

## Dependency Injection
Used Dagger lib