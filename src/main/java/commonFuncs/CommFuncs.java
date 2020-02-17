package commonFuncs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.FileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropFileUtil;

public class CommFuncs {
	
	public static WebDriver d;
	public static WebDriver startBrowser(WebDriver d) throws Exception{
		
		if(PropFileUtil.getValueForKey("browser").equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "D:\\nagjt\\myprograms\\StocAccHybrid_Maven\\drivers\\chromedriver.exe");
			d=new ChromeDriver();
		}
		else if(PropFileUtil.getValueForKey("browser").equalsIgnoreCase("firefox")){
			
		}
else if(PropFileUtil.getValueForKey("browser").equalsIgnoreCase("IE")){
			
		}
		
		return d;
		
	}
	
	public static void openApplication(WebDriver d) throws Exception{
		System.out.println("driver");
		d.get(PropFileUtil.getValueForKey("url"));
		d.manage().window().maximize();
	}
	
	public static void waitForElement(WebDriver d,String locatortype,String locatorvalue,String waittime){
		WebDriverWait mywait=new WebDriverWait(d, Integer.parseInt(waittime));
		if(locatortype.equalsIgnoreCase("id")){
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("name")){
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));

		}
		else if(locatortype.equalsIgnoreCase("xpath")){
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));

		}
		
		else{
			System.out.println("unable to  locate for waitforElement method ");
		}
		
		
	}
	
	
	public static void typeAction(WebDriver d,String locatortype,String locatorvalue,String testdata ){
		
		if(locatortype.equalsIgnoreCase("id")){

			d.findElement(By.id(locatorvalue)).clear();
			d.findElement(By.id(locatorvalue)).sendKeys(testdata);
		}
		else if(locatortype.equalsIgnoreCase("name")){

			d.findElement(By.name(locatorvalue)).clear();
			d.findElement(By.name(locatorvalue)).sendKeys(testdata);
		}
		else if(locatortype.equalsIgnoreCase("xpath")){
			d.findElement(By.xpath(locatorvalue)).clear();
			d.findElement(By.xpath(locatorvalue)).sendKeys(testdata);

		}
		else
		{
			System.out.println("unable to execute typeaction method");
		}
		
	}
	
	
	//method for click
		public static void clickAction(WebDriver d,String locatortype,String locatorvalue){

			if(locatortype.equalsIgnoreCase("id")){

				d.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);

			}
			else if(locatortype.equalsIgnoreCase("name")){

				d.findElement(By.name(locatorvalue)).click();

			}
			else if(locatortype.equalsIgnoreCase("xpath")){
				d.findElement(By.xpath(locatorvalue)).click();

			}
			else
			{
				System.out.println("unable to execute clickAction method");
			}

		}
	
		public static void closeBrowser(WebDriver d){
			d.close();

		}
		
		
		public static String generateDate(){
			Date d=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd HH_mm_ss");
			return sdf.format(d);
	}
		
		public static void captureData(WebDriver d,String locatortype,String locatorvalue) throws IOException{
		
			String snumber="";
			if(locatortype.equalsIgnoreCase("id")){
				snumber=d.findElement(By.id(locatorvalue)).getAttribute("value");
			}
				else if(locatortype.equalsIgnoreCase("name")){
					snumber=d.findElement(By.name(locatorvalue)).getAttribute("value");
				}
			
				else if(locatortype.equalsIgnoreCase("xpath")){
					snumber=d.findElement(By.xpath(locatorvalue)).getAttribute("value");	
			}
			
				else if(locatortype.equalsIgnoreCase("linkText")){
					snumber=d.findElement(By.linkText(locatorvalue)).getAttribute("value");
				}
			
			FileWriter fw=new FileWriter("D:\\nagjt\\myprograms\\StocAccHybrid_Maven\\captureData\\supplier.txt");
			BufferedWriter br=new BufferedWriter(fw);
			br.write(snumber);
			br.flush();
			br.close();
			
		}
		
		
		public static void tableValidation(WebDriver d,String column) throws IOException, InterruptedException{
			
			
			FileReader fr=new FileReader("D:\\nagjt\\myprograms\\StocAccHybrid_Maven\\captureData\\supplier.txt");
			BufferedReader bf=new BufferedReader(fr);
			String expdata=bf.readLine();
			int coldata=Integer.parseInt(column);
			if(!d.findElement(By.xpath(PropFileUtil.getValueForKey("search-text"))).isDisplayed()){
				
				d.findElement(By.xpath(PropFileUtil.getValueForKey("search-panel"))).click();
				
				d.findElement(By.xpath(PropFileUtil.getValueForKey("search-text"))).clear();
				
				Thread.sleep(2000);
				
				d.findElement(By.xpath(PropFileUtil.getValueForKey("search-text"))).sendKeys(expdata);
				
				d.findElement(By.xpath(PropFileUtil.getValueForKey("search-button"))).click();
				
				
				WebElement table=d.findElement(By.xpath(PropFileUtil.getValueForKey("supplier-table")));
                List<WebElement> tr=table.findElements(By.tagName("tr"));
                for(int i=1;i<tr.size();i++){
                    
        			
        			String act_data=d.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+coldata+"]/div/span/span")).getText();
                    
        			Thread.sleep(3000);
        			
        			System.out.println(expdata +" "+act_data);
        			
        			Thread.sleep(4000);
        			
        			Assert.assertEquals(expdata,act_data,"snumber not matched" );
        			break;


			}
			
			
			
		}
		
		
		}

}
