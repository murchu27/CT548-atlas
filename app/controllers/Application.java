package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	List<Country> countries = Country.findAll();
        List<City> cities = City.findAll();
        
        render(countries, cities);
    }
    
    public static void show(String countryName) {
    	Country country = Country.find("byName", countryName).first();
    	
    	Set<Country> bordering = country.listBordering();
    	Set<Area> cities = country.getSubAreas();
    	render(country, bordering, cities);
    }
}