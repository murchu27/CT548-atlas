package controllers;

import play.mvc.Controller;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import models.*;
import planner.*;

public class planner extends Controller {
	
	public static void JourneyPlanner(List<String> plan) {
        List<City> cities = City.findAll();

		render(cities, plan);
	}
	
	public static void planJourney(Long startCityId, Long endCityId) {
		City city1 = City.findById(startCityId);
		City city2 = City.findById(endCityId);
		
		assertNotNull(city1);
		assertNotNull(city2);
		System.out.println("go " + city1.getName());
		
		assertNotNull(AreaCatalogue.getCountryOf(city1));
		
		JourneyPlanner jp = new JourneyPlanner(city1, city2);
		jp.planJourneys();
		List<String> plan = jp.reportPlan();
		
		JourneyPlanner(plan);
	}
}
