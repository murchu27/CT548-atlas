package planner;

import models.City;
import java.util.*;

public class Journey {

    public Journey(City city1, City city2) {
    	this.city1 = city1;
//    	this.city2 = city2;
    	legs = new ArrayList<JourneyLeg>();
    }

    private City city1/*, city2*/;
    private ArrayList<JourneyLeg> legs;

    public void addLeg(City otherCity, Integer transportType) {
        if (legs.size()==0)
        	legs.add(new JourneyLeg(city1, otherCity, transportType));
        else {
        	//get the end point of the last leg, use as the start for this leg
        	City startCity = legs.get(legs.size()-1).getCities()[1];
        	legs.add(new JourneyLeg(startCity, otherCity, transportType));
        }
    }
    
    public String reportJourney() {
    	String journey = "";
        Integer l = 0;
    	for (JourneyLeg leg : legs)
    		journey += String.format("%d. %s\n", ++l, leg.reportLeg());
    	
    	return journey.substring(0, journey.length()-1);
    }
}