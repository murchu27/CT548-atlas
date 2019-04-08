## CT548 Assignment 2
### Name: Michael Murphy
### Student Number: 15300856

### Assignment notes
#### 1. Assignment directory

Directory structure is as follows:

	atlas (directory root)
	|- README.txt //this file
	|- data.json //custom JSON database
	|- \app //application source code
	|	|- Bootstrap.java //initial database population
	|	|- \controllers
	|	|	|- Application.java
	|	|	|- Cities.java
	|	|	|- Countries.java
	|	|	|- jsonLoader.java
	|	|	|- planner.java
	|	|- \exceptions
	|	|	|- DataFormatException.java
	|	|	|- JsonExtractor.java
	|	|	|- SameCityException.java
	|	|- \models
	|	|	|- Area.java
	|	|	|- City.java
	|	|	|- Country.java
	|	|	|- PointOfInterest.java
	|	|	|- PopulatedArea.java
	|	|- \planner //used to plan journies
	|	|	|- Bus.java
	|	|	|- Journey.java
	|	|	|- JourneyLeg.java
	|	|	|- JourneyPlanner.java
	|	|	|- Plane.java
	|	|	|- Train.java
	|	|	|- TransportMethod.java
	|	|- \views
	|	|	|- main.html
	|	|	|- \Application
	|	|	|	|- index.html
	|	|	|	|- show.html
	|	|	|- \errors
	|	|	|	|- 404.html
	|	|	|	|- 500.html
	|	|	|- \jsonLoader
	|	|	|	|- loader.html
	|	|	|- \planner
	|	|	|	|- JourneyPlanner.html
	|- \conf //configuration files
	|	|- application.conf
	|	|- dependencies.yml
	|	|- messages
	|	|- routes
	|- \documentation ...
	|- \public ...
	|- \test //unit and functional tests
	|	|- Application.test.html
	|	|- ApplicationTest.java //functional tests
	|	|- BasicTest.java //unit tests
	|	|- data.yml

#### 2. Running the application
The application is hosted at port 80: hence, once `play run` is executed, it can simply be visited at `localhost`.

#### 3. Software and package dependencies  
As per the *dependencies.yml* file, the application depends on:

- The Play! Framework (`play`)
- The Play! Documentation Viewer module (`play -> docviewer`)
- The Play! CRUD module (`play -> crud`)
- The Play! Secure module (`play -> secure`)
- The JSON Java library (`play -> json 20180813`)

#### 4. Unit and Functional Tests
All unit tests are written in *BasicTest.java*:

- `createAndRetrievePOI`
- `createAndRetrieveCity`
- `createAndRetrieveCountry`

All functional tests are written in *ApplicationTest.java*:

- `testThatIndexPageWorks`
- `testThatJourneyPlannerPageWorks`
- `testAdminSecurity`
- `testLoaderSecurity`

#### 5. JSON Data
JSON database contains 12 countries, each with 2 cities. 

- The file has a `countries` array  of *Country* objects, whose elements describe the 12 countries. 
	- Each of these elements has `name`, `bordering`, and `cities`.
	- `name` is a String object, containing the country name.
	- `bordering` is an array of Strings, whose elements are the names of countries which are bordered by this country
	- `cities` is an array of *City* objects, whose elements describe the 2 cities of this country.
		- Each of these elements has `name` and `population`.
		- `name` is a String object, containing the city name.
		- `population` is an Integer object, containing the population of the city.

#### 6. Issue with file uploading
When attempting to upload a file through the *loader.html* page, a strange issue is encountered. The form should send the file to the `jsonLoader.uploadJSON(File jsonData,...)` action method. However, no matter what file is uploaded, **a null pointer exception occurs**. 

I tried using different browsers, and even making a fresh project, but I could not get through this issue. The contents of the *loader.html* page and the `jsonLoader.uploadJSON` method are identical to the ones used in the tutorial projects in labs, so I really can't understand what the problem is. 

To ensure there was nothing was wrong with my setup, I used the following workaround:

- The input tag in *loader.html* is adjusted to `<input type="text"... />` instead of `<input type="file"... />`
- The signature of `jsonLoader.uploadJSON`is changed to take in `String jsonData` instead of `File jsonData`.
- While on the webpage, the storage location of *data.json* is manually input as a string. This location is `{path to parent directory}\atlas\data.json`.

This workaround shows that my source code does not appear to have issue. It also successfully updates the database as required. 

This issue appeared only days before the submission deadline, seemingly out of nowhere, and I cannot fathom why it is happening. The one thing I didn't get a chance to try, as I am currently away, was downloading the application onto another machine with a fresh installation of Play!. I hope that due consideration will be given under the circumstances.