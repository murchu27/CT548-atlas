package models;
import javax.persistence.*;

import play.db.jpa.*;


@Entity
public class PointOfInterest extends Area {

	public PointOfInterest(String name) {
		super(name);
	}

}
