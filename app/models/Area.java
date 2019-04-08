package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Area extends Model {

    public String name;
    @OneToMany(mappedBy="superArea", cascade = CascadeType.ALL)
    public Set<Area> subAreas;
    @ManyToOne
    @JoinColumn(name="FK_SuperAreaId")
    public Area superArea;
    
	public Area(String name) {
    	this.name = name;
    	this.subAreas = new HashSet<Area>();
	}
    
	public void addArea(Area area) {
		subAreas.add(area);
		area.setSuperArea(this);
	}
	
	public void setSuperArea(Area area) {
		superArea = area;
	}
	
    public String getName() {
        return name;
    }
    
    public Set<Area> getSubAreas() {
    	return subAreas;
    }
    
    public Area getSuperArea() {
    	return superArea;
    }
}