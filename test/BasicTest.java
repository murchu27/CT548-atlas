import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	// This function will first in this test
	@Before
    public void setup() {
		// Clear the database
        Fixtures.deleteDatabase();
    }
		
    @Test
    public void createAndRetrieveCity() {
    	String cityName = "Madrid";
    	Integer cityPopulation = 400;
    	
    	new City(cityName, cityPopulation).save();
    	City city = City.find("byPopulation", cityPopulation).first();

    	assertNotNull(city);
    	assertEquals(cityName, city.getName());
    	assertEquals(cityPopulation, city.getPopulation());
    }
    
    @Test
    public void createAndRetrieveCountry() {
    	String cityName = "Dublin";
    	Integer cityPopulation = 20;
    	String countryName = "Ireland";
    	
    	City ci = new City(cityName, cityPopulation).save();
    	Country co = new Country(countryName);	
    	co.addCity(ci);
    	co.save();
    	
    	Country country = Country.find("byName", countryName).first();

    	assertNotNull(country);
    	assertEquals(ci.getPopulation(), country.getPopulation());
    }
}
