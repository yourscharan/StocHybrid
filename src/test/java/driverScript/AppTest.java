package driverScript;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFuncs.CommFuncs;
import utilities.ExcelfileUtils;

public class AppTest {
	
	
	
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest test;
	
  @Test
  public void f() throws Exception {
	  
	  ExcelfileUtils ex=new ExcelfileUtils(); 
	for(int i=1;i<=ex.rowCount("MasterTestCases");i++){
			
			if(ex.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y")){
				
				String TCmodule=ex.getData("MasterTestCases", i, 1);
				report= new ExtentReports("D:\\nagjt\\myprograms\\StocAccHybrid_Maven\\Reports\\"+TCmodule+CommFuncs.generateDate()+".html");
				for(int j=1;j<=ex.rowCount(TCmodule);j++){
					
					test=report.startTest(TCmodule);
					//read all columns from TC Module
					String Description=ex.getData(TCmodule, j, 0);
					String Function_Name=ex.getData(TCmodule, j, 1);
					String Locator_Type=ex.getData(TCmodule, j, 2);
					String Locator_Value=ex.getData(TCmodule, j, 3);
					String Test_Data=ex.getData(TCmodule, j, 4);
						
						try{
							if(Function_Name.equalsIgnoreCase("startBrowser"))
							{
								driver=CommFuncs.startBrowser(driver);
								System.out.println("Excuting start browser");
								test.log(LogStatus.INFO, Description);
							}
							else if(Function_Name.equalsIgnoreCase("openApplication"))
							{
								CommFuncs.openApplication(driver);
								test.log(LogStatus.INFO, Description);
							}
							else if(Function_Name.equalsIgnoreCase("waitForElement"))
							{
								CommFuncs.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
								test.log(LogStatus.INFO, Description);
							}
							else if(Function_Name.equalsIgnoreCase("typeAction"))
							{
								CommFuncs.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
								test.log(LogStatus.INFO, Description);
							}
							else if(Function_Name.equalsIgnoreCase("clickAction"))
							{
								CommFuncs.clickAction(driver, Locator_Type, Locator_Value);
								test.log(LogStatus.INFO, Description);
							}
							else if(Function_Name.equalsIgnoreCase("closeBrowser"))
							{
								CommFuncs.closeBrowser(driver);
								test.log(LogStatus.INFO, Description);
							}
							else if(Function_Name.equalsIgnoreCase("captureData")){
								CommFuncs.captureData(driver, Locator_Type, Locator_Value);
								test.log(LogStatus.INFO, Description);
							}
							
							
							else if(Function_Name.equalsIgnoreCase("tableValidation")){
								CommFuncs.tableValidation(driver, Test_Data);
								test.log(LogStatus.INFO, Description);
								
							}
							//write as pass into status column
							ex.setData(TCmodule, j,5, "PASS");
							test.log(LogStatus.PASS, Description);
							ex.setData("MasterTestCases", i, 3, "PASS");
						
						
					}catch(Exception e){
						
						System.out.println("exception handled");
						ex.setData(TCmodule, j,5, "Fail");
						test.log(LogStatus.FAIL, Description);
						ex.setData("MasterTestCases", i, 3, "Fail");
						//take screen shot and store
						File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./Screens//"+TCmodule + CommFuncs.generateDate()+"Myscreen.png"));
						break;
						
					}
						report.endTest(test);
						report.flush();

						
					}
					
				}
			else
			{
				//write as not executed	into status column
				ex.setData("MasterTestCases", i, 3,"Not Executed");
			}
				
			}
			
			
			
		}

  }

