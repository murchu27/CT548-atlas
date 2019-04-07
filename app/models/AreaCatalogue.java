package models;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import planner.JourneyPlanner;

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
    public static HashMap<City, Country> CountryOf = new HashMap<City, Country>();
    
    public void addCountry(Country country) {
    	System.out.println("adding country");
        countries.add(country);
        for (Area a: country.getSubAreas()) {
        	if (a instanceof City)        	
        		addCity((City)a, country);
        }    
    }

    private void addCity(City city, Country country) {
    	System.out.println("adding city");
    	CountryOf.put(city, country);
    }

    public static Country getCountryOf(City city) {
    	return CountryOf.get(city);
    }
    
    public ArrayList<Country> listCountries() {
    	return countries;
    }
}