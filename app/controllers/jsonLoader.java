package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import exceptions.DataFormatException;
import play.mvc.Controller;
import play.mvc.With;

import models.*;

@With(Secure.class)
public class jsonLoader extends Controller{	
    public static void loader() {
    	render();
    }
	
	public static void uploadJSON(String jsonData, boolean clearData) {
    	InputStream input = null;
		
		try {
			if (clearData) play.test.Fixtures.deleteDatabase();
			
			input = new FileInputStream(jsonData);
			populateDatabase(input);

			flash.success("File uploaded successfully and database updated");
		} catch (FileNotFoundException fe) {
			flash.error("Failed to locate the uploaded JSON file.");
			fe.printStackTrace();
		} catch (DataFormatException de) {
			flash.error("Data formating error: " + de.getMessage());
			de.printStackTrace();
		} catch (JSONException je) {
			flash.error("JSON library exception raised while loading the file. ");
			je.printStackTrace();
		} finally {
			try {
				if (! (null == input)) input.close();
			}catch (IOException ioe) {
			}
		}
    	loader();
    }

	private static void populateDatabase(InputStream input) 
		throws DataFormatException, FileNotFoundException, JSONException {
			
			// Parse the JSON file and make sure it's not completely broken.
			JSONTokener parser = new JSONTokener(input);
			JSONObject data = new JSONObject(parser);

			// Check if file has the key "countries"
			if (!data.has("countries"))
				throw new DataFormatException("No key 'countries', nothing I can do with these data.", data);
			
			// Load the array of countries
			JSONArray countriesArray = data.getJSONArray("countries");
			
			// Check if the data for "countries" is a an array
			if (!(countriesArray instanceof JSONArray))
				throw new DataFormatException("Key 'countries' must point to a JSON array.", data);
			
			
			// Loop through all countries
			for (int i = 0; i < countriesArray.length(); i++) {
				JSONObject countryJSON = countriesArray.getJSONObject(i);
				
				if (!(countryJSON.has("name") && countryJSON.get("name") instanceof String))
					throw new DataFormatException("All countries must be named.", data);
				
				String countryName = countryJSON.getString("name");
				// Check if there is already a country with this name in database
				if (null != Country.find("byName", countryName).first())
					throw new DataFormatException("There is already an existing country called " + countryName, data);

				Country country = new Country(countryName);
				country.save();
							
				// Scan the cities of the current country in JSON file
				if (countryJSON.has("cities") && countryJSON.get("cities") instanceof JSONArray) {

					JSONArray citiesArray = countryJSON.getJSONArray("cities");

					for (int j = 0; j < citiesArray.length(); j++) {
						JSONObject cityJSON = citiesArray.getJSONObject(j);

						if (!cityJSON.has("name") && cityJSON.get("name") instanceof String)
							throw new DataFormatException("Missing city name(s) in country " + countryName, data);

						if (!cityJSON.has("population") && cityJSON.get("population") instanceof Integer)
							throw new DataFormatException("Missing city population(s) in country " + countryName, data);

						String cityName = cityJSON.getString("name");
						Integer cityPopulation = cityJSON.getInt("population");

						if (null != City.find("byName", cityName).first()) {
							throw new DataFormatException("There is already an existing city called " + cityName, data);
						}

						// Create and save to database
						City city = new City(cityName, cityPopulation);
						city.save();

						country.addArea(city);
						if (j==0) //first city is capital
							country.setCapital(city);
						country.save();
					}
				}
				
				//finally, check for this country's bordering countries (which may not exist yet)
				if (countryJSON.has("bordering") && countryJSON.get("bordering") instanceof JSONArray) {

					JSONArray borderArray = countryJSON.getJSONArray("bordering");
					
					Country neighbour;
					for (int k = 0; k < borderArray.length(); k++) {
						neighbour = Country.find("byName", borderArray.getString(k)).first();
						if (neighbour!=null) {
							neighbour.save();
							country.addBordering(neighbour);
							country.save();
							neighbour.save();
						}
					}	
				}
			}
		}
}


