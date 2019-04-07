package planner;

import models.City;
import java.util.*;

public class Journey {

    private List<JourneyLeg> legs = new ArrayList<JourneyLeg>();

    public void addLeg(JourneyLeg leg) {
        legs.add(leg);
    }
    
    public List<JourneyLeg> getJourneyLegs() {
    	return Collections.unmodifiableList(legs);
    }
    
    public String reportJourney() {
    	String journey = "";
        Integer l = 0;
    	for (JourneyLeg leg : legs)
    		journey += String.format("%d. %s\n", ++l, leg.reportLeg());
    	
    	return journey.substring(0, journey.length()-1);
    }
}