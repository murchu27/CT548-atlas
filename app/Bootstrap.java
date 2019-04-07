import play.db.jpa.JPABase;
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

            // Create and save new cities
            new City("Dublin", 2000).save();
            new City("Galway", 1000).save();
            new City("Paris", 4000).save();
        }

        // Check if there are no existing countries
        if (Country.count() == 0) {
            // Find an existing city
            City c1 = City.find("byName", "Dublin").first();
            City c2 = City.find("byName", "Galway").first();
            City c3 = City.find("byName", "Paris").first();

            // Create and save a new country
            Country co1 = new Country("Ireland", c1);
            co1.addArea(c2);
            co1.save();
            
            Country co2 = new Country("France", c3);
            co2.save();
            
//            AreaCatalogue cat = AreaCatalogue.getInstance();
//            cat.addCountry(co1);
//            cat.addCountry(co2);

    		for (JPABase c: Country.findAll())
    			if (c instanceof Country)
    				System.out.println(((Country)c).getName() + ((Country)c).getCapital().getName());
    		
//    		assertNotNull(cat.CountryOf.get(c1));
        }
    }
}