//package com.TestAutomation;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.*;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//public class Login {
//
//    WebDriver driver = new ChromeDriver();
//
//    @BeforeMethod
//    public void setup() throws InterruptedException {
//        driver.get("https://stageretail.ayekart.com/");
//        driver.manage().window().maximize();
//
//        Thread.sleep(1000);
//        driver.findElement(By.id("mobile_number")).sendKeys("7894561233");
//        Thread.sleep(1000);
//        driver.findElement(By.name("password")).sendKeys("Demo@123");
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//button[@type='submit']")).click();
//        Thread.sleep(1000);
//    }
//
//    @Test
//    public void SellOrder() throws InterruptedException {
//        driver.findElement(By.xpath("//p[text()='Sell Orders']")).click();
//        driver.findElement(By.xpath("//button[normalize-space()='Create New Order']")).click();
//
//        WebElement merchantInput = driver.findElement(By.id("merchant_id"));
//        merchantInput.click();
//        merchantInput.sendKeys("wooden furniture");
//        Thread.sleep(4000);
//        merchantInput.sendKeys(Keys.ARROW_DOWN);
//        merchantInput.sendKeys(Keys.ENTER);
//
//        WebElement branch = driver.findElement(By.id("outlet_shop_id"));
//        branch.click();
//        branch.sendKeys("jaipur");
//        Thread.sleep(1000);
//        branch.sendKeys(Keys.ARROW_DOWN);
//        branch.sendKeys(Keys.ENTER);
//
//        WebElement branch1 = driver.findElement(By.xpath("//input[@placeholder='Select branch']"));
//        branch1.click();
//        branch1.sendKeys("ASD");
//        Thread.sleep(1000);
//        branch1.sendKeys(Keys.ARROW_DOWN);
//        branch1.sendKeys(Keys.ENTER);
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        Thread.sleep(1000);
//        js.executeScript("window.scrollBy(0, 700);");
//
//        Set<String> selectedProducts = new HashSet<>();
//        int maxToSelect = 5;
//        int attempts = 0;
//
//        while (selectedProducts.size() < maxToSelect && attempts < 10) {
//            WebElement productInput = driver.findElement(By.id("product-autocomplete"));
//            productInput.clear();
//            productInput.sendKeys(" ");
//            Thread.sleep(1000);
//
//            List<WebElement> productOptions = driver.findElements(By.xpath("//li[contains(@class,'MuiAutocomplete-option')]"));
//            List<WebElement> availableOptions = productOptions.stream()
//                    .filter(p -> !selectedProducts.contains(p.getText()))
//                    .collect(Collectors.toList());
//
//            if (availableOptions.isEmpty()) {
//                System.out.println("No new product options to select.");
//                break;
//            }
//
//            Collections.shuffle(availableOptions);
//            WebElement randomProduct = availableOptions.get(0);
//            String selectedName = randomProduct.getText();
//            randomProduct.click();
//
//            selectedProducts.add(selectedName);
//            System.out.println("Selected product: " + selectedName);
//
//            Thread.sleep(500);
//            attempts++;
//        }
//
//        System.out.println("Final selected products: " + selectedProducts);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//        List<WebElement> productRows = driver.findElements(By.xpath("//table//tbody/tr"));
//
//        for (WebElement row : productRows) {
//            try {
//                WebElement expiryInput = row.findElement(By.xpath(".//td[4]//input"));
//                String expiryDateStr = expiryInput.getAttribute("value").trim();
//
//                LocalDate expiryDate = null;
//                try {
//                    expiryDate = LocalDate.parse(expiryDateStr, formatter);
//                } catch (DateTimeParseException e) {
//                    System.out.println("Invalid expiry date format: " + expiryDateStr);
//                    continue;
//                }
//
//                LocalDate today = LocalDate.now();
//                if (expiryDate.isBefore(today)) {
//                    LocalDate newExpiry = today.plusYears(2);
//                    String newExpiryStr = newExpiry.format(formatter);
//
//                    js.executeScript("arguments[0].scrollIntoView(true);", expiryInput);
//                    expiryInput.click();
//                    expiryInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//                    expiryInput.sendKeys(Keys.BACK_SPACE);
//                    expiryInput.sendKeys(newExpiryStr);
//                    System.out.println("Updated expired date from " + expiryDateStr + " to " + newExpiryStr);
//                    expiryDateStr = newExpiryStr;
//                }
//
//                WebElement batchInput = row.findElement(By.xpath(".//input[contains(@id,'react-select')]"));
//
//                js.executeScript("arguments[0].scrollIntoView(true);", batchInput);
//                Thread.sleep(500);
//                js.executeScript("arguments[0].click();", batchInput);
//                Thread.sleep(500);
//
//                batchInput.sendKeys("0");
//                Thread.sleep(1000);
//
//                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//                WebElement dropdownContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                        By.xpath("//div[contains(@class,'select__menu')]")));
//
//                List<WebElement> batchOptions = dropdownContainer.findElements(
//                        By.xpath(".//div[contains(@class,'select__option')]"));
//
//                if (!batchOptions.isEmpty()) {
//                    WebElement selectedBatch = null;
//                    for (WebElement option : batchOptions) {
//                        String text = option.getText();
//                        if (text.matches(".*(Stock:\\s*([1-9][0-9]+)).*")) {
//                            selectedBatch = option;
//                            break;
//                        }
//                    }
//
//                    if (selectedBatch == null) {
//                        Collections.shuffle(batchOptions);
//                        selectedBatch = batchOptions.get(0);
//                    }
//
//                    js.executeScript("arguments[0].scrollIntoView(true);", selectedBatch);
//                    Thread.sleep(200);
//                    js.executeScript("arguments[0].click();", selectedBatch);
//                    String batchText = selectedBatch.getText();
//                    String productName = row.findElement(By.xpath(".//td[2]")).getText();
//                    System.out.println("Product: " + productName + ", Selected Batch: " + batchText + ", Expiry: " + expiryDateStr);
//                } else {
//                    System.out.println("No batch options available.");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error processing product row: " + e.getMessage());
//            }
//        }
//
//        WebElement pmethod = driver.findElement(By.id("supplyChainFinance"));
//        js.executeScript("arguments[0].scrollIntoView(true);", pmethod);
//        Thread.sleep(500);
//        js.executeScript("arguments[0].click();", pmethod);
//
//        WebElement rm = driver.findElement(By.id("rm_id"));
//        js.executeScript("arguments[0].scrollIntoView(true);", rm);
//        Thread.sleep(500);
//        js.executeScript("arguments[0].click();", rm);
//        rm.sendKeys("naushad");
//        rm.sendKeys(Keys.ARROW_DOWN);
//        rm.sendKeys(Keys.ENTER);
//
//        WebElement creditP = driver.findElement(By.id("credit_period"));
//        creditP.sendKeys("5");
//
//        WebElement submitBtn = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));
//        js.executeScript("arguments[0].scrollIntoView(true);", submitBtn);
//        Thread.sleep(1000);
//        submitBtn.click();
//
//        WebElement yes1 = driver.findElement(By.xpath("//button[text()='Yes']"));
//        js.executeScript("arguments[0].scrollIntoView(true);", yes1);
//        Thread.sleep(1000);
//        yes1.click();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement orderIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//table//tr[1]//td[1]//p)[1]")));
//        String orderId = orderIdElement.getText();
//        System.out.println("Order placed successfully. Order ID: " + orderId);
//
//        Thread.sleep(5000);
//        driver.quit();
//    }
//}
