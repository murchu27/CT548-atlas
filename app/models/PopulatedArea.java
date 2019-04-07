package models;

import java.util.Arrays;
import javax.persistence.*;
import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public abstract class PopulatedArea extends Area {

	@Required
	public int population;
	
	public PopulatedArea(String name) {
		super(name);
	}
	
	public abstract Integer getPopulation();
	
	public boolean equals(Object obj) {
    	if(this==obj) return true;
    	if(obj == null || obj.getClass() != this.getClass()) return false; 
    	
    	PopulatedArea other = (PopulatedArea) obj;
    	return (other.getName() == this.getName() && other.getPopulation() == this.getPopulation());
    }
    
    public int hashCode() {
    	return Arrays.hashCode(new Object[] {name, population});
    }
	
}
