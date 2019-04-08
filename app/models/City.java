package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;


@Entity
public class City extends PopulatedArea {

    public City(String name, Integer population) {
    	super(name);
    	setPopulation(population);
    }

    public Integer getPopulation() {
        return population;
    }
    
    public void setPopulation(Integer population) {
    	if (population < 0)
    		throw new IllegalArgumentException("Population cannot be less than zero.");
    	this.population = population;
    }
    
    public Country getCountry() {
    	Area a = getSuperArea();
    	if (a instanceof Country)
    		return (Country)this.getSuperArea();
    	else
    		return null;
    }
}