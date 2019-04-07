package planner;

import models.City;

public class Bus extends TransportMethod {

	@Override
	public String report(City city1, City city2) {
		return String.format("Travel from %s to %s by bus.", city1.getName(), city2.getName());
	}

}
