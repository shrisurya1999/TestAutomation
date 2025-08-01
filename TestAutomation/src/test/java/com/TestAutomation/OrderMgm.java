package com.TestAutomation;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrderMgm {

    WebDriver driver = new ChromeDriver();
 	  @BeforeMethod 	
     public void setup() throws InterruptedException {
         driver.get("https://stageretail.ayekart.com/");
         driver.manage().window().maximize();

         Thread.sleep(1000);
         driver.findElement(By.id("mobile_number")).sendKeys("7894561233");
         Thread.sleep(1000);
         driver.findElement(By.name("password")).sendKeys("Demo@123");
         Thread.sleep(1000);
         driver.findElement(By.xpath("//button[@type='submit']")).click();

         Thread.sleep(1000);
	}
	
 	  //sell order creation with multiple product
 	  @Test
 	  public void SellOrder() throws InterruptedException {
		//WebDriver driver = new ChromeDriver();
		driver.findElement(By.xpath("//p[text()='Sell Orders']")).click();
		//System.out.println("clicked on sell order");
		driver.findElement(By.xpath("//button[normalize-space()='Create New Order']")).click();
		WebElement merchantInput= driver.findElement(By.id("merchant_id"));
		merchantInput.click();
		merchantInput.sendKeys("wooden furniture");
		Thread.sleep(4000);
		merchantInput.sendKeys(Keys.ARROW_DOWN);
		merchantInput.sendKeys(Keys.ENTER);
		WebElement branch= driver.findElement(By.id("outlet_shop_id"));
		branch.click();
		branch.sendKeys("jaipur");
		Thread.sleep(1000);
		branch.sendKeys(Keys.ARROW_DOWN);
		branch.sendKeys(Keys.ENTER);
		WebElement branch1= driver.findElement(By.xpath("//input[@placeholder='Select branch']"));
		branch1.click();
		branch1.sendKeys("ASD");
		Thread.sleep(1000);
		branch1.sendKeys(Keys.ARROW_DOWN);
		branch1.sendKeys(Keys.ENTER);
		
		//scroll the page till target
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0, 700);"); // scrolls down 300 pixels

		
		WebElement product= driver.findElement(By.id("product-autocomplete"));
	
		product.sendKeys("MITV");
		//Thread.sleep(1000);
		product.sendKeys(Keys.ARROW_DOWN);
		product.sendKeys(Keys.ENTER);
		
		// select batch
		
		
		WebElement batch = driver.findElement(By.id("react-select-2-input"));

		// Scroll the input into view
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", batch);
		Thread.sleep(500); // allow scroll to settle

		// Try clicking via JS to avoid interception
		js1.executeScript("arguments[0].click();", batch);

	
		batch.sendKeys("0407202553515020");

		// Wait for dropdown options to load 
		Thread.sleep(1000);

		// Select the first matching option
		batch.sendKeys(Keys.ARROW_DOWN);
		batch.sendKeys(Keys.ENTER);
		

		//selecting payment method
		
		WebElement pmethod = driver.findElement(By.id("supplyChainFinance"));
		//pmethod.click();
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].scrollIntoView(true);", pmethod);
		Thread.sleep(500); // allow scroll to settle

		// Try clicking via JS to avoid interception
		js2.executeScript("arguments[0].click();", pmethod);

		
		//select rm
		WebElement rm = driver.findElement(By.id("rm_id"));
		//rm.click();
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("arguments[0].scrollIntoView(true);", rm);
		Thread.sleep(500); // allow scroll to settle

		// Try clicking via JS to avoid interception
		js2.executeScript("arguments[0].click();", rm);
		rm.sendKeys("naushad");
		rm.sendKeys(Keys.ARROW_DOWN);
		rm.sendKeys(Keys.ENTER);

		
		WebElement creditP = driver.findElement(By.id("credit_period"));
		creditP.sendKeys("5");
		
		WebElement submitBtn = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
		//submit.click();
		JavascriptExecutor js4 = (JavascriptExecutor) driver;
		js4.executeScript("arguments[0].scrollIntoView(true);", submitBtn);
		Thread.sleep(1000); 
		submitBtn.click();
		
		//Popup button
		WebElement yes1 = driver.findElement(By.xpath("//button[text()='Yes']"));
		//submit.click();
		JavascriptExecutor js5 = (JavascriptExecutor) driver;
		js5.executeScript("arguments[0].scrollIntoView(true);", submitBtn);
		Thread.sleep(1000); 
		yes1.click();
		
		System.out.println("order places success");
		//close browser
		
		//driver.quit();
}
}
