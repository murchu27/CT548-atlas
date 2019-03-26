package controllers;

import models.City;
import play.mvc.With;

@With(Secure.class)
@CRUD.For(City.class)
public class Cities extends CRUD {
	
}