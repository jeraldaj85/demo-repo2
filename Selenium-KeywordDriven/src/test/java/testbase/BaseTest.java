package testbase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;
import runner.DataUtil;
// acceptable failure, critical failure, unexpected


// how to configure and run on grid - 4 alpha 6  3.141.59
// how to manage data from xls or json
// how to run this with JSON config
// Running from GIT and Jenkins

public class BaseTest {
	
	public ApplicationKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest(ITestContext context) throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		System.out.println("----------Before Test---------");
		String datafilpath = context.getCurrentXmlTest().getParameter("datafilpath");
		String dataFlag = context.getCurrentXmlTest().getParameter("dataflag");
		String iteration = context.getCurrentXmlTest().getParameter("iteration");
		//String suiteName = context.getCurrentXmlTest().getParameter("suitename");
		// suitename(sheetname)
		System.out.println("datafilepath: "+datafilpath);
		System.out.println("dataflag: "+dataFlag);
		System.out.println("iteration: "+iteration);
		
	//	 reading data from JSON
		JSONObject data = new DataUtil().getTestData(datafilpath, dataFlag, Integer.parseInt(iteration));	
		context.setAttribute("data", data);
		String runmode = (String)data.get("runmode");
		
		// init the reporting for the test
		String testType = context.getCurrentXmlTest().getParameter("testingtype");
		rep = ExtentManager.getReports(testType);
		test =rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Starting Test ");
		//test.log(Status.INFO, "Data "+data.toString());

		context.setAttribute("report", rep);
		context.setAttribute("test", test);
        if(!runmode.equals("Y")) {
        	test.log(Status.SKIP, "Skpping as Data Runmode is N");
        	throw new SkipException("Skpping as Data Runmode is N");
		}		
//		// init and share it with all tests
		app = new ApplicationKeywords(); // 1 app keyword object for entire test -All @Test
        app.setReport(test);		
		app.openBrowser("Chrome");		
		context.setAttribute("app", app);
		System.out.println("End of Before Test Method");
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(ITestContext context, Method m) {
		
		System.out.println("****Before Method****");
	    test = (ExtentTest)context.getAttribute("test");
		String criticalFailure = (String)context.getAttribute("criticalFailure");
		if(criticalFailure != null && criticalFailure.equals("Y")) {
			test.log(Status.SKIP, "Critical Failure in Prevoius Tests - Skipping Tests: "+m.getName());
			throw new SkipException("Critical Failure in Prevoius Tests");// skip in testNG
		}
	    app = (ApplicationKeywords)context.getAttribute("app");
	    rep = (ExtentReports)context.getAttribute("report");
	    System.out.println("****End-Of-Before Method****");
	}
	
	@AfterTest(alwaysRun = true)
	public void quit(ITestContext context) {
		app = (ApplicationKeywords)context.getAttribute("app");
		if(app!=null)
			app.quit();		
		rep = (ExtentReports)context.getAttribute("report");
		if(rep !=null)
			rep.flush();
	}
	
}
