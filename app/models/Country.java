package models;
import javax.persistence.*;
import play.db.jpa.*;

import java.util.*;

import controllers.AreaCatalogue;

@Entity
public class Country extends Area {

	//Country maintains One-to-Many relationship with cities
    @OneToMany
    public List<City> cities;
    
	//Country maintains Many-to-Many relationship with itself
    @OneToMany
    public List<Country> bordering;
    
    public City capital;
    
    public Country(String name) {
    	super(name);
    	cities = new ArrayList<City>();
    	bordering = new ArrayList<Country>();
    }
    
    public Country(String name, City capital) {
    	this(name);
    	setCapital(capital);
    }
    
    public Integer getPopulation() {
        Integer p = 0;
        for (City c: cities)
        	p += c.getPopulation();
    	return p;
    }

    public List<Country> listBordering() {
        return bordering;
    }

    public void addBordering(Long countryID) {
    	addBordering(Country.findById(countryID));
    }
    
    public void addBordering(String countryName) {
        addBordering((Country)Country.find("byName", countryName).first());
    }
    
    public void addBordering(Country other) {
    	bordering.add(other);
    	if (!other.isBordering(this))
    		other.addBordering(this);
    }

    public Boolean isBordering(Long countryID) {
        return isBordering(Country.findById(countryID));
    }

    public Boolean isBordering(String countryName) {
        return isBordering((Country)Country.find("byName", countryName).first());
    }
    
    public Boolean isBordering(Country other) {
        return bordering.contains(other);
    }
    
    public void addCity(City city) {
    	cities.add(city);
    	AreaCatalogue.CountryOf.put(city, this);
    }

    public List<City> listCities() {
    	return cities;
    }

    public City getCapital() {
        return capital;
    }
    
    public void setCapital(City capital) {
    	this.capital = capital;
    	addCity(capital);
    }
}