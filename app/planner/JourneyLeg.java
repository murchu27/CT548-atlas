package planner;

import models.City;

//simply holds the information to be printed, no logic implemented
public class JourneyLeg {

    public JourneyLeg(City city1, City city2, TransportMethod transport) {
    	this.city1 = city1;
    	this.city2 = city2;
    	this.transport = transport;
    }

    private City city1, city2;
    private TransportMethod transport;
    
    public String reportLeg() {
    	return transport.report(city1, city2);
    }
    
    public City[] getCities() {
    	return new City[] {city1, city2};
    }
    
    public TransportMethod getTransportMethod() {
    	return transport;
    }
}