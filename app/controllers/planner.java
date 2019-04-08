package controllers;

import play.mvc.Controller;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import exceptions.SameCityException;
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
		
		assertNotNull(city1.getCountry());
		
		JourneyPlanner jp = new JourneyPlanner(city1, city2);
		try {
			jp.planJourneys();
			List<String> plan = jp.reportPlan();
			flash.success("Journey successfully calculated.");
			JourneyPlanner(plan);
		} catch (SameCityException e) {
			flash.error("Cannot travel from a city to itself!");
			JourneyPlanner(null);
		}
		
	}
}
