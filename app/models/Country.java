package models;

import javax.persistence.*;
import play.db.jpa.*;
import java.util.*;

@Entity
public class Country extends PopulatedArea {
    
	//Country maintains Many-to-Many relationship with itself
    @ManyToMany
    public Set<Country> bordering;
    
    //change default length so that it is long enough to hold the information of a city
    @OneToOne
    public City capital; 
    
    public Country(String name) {
    	super(name);
    	bordering = new HashSet<Country>();
    }
    
    public Country(String name, City capital) {
    	this(name);
    	setCapital(capital);
    }
    
    @Override
    public Integer getPopulation() {
        Integer p = 0;
        for (Area a: getSubAreas())
        	if (a instanceof PopulatedArea)
        		p += ((PopulatedArea)a).getPopulation();
    	return p;
    }

    public Set<Country> listBordering() {
        return bordering;
    }

    public void addBordering(String countryName) {
        addBordering((Country)Country.find("byName", countryName).first());
    }
    
    public void addBordering(Long countryID) {
    	addBordering(Country.findById(countryID));
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
    
    public City getCapital() {
        return capital;
    }
    
    public void setCapital(City capital) {
    	this.capital = capital;
    	if (!getSubAreas().contains(capital))
    		addArea(capital);
    }
}