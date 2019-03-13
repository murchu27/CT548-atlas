package controllers;

import java.util.*;

public class Country extends Area {

    public Country(String name) {
    	super(name);
    	cities = new ArrayList<City>();
    	bordering = new ArrayList<Country>();
    }

    private ArrayList<City> cities;
    private ArrayList<Country> bordering;
    
    public Integer getPopulation() {
        Integer p = 0;
        for (City c: cities)
        	p += c.getPopulation();
    	return p;
    }

    public void addBordering(Integer countryID) {
    	addBordering((Country) AreaCatalogue.getAreaByID(countryID));
    }
    
    public void addBordering(String countryName) {
        addBordering((Country) AreaCatalogue.getAreaByName(countryName));
    }
    
    public void addBordering(Country other) {
    	bordering.add(other);
    	if (!other.isBordering(this))
    		other.addBordering(this);
    }

    public Boolean isBordering(Integer countryID) {
        return isBordering((Country) AreaCatalogue.getAreaByID(countryID));
    }

    public Boolean isBordering(String countryName) {
        return isBordering((Country) AreaCatalogue.getAreaByName(countryName));
    }
    
    public Boolean isBordering(Country other) {
        return bordering.contains(other);
    }

    public ArrayList<Country> listBordering() {
        return bordering;
    }
    
    public void addCity(City city) {
    	cities.add(city);
    	AreaCatalogue.CountryOf.put(city, this);
    }

    public ArrayList<City> listCities() {
    	return cities;
    }

    public City getCapital() {
        return cities.get(0);
    }
}