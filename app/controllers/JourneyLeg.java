package controllers;

//simply holds the information to be printed, no logic implemented
public class JourneyLeg {

    public JourneyLeg(City city1, City city2, Integer transportType) {
    	this.city1 = city1;
    	this.city2 = city2;
    	this.transportType = transportType;
    }

    private static String[] transportTypes = new String[] {"bus", "train", "plane"};
    private City city1, city2;
    private Integer transportType;
    
    public static String getTransportType(Integer type) {
    	if (type > transportTypes.length)
    		throw new java.lang.RuntimeException("Invalid transportType identifier");
    	return transportTypes[type];
    }
    
    public String reportLeg() {
    	return String.format("Travel from %s to %s by %s", city1.getName(), city2.getName(), getTransportType(transportType));
    }
    
    public City[] getCities() {
    	return new City[] {city1, city2};
    }
}