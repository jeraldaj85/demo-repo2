package listener;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class CustomTestNGListener implements ITestListener {
	
	public void onTestFailure(ITestResult result) {
		System.out.println("****************Listener************Test Failed "+result.getName());
		System.out.println(result.getThrowable().getMessage());
		ExtentTest test =(ExtentTest) result.getTestContext().getAttribute("Test");
		test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ",  ExtentColor.RED));
		
	}
	public void onTestSuccess(ITestResult result) {
		System.out.println("****************Listener************Test Passed "+result.getName());
		System.out.println(result.getThrowable().getMessage());
		ExtentTest test =(ExtentTest) result.getTestContext().getAttribute("Test");
		test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ",  ExtentColor.GREEN));
		
	}
	public void onTestSkipped(ITestResult result) {
		System.out.println("****************Listener************Test Skipped "+result.getName());
		System.out.println(result.getThrowable().getMessage());
		ExtentTest test =(ExtentTest) result.getTestContext().getAttribute("Test");
		test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ",  ExtentColor.YELLOW));
		
	}

}
