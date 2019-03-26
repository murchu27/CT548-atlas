package models;
import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

import controllers.AreaCatalogue;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Area extends Model {

    public String name;

	public Area(String name) {
    	this.name = name;
//    	AreaCatalogue.areaIDs.put(getId(), this);
//    	AreaCatalogue.areaNames.put(name, this);
	}
    
    public String getName() {
        return name;
    }
    
    public abstract Integer getPopulation();
}