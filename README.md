## CT548 Assignment 1
### Name: Michael Murphy
### Student Number: 15300856

### Assignment notes:
1. Assignment directory structure is as follows

\assignment1_15300856
|- README.txt //this file
|- \lib //external libraries
|	|- json.jar //JSON library for permanent storage implementation
|- \src //source files
|	|- data.json //JSON database
|	|- \atlas //java files in atlas package
|	|	|- Area.java
|	|	|- AreaCatalogue.java //contains main() method
|	|	|- City.java
|	|	|- Country.java
|	|	|- Journey.java
|	|	|- JourneyLeg.java
|	|	|- JourneyPlanner.java
|- \uml //UML class and use case diagrams
|	|- atlas.mdj //StarUML Model file
|	|- atlas_class.jpg //class diagram
|	|- atlas_usecase.jpg //use case diagram

2. Windows Command Prompt usage  
Source code may be compiled in \src folder using the command:
`javac -cp ..\lib\json.jar atlas\*.java`
The `main()` method is then executed using the command:
`java -cp .;..\lib\json.jar atlas.AreaCatalogue`

3. `main()` method calls the JourneyPlanner, and asks the user to input city names at runtime.

4. JSON database at \src\data.json contains 12 countries, each with 2 cities. It is used to create objects at runtime, but I could not figure out how to persist new countries.

5. UML diagrams drawn using StarUML 3.0.2.

6. The City and Country classes extend Area. Area can further be extended by other area types like regions, continents, etc.