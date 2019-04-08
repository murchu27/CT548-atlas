import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
    
    @Test
    public void testThatJourneyPlannerPageWorks() {
        Response response = GET("/JourneyPlanner");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
	
	@Test
	public void testAdminSecurity() {
		Response response = GET("/admin");
	    assertStatus(302, response);
	    assertHeaderEquals("Location", "/secure/login", response);
	}
	
	@Test
	public void testLoaderSecurity() {
		Response response = GET("/loader");
	    assertStatus(302, response);
	    assertHeaderEquals("Location", "/secure/login", response);
	}
}