import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import models.Area;
import models.AreaCatalogue;
import models.City;
import models.Country;
import planner.JourneyPlanner;

public class OldAreaCatalogue {    
	public static void main(String[] args) {
    	AreaCatalogue cat = AreaCatalogue.getInstance();
		
		//1. List all the countries
    	ArrayList<Country> ctrs = cat.listCountries();
    	System.out.print("1. The countries in the database are: ");
    	for (Country c: ctrs)
    		System.out.print(c.getName() + "   ");
    	System.out.println('\n');
    	
    	//2. For every country, list the bordering countries, and answer the question whether 2 countries are bordering or not
    	System.out.println("2. Bordering countries");
    	Set<Country> neighbours;
    	Country testNeighbour = (Country) Country.find("byName", "France").first();
    	for (Country c: ctrs) {
        	neighbours = c.listBordering();
        	if (neighbours.size() == 0) {
        		System.out.println(c.getName() + " is not bordered by any countries in the database.\n");
        		continue;
        	}
    		System.out.print(c.getName() + " is bordered by: ");
    		for (Country b: neighbours)
    			System.out.print(b.getName() + "   ");
    		System.out.println();
    		
    		System.out.println(String.format("%s is %sbordered by France.\n", c.getName(), (c.isBordering(testNeighbour)? "" : "not ")));	
    	}
    	
    	//3. Retrieve the population of a city or country
    	System.out.println("3. Population getter");    	
    	City testCity = City.find("byName", "Madrid").first();
    	System.out.println(String.format("The population of %s is %d.", testCity.getName(), testCity.getPopulation()));
 		Country testCountry = Country.find("byName", "United States").first();
    	System.out.println(String.format("The population of %s is %d.\n", testCountry.getName(), testCountry.getPopulation()));
    	
    	//4. List all the cities in a country, or retrieve only the capital
    	System.out.println("4. Cities & capitals");
    	System.out.print(String.format("The cities of %s are: ", testCountry.getName()));
//    	for (City c: testCountry.listCities())
//        	System.out.print(c.getName() + "   ");
    	System.out.println(String.format("\nThe capital of %s is: %s\n", testCountry.getName(), testCountry.getCapital().getName()));
    	
    	//5. For any two cities, print at least one travel plan
    	System.out.println("5. Travel plan");
    	City city1 = City.find("byName", "Dublin").first();
    	City city2 = City.find("byName", "Galway").first();

    	JourneyPlanner jp = new JourneyPlanner(city1, city2);
    	jp.planJourneys();
    	System.out.println(jp.reportPlan());
	}
}