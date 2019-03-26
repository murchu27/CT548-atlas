package models;
import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

import controllers.AreaCatalogue;

@Entity
public class City extends Area {

    public City(String name, Integer population) {
    	super(name);
    	this.population = population;
    	points_of_interest = new ArrayList<String>();
    }

    public Integer population;
    public ArrayList<String> points_of_interest;

    public Integer getPopulation() {
        return population;
    }

    public void addPOI(String name) {
        points_of_interest.add(name);
    }

    public ArrayList<String> listPOIs() {
        return points_of_interest;
    }
    
    public Country getCountry() {
    	return AreaCatalogue.CountryOf.get(this);
    }
    
    public boolean equals(Object obj) {
    	if(this==obj) return true;
    	if(obj == null || obj.getClass()!= this.getClass()) return false; 
    	
    	City other = (City) obj;
    	if (other.getName() != this.getName()) return false; 
    	if (other.getId() != this.getId()) return false; 
    	if (other.getPopulation() == this.getPopulation()) return false; 
    	return (other.listPOIs() == this.listPOIs());
    }
    
    public int hashCode() {
    	return Arrays.hashCode(new Object[] {name, this.getId(), population, points_of_interest});
    }
}