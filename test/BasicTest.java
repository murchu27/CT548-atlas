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
	public void createAndRetrievePOI() {
		String poiName = "O'Connell Monument";
		new PointOfInterest(poiName).save();
		PointOfInterest poi = PointOfInterest.find("byName", poiName).first();
		
		assertNotNull(poi);
		assertEquals(poiName, poi.getName());
	}
		
    @Test
    public void createAndRetrieveCity() {
    	String cityName = "Madrid";
    	Integer cityPopulation = 400;
    	
    	new City(cityName, cityPopulation).save();
    	City city = City.find("byName", cityName).first();

    	assertNotNull(city);
    	assertEquals(cityName, city.getName());
    	assertEquals(cityPopulation, city.getPopulation());
    }
    
    @Test
    public void createAndRetrieveCountry() {
    	String cityName = "Dublin";
    	Integer cityPopulation = 20;
    	String countryName = "Ireland";
    	String borderName = "England";
    	
    	City ci = new City(cityName, cityPopulation).save();
    	Country co1 = new Country(countryName, ci);	
    	co1.setCapital(ci);
    	co1.save();
    	
    	Country co2 = new Country(borderName);
    	co2.save();
    	
    	Country country = Country.find("byName", countryName).first();
    	
    	assertNotNull(country);
    	country.save();
    	
    	country.addBordering(co2);
    	country.save();
    	co2.save();

    	assertEquals(cityPopulation, country.getPopulation());
    	assertEquals(cityName, country.getCapital().getName());
    	assertTrue(country.listBordering().contains(co2));
    	assertTrue(co2.listBordering().contains(country));
    }
}