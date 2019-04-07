package planner;

import models.City;

public abstract class TransportMethod {
	public abstract String report(City city1, City city2);
}
