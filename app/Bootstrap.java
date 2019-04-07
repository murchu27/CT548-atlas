import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import static org.junit.Assert.assertNotNull;

import models.AreaCatalogue;
import models.City;
import models.Country;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
        // Clear all data 
        Fixtures.deleteDatabase();

        // Check if there are no existing cities
        if(City.count() == 0) {

            // Create and save two new cities
            new City("Dublin", 2000).save();
            new City("Galway", 1000).save();
        }

        // Check if there are no existing countries
        if (Country.count() == 0) {
            // Find an existing account
            City c1 = City.find("byName", "Dublin").first();
            City c2 = City.find("byName", "Galway").first();

            // Create and save a new country
            Country co = new Country("Ireland", c1);
            co.addArea(c2);
            co.save();
            
            AreaCatalogue cat = AreaCatalogue.getInstance();
            cat.addCountry(co);

    		for (Country c: cat.listCountries())
    			System.out.println(c.getName() + c.getCapital().getName());
    		
    		assertNotNull(cat.CountryOf.get(c1));
        }
    }
}