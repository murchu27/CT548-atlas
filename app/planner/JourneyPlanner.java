package planner;

import java.util.*;
import java.util.stream.Collectors;

import models.AreaCatalogue;
import models.City;
import models.Country;
import planner.Journey;

public class JourneyPlanner {

    public JourneyPlanner(City city1, City city2) {
    	this.city1 = city1;
    	this.city2 = city2;
    	journies = new ArrayList<Journey>();
    }

    private City city1, city2;
    private ArrayList<Journey> journies;

    public void planJourneys() {
    	//before anything else, check if city1 == city2, in which case journey does not make sense
    	if (city1.equals(city2))
    		return;
    	
    	AreaCatalogue cat = AreaCatalogue.getInstance();
    	Journey j  = new Journey();
    	
    	Country country1 = cat.CountryOf.get(city1);
    	Country country2 = cat.CountryOf.get(city2);
    	City cap1 = country1.getCapital();
    	City cap2 = country2.getCapital();
    	
    	//first, check if both are in the same country, if so, travel by bus
    	if (country1.equals(country2)) {
    		j.addLeg(new JourneyLeg(city1, city2, new Bus()));
    		journies.add(j);
    	}
    	//check if both are capitals; if so, just travel by plane
    	else if (city1.equals(cap1) && city2.equals(cap2)){
    		j.addLeg(new JourneyLeg(city1, city2, new Plane()));
    		journies.add(j);
    	}
    	//check if countries are neighbouring; if so, travel by train
    	else if (country1.isBordering(country2)) {
    		j.addLeg(new JourneyLeg(city1, city2, new Train()));
    		journies.add(j);
    	}
    	else {
	    	//if only city1 is a capital, we will travel by bus to its capital
	    	if (!city1.equals(cap1) && city2.equals(cap2)) {
	    		j.addLeg(new JourneyLeg(city1, cap1, new Bus()));//start with bus
	    		j.addLeg(new JourneyLeg(cap1, city2, new Plane()));//then plane
	    	}
	    	//similar if only city2 is a capital
	    	else if (city1.equals(cap1) && !city2.equals(cap2)) {
	    		j.addLeg(new JourneyLeg(city1, cap2, new Bus()));//start with plane
	    		j.addLeg(new JourneyLeg(cap2, city2, new Plane()));//then bus
	    	}
    		journies.add(j);

	    	//neither are capitals; in this case, figure out if the countries share a neighbour
			Collection<Country> common = country1.listBordering().stream().filter(country2.listBordering()::contains).collect(Collectors.toList());
			
			if (common.size() > 0) {//countries share at least one neighbour
				//travel through those countries by train (both legs), through their capitals say
				City cap3;
				for (Country c : common) {
					j = new Journey();
					cap3 = c.getCapital();
					j.addLeg(new JourneyLeg(city1, cap3, new Train()));
					j.addLeg(new JourneyLeg(cap3, city2, new Train()));
	    			journies.add(j);
				}
	    	}
			
			//after all these ideal cases, best we can do is bus to/from each capital, plane between capitals
			if (!city1.equals(cap1) && !city2.equals(cap2)) {
		    	j = new Journey();
	    		j.addLeg(new JourneyLeg(city1, cap1, new Bus()));//start with bus
	    		j.addLeg(new JourneyLeg(cap1, cap2, new Plane()));//then plane
	    		j.addLeg(new JourneyLeg(cap2, city2, new Bus()));//then bus
		    	journies.add(j);
			}
    	}
    }
    
    public List<String> reportPlan() {
        List<String> plan = new ArrayList<String>();
        Integer j = 0;
    	for (Journey journey : journies)
        	plan.add(String.format("OPTION %d\n%s\n\n", ++j, journey.reportJourney()));
    	
    	return plan;
    }
}