package testcases.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import testbase.BaseTest;

public class Session extends BaseTest{
	
	@Test
	public void doLogin(ITestContext context) {
		//test.log(Status.INFO, "Logging In");
		app.log("Logging In");
		app.openBrowser("Chrome");
		app.navigate("url");
		int i = 100/0;
		app.type("username_css", "ashishthakur1983");
		// failure
		//app.reportFailure("First Failure - Non Critical",false);
		app.type("password_xpath", "pass@1234");
		app.validateElementPresent("login_submit_id");
		app.click("login_submit_id");	   
	}
	
	@Test
	public void doLogout() {
		test.log(Status.INFO, "Logging out");
	}

}
