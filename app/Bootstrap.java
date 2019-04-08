import play.db.jpa.JPABase;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

import static org.junit.Assert.assertNotNull;

import models.City;
import models.Country;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
    public void doJob() {
        // Clear all data 
        Fixtures.deleteDatabase();

        // Check if there are no existing cities
        if(City.count() == 0 && Country.count() == 0) {
            // Create and save new cities            
            City c1 = new City("Dublin", 1000);
            City c2 = new City("Galway", 2000);
            City c3 = new City("Paris", 3000);
            City c4 = new City("Lyon", 4000);
            City c5 = new City("Rome", 5000);
            City c6 = new City("Milan", 6000);
            City c7 = new City("Madrid", 7000);
            City c8 = new City("Barcelona", 8000);
            c1.save();
            c2.save();
            c3.save();
            c4.save();
            c5.save();
            c6.save();
            c7.save();
            c8.save();
            
            // Create and save their countries
            Country co1 = new Country("Ireland", c1);
            Country co2 = new Country("France", c3);
            Country co3 = new Country("Italy", c5);
            Country co4 = new Country("Spain", c7);
            co1.addArea(c2);
            co2.addArea(c4);
            co3.addArea(c6);
            co4.addArea(c8);
            co1.save();
            co2.save();
            co3.save();
            co4.save();
            c1.save();
            c2.save();
            c3.save();
            c4.save();
            c5.save();
            c6.save();
            c7.save();
            c8.save();
            
            // add bordering relationships
            co2.addBordering(co3);
            co2.addBordering(co4);
            co2.save();
            co3.save();
            co4.save();
        }
    }
}