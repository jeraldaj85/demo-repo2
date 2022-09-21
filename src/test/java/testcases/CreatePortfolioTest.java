package testcases;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import testbase.BaseTest;

public class CreatePortfolioTest extends BaseTest{
	
	JSONObject data = null;
	
@Test
	public void createPFTest(ITestContext context) throws InterruptedException  {
	data = (JSONObject) context.getAttribute("data");
	String username = (String) data.get("username");
	String password = (String) data.get("password");
	
	app.navigate("url_rediff");
        app.takeScreenShot();
	app.type("username_css", username);
	app.type("password_xpath", password);
	app.validateElementPresent("loginsubmit_id");
//	app.click("login_submit_id");
//	app.validateLogin();
	app.pause(2);
	app.takeScreenShot();

		
	}
}
