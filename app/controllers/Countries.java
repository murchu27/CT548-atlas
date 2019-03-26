package controllers;

import models.Country;
import play.mvc.With;

@With(Secure.class)
@CRUD.For(Country.class)
public class Countries extends CRUD {
	
}