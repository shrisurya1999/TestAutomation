package com.TestAutomation;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.util.List;


public class Login {
	
	WebDriver driver = new ChromeDriver();
	
 
  public void openapp() throws InterruptedException {
	  
	  WebDriver driver = new ChromeDriver();
      driver.get("https://stageretail.ayekart.com/");
      driver.manage().window().maximize();
      Thread.sleep(2000);
     driver.findElement(By.id("mobile_number")).sendKeys("7894561233");
      Thread.sleep(2000);
     driver.findElement(By.name("password")).sendKeys("Demo@123");
      Thread.sleep(2000);
     driver.findElement(By.xpath("//button[@type='submit']")).click();    
     
     List<WebElement> buttons =driver.findElements(By.xpath("/html/body/div[2]/div[3]/div[2]/div/div[1]/div[2]/div/div/div/div/ul/nav[1]/div[2]"));
	  System.out.println(buttons);
	  for (WebElement btn : buttons) {
          System.out.println(btn.getText().trim()); // .trim() removes extra spaces
      }
      System.out.println("---- End of List ----");
      
      
      System.out.println("test ");
  }
  }
  
  
  

