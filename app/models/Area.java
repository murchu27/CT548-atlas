package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Area extends Model {

    public String name;
    @OneToMany
    public Set<Area> subareas;

	public Area(String name) {
    	this.name = name;
    	subareas = new HashSet<Area>();
	}
    
	public void addArea(Area area) {
		subareas.add(area);
	}
	
    public String getName() {
        return name;
    }
    
    public Set<Area> getSubAreas() {
    	return subareas;
    }
}