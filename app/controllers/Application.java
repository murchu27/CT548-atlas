package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import models.*;

public class Application extends Controller {

    public static void index() {
    	List<Country> countries = Country.findAll();
        List<City> cities = City.findAll();
        
        render(countries, cities);
    }
    
    public static void show(String countryName) {
    	Country country = Country.find("byName", countryName).first();
    	assertNotNull(country);
    	
    	Set<Country> bordering = country.listBordering();
    	Set<Area> cities = country.getSubAreas();
    	render(country, bordering, cities);
    }
}