package com.TestAutomation;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		js.executeScript("window.scrollBy(0, 300);"); // scrolls down 300 pixels

//		single product selection
//		WebElement product= driver.findElement(By.id("product-autocomplete"));
//	
//		product.sendKeys("MITV");
//		//Thread.sleep(1000);
//		product.sendKeys(Keys.ARROW_DOWN);
//		product.sendKeys(Keys.ENTER);
//		
	
		// multiple product selection
		Set<String> selectedProducts = new HashSet<>();
		int maxToSelect = 5;
		int attempts = 0;

		while (selectedProducts.size() < maxToSelect && attempts < 10) {
		    // 1. Trigger the product suggestion dropdown
		    WebElement productInput = driver.findElement(By.id("product-autocomplete"));
		    productInput.clear();
		    productInput.sendKeys(" "); // or "A" to get suggestions
		    Thread.sleep(1000);

		    // 2. Get current suggestions
		    List<WebElement> productOptions = driver.findElements(By.xpath("//li[contains(@class,'MuiAutocomplete-option')]"));

		    // Filter out already selected products
		    List<WebElement> availableOptions = productOptions.stream()
		        .filter(p -> !selectedProducts.contains(p.getText()))
		        .collect(Collectors.toList());

		    if (availableOptions.isEmpty()) {
		        System.out.println("No new product options to select.");
		        break;
		    }

		    // 3. Select a random product from remaining options
		    Collections.shuffle(availableOptions);
		    WebElement randomProduct = availableOptions.get(0);
		    String selectedName = randomProduct.getText();
		    randomProduct.click();

		    selectedProducts.add(selectedName);
		    System.out.println("Selected product: " + selectedName);

		    Thread.sleep(500); // Small delay to handle UI transitions
		    attempts++;
		}

		System.out.println("Final selected products: " + selectedProducts);


 	  
		
		
		
		// select batch
		
//		WebElement batch = driver.findElement(By.id("react-select-2-input"));
//
//		// Scroll the input into view
//		JavascriptExecutor js1 = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].scrollIntoView(true);", batch);
//		Thread.sleep(500); // allow scroll to settle
//		// clicking via JS to avoid interception
//		js1.executeScript("arguments[0].click();", batch);
//		batch.sendKeys("0407202553515020");
//		Thread.sleep(1000);
//		batch.sendKeys(Keys.ARROW_DOWN);
//		batch.sendKeys(Keys.ENTER);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		List<WebElement> productRows = driver.findElements(By.xpath("//table//tbody/tr"));

		for (WebElement row : productRows) {
		    try {
		        // ---------------- BATCH SELECTION ----------------
		        WebElement batchInput = row.findElement(By.xpath(".//input[contains(@id,'react-select')]"));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", batchInput);

		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        wait.until(ExpectedConditions.elementToBeClickable(batchInput)).click();
		        Thread.sleep(500); // Allow dropdown to appear

		        WebElement dropdownContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.xpath("//div[contains(@class,'select__menu')]")));

		        List<WebElement> batchOptions = dropdownContainer.findElements(
		            By.xpath(".//div[contains(@class,'select__option')]"));

		        WebElement selectedBatch = null;
		        for (WebElement option : batchOptions) {
		            String text = option.getText();
		            Matcher matcher = Pattern.compile("Stock:\\s*(\\d+)").matcher(text);
		            if (matcher.find()) {
		                int stock = Integer.parseInt(matcher.group(1));
		                if (stock > 10) {
		                    selectedBatch = option;
		                    break;
		                }
		            }
		        }

		        if (selectedBatch == null && !batchOptions.isEmpty()) {
		            Collections.shuffle(batchOptions);
		            selectedBatch = batchOptions.get(0);
		        }

		        String batchText = "";
		        if (selectedBatch != null) {
		            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectedBatch);
		            new Actions(driver).moveToElement(selectedBatch).click().perform();
		            Thread.sleep(500); // Let the input update
		            batchText = selectedBatch.getText();
		        } else {
		            System.out.println("No batch options available for this product.");
		            continue; // skip this row
		        }

		        // ---------------- EXPIRY DATE CHECK ----------------
		        WebElement expiryInput = row.findElement(By.xpath(".//td[4]//input"));
		        String expiryDateStr = expiryInput.getAttribute("value").trim();

		        LocalDate expiryDate;
		        try {
		            expiryDate = LocalDate.parse(expiryDateStr, formatter);
		        } catch (DateTimeParseException e) {
		            System.out.println("Invalid expiry date format: " + expiryDateStr);
		            continue;
		        }

		        LocalDate today = LocalDate.now();
		        if (expiryDate.isBefore(today)) {
		            LocalDate newExpiry = today.plusYears(1);
		            String newExpiryStr = newExpiry.format(formatter);

		            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", expiryInput);
		            expiryInput.click();
		            expiryInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		            expiryInput.sendKeys(newExpiryStr);
		            expiryDateStr = newExpiryStr;

		            System.out.println("Updated expiry date from " + expiryDate + " to " + newExpiryStr);
		        }

		        String productName = row.findElement(By.xpath(".//td[2]")).getText();
		        System.out.println("Product: " + productName + ", Selected Batch: " + batchText + ", Expiry: " + expiryDateStr);

		    } catch (Exception e) {
		        System.out.println("Error processing row: " + e.getMessage());
		        e.printStackTrace();
		    }
		}


		    
		    
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
		
		//System.out.println("order places success");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement orderIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//table//tr[1]//td[1]//p)[1]")));
		String orderId = orderIdElement.getText();
		System.out.println("Order placed successfully. Order ID: " + orderId);

		//close browser
		Thread.sleep(5000);
		driver.quit();
}
	
 	  }
