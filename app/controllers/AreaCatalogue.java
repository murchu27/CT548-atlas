package controllers;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import models.Area;
import models.City;
import models.Country;

public class AreaCatalogue {

	//don't want others to be able to make a new AreaCatalogue
    private AreaCatalogue() {} 

    private static AreaCatalogue me;
    public static AreaCatalogue getInstance() {
    	if (me == null)
			me = new AreaCatalogue();
		return me;
    }

    private ArrayList<Country> countries = new ArrayList<Country>();
//    public static HashMap<Long, Area> areaIDs = new HashMap<Long, Area>();
//    public static HashMap<String, Area> areaNames = new HashMap<String, Area>();
    public static HashMap<City, Country> CountryOf = new HashMap<City, Country>();
    
    public void addCountry(Country country) {
        countries.add(country);
    }

    public ArrayList<Country> listCountries() {
    	return countries;
    }
    
//    public static Area getAreaByID(Long areaID) {
//    	return areaIDs.get(areaID);
//    }
//    
//    public static Area getAreaByName(String areaName) {
//    	return areaNames.get(areaName);
//    }
    
	public static void main(String[] args) {
    	AreaCatalogue cat = AreaCatalogue.getInstance();

    	//read from database
		JSONTokener parser = new JSONTokener(cat.getClass().getResourceAsStream("/data.json"));
		JSONObject data = new JSONObject(parser);

		JSONArray countriesArray = data.getJSONArray("countries");
		Country country, neighbour;
		JSONObject JSONcountry, JSONcity;
		JSONArray citiesArray, borderingArray;
		for(int i = 0; i < countriesArray.length(); i++) {
			JSONcountry = countriesArray.getJSONObject(i);
			country = new Country(JSONcountry.getString("name"));

			citiesArray = JSONcountry.getJSONArray("cities");
			for(int j = 0; j < citiesArray.length(); j++) {
				JSONcity = citiesArray.getJSONObject(j);
				country.addCity(new City(JSONcity.getString("name"), JSONcity.getInt("population")));
			}

			borderingArray = JSONcountry.getJSONArray("bordering");
			for(int k = 0; k < borderingArray.length(); k++) {
//				neighbour = (Country) AreaCatalogue.getAreaByName(borderingArray.getString(k));
				neighbour = (Country)Country.find("byName", borderingArray.getString(k)).first();
				if (neighbour!=null)
					country.addBordering(neighbour);
			}
			
			cat.addCountry(country);
		}
		
		//////////////////////////
		// FUNCTIONAL REQUIREMENTS
		//////////////////////////
		
		//1. List all the countries
		
    	ArrayList<Country> ctrs = cat.listCountries();
    	System.out.print("1. The countries in the database are: ");
    	for (Country c: ctrs)
    		System.out.print(c.getName() + "   ");
    	System.out.println('\n');
    	
    	//2. For every country, list the bordering countries, and answer the question whether 2 countries are bordering or not
    	System.out.println("2. Bordering countries");
    	List<Country> neighbours;
//    	(Country) getAreaByName("France");
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
//    	(City) getAreaByName("Madrid");
    	City testCity = (City) City.find("byName", "Madrid").first();
    	System.out.println(String.format("The population of %s is %d.", testCity.getName(), testCity.getPopulation()));
//    	(Country) getAreaByName("United States");
 		Country testCountry = (Country)Country.find("byName", "United States").first();
    	System.out.println(String.format("The population of %s is %d.\n", testCountry.getName(), testCountry.getPopulation()));
    	
    	//4. List all the cities in a country, or retrieve only the capital
    	System.out.println("4. Cities & capitals");
    	System.out.print(String.format("The cities of %s are: ", testCountry.getName()));
    	for (City c: testCountry.listCities())
        	System.out.print(c.getName() + "   ");
    	System.out.println(String.format("\nThe capital of %s is: %s\n", testCountry.getName(), testCountry.getCapital().getName()));
    	
    	//5. For any two cities, print at least one travel plan
    	System.out.println("5. Travel plan");
    	Scanner input = new Scanner(System.in);
    	System.out.print("Enter the name of city 1: ");
//    	(City) getAreaByName(input.nextLine());
    	City city1 = (City)City.find("byName", input.nextLine()).first();
    	System.out.print("Enter the name of city 2: ");
//    	(City) getAreaByName(input.nextLine());
    	City city2 = (City)City.find("byName", input.nextLine()).first();
    	input.close();

    	JourneyPlanner jp = new JourneyPlanner(city1, city2);
    	jp.planJourneys();
    	System.out.println(jp.reportPlan());
	}
}