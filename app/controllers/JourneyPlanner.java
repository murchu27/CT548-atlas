package controllers;

import java.util.*;
import java.util.stream.Collectors;

public class JourneyPlanner {

    public JourneyPlanner(City city1, City city2) {
    	this.city1 = city1;
    	this.city2 = city2;
    	journies = new ArrayList<Journey>();
    }

    private City city1, city2;
    private ArrayList<Journey> journies;

    public void planJourneys() {
    	Country country1 = AreaCatalogue.CountryOf.get(city1);
    	Country country2 = AreaCatalogue.CountryOf.get(city2);
    	City cap1 = country1.getCapital();
    	City cap2 = country2.getCapital();
    	
    	Journey j = new Journey(city1,city2);
    	//first, check if both are in the same country, if so, travel by bus
    	if (country1.equals(country2)) {
    		j.addLeg(city2, 0);
    		journies.add(j);
    	}
    	//check if both are capitals; if so, just travel by plane
    	else if (city1.equals(cap1) && city2.equals(cap2)){
    		j.addLeg(city2, 2);
    		journies.add(j);
    	}
    	//check if countries are neighbouring; if so, travel by train
    	else if (country1.isBordering(country2)) {
    		j.addLeg(city2, 1);
    		journies.add(j);
    	}
    	else {
	    	//if only city1 is a capital, we will travel by bus to its capital
	    	if (!city1.equals(cap1) && city2.equals(cap2)) {
	    		j.addLeg(cap1, 0);//start with bus
	    		j.addLeg(city2, 2);//then plane
	    		journies.add(j);
	    	}
	    	//similar if only city2 is a capital
	    	else if (city1.equals(cap1) && !city2.equals(cap2)) {
	    		j.addLeg(cap2, 2);//start with plane
	    		j.addLeg(city2, 0);//then bus
	    		journies.add(j);
	    	}
	    	
	    	//neither are capitals; in this case, figure out if the countries share a neighbour
			Collection<Country> common = country1.listBordering().stream().filter(country2.listBordering()::contains).collect(Collectors.toList());
			
			if (common.size() > 0) {//countries share at least one neighbour
				//travel through those countries by train (both legs), through their capitals say
				for (Country c : common) {
					j = new Journey(city1,city2);
	    			j.addLeg(c.getCapital(), 1);
	    			j.addLeg(city2, 1);
	    			journies.add(j);
				}
	    	}
			
			//after all these ideal cases, best we can do is bus to/from each capital, plane between capitals
			if (!city1.equals(cap1) && !city2.equals(cap2)) {
		    	j = new Journey(city1,city2);
		    	j.addLeg(cap1, 0);
		    	j.addLeg(cap2, 2);
		    	j.addLeg(city2, 0);
		    	journies.add(j);
			}
    	}
    }
    
    public String reportPlan() {
        String plan = "";
        Integer j = 0;
    	for (Journey journey : journies)
        	plan += String.format("OPTION %d\n%s\n\n", ++j, journey.reportJourney());
    	
    	return plan.substring(0, plan.length()-2);
    }
}