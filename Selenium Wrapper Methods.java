package businesscomponents;

import supportlibraries.*;
import uimap.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.jetty.html.Image;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import com.thoughtworks.selenium.Wait;

/**
 * Class for storing general purpose business components
 * 
 * @author Vignesh AS
 */

public class GeneralComponents extends ReusableLibrary {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object passed from the
	 *            {@link DriverScript}
	 */
	String title = driver.getTitle();
	boolean present;
	String parentWindow;
	public Connection con;
	public Statement stmt;
	String finalStatus;
	public Map<String, Double> dataEntryavailableMap;
	public Map<String, Double> dataEntryMissingMap;

	public GeneralComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void switchFrameByXpath(By by) {
		try {
			// String title = driver.getTitle();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			WebElement webElementFrame = driver.findElement(by);
			driver.switchTo().frame(webElementFrame);
			System.out.println("Now you are inside the Frame xpath");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Frame found");
			throw new FrameworkException(e.getMessage());
		}
	}

	public String getAttributeValue(By by, String attributeName) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		moveToElementToBeActive(by);
		String text = driver.findElement(by).getAttribute(attributeName);
		System.out.println("The text from Application is " + text);
		return text;
	}

	public void getAttributeValueAndValidate(By by, String attributeName, String validationText)
			throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			moveToElementToBeActive(by);
			String text = driver.findElement(by).getAttribute(attributeName);
			System.out.println("The text from Application is " + text);
			if (text.equals(validationText) || validationText.contains(text)) {
				System.out.println("The Text is " + validationText + " and is expected");
				highlightWebElement(by, text);
				report.updateTestLog("Validate Text", "The text is validated and the status is " + text, Status.DONE);
			} else {
				System.out.println("The Text is " + validationText + " and is not as expected and is not found");
				highlightWebElement(by, text);
				report.updateTestLog("Validate Text", "The text is incorrect", Status.FAIL);
			}

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			report.updateTestLog("Validate Text", "The text is incorrect", Status.FAIL);
		}
	}

	/*
	 * public void clickOnRecordName() { try { //Thread.sleep(5000);
	 * driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); String
	 * leaseID = dataTable.getData("General_Data", "LeaseID");
	 * System.out.println("The lease ID inside clickRecordName Method is "+leaseID);
	 * //getWindowTitle = driver.getWindowHandle();
	 * //System.out.println("Parent Process id: "+getWindowTitle);
	 * clickByXpath(ApproveLeaseAbstract_ReviewerPage_US.lnkRecordName);
	 * //driver.getWebDriver().findElement(By.xpath("//span[contains(text(),'"+
	 * leaseID+"')]")).click();
	 * report.updateTestLog("Click on the Record Name to Approve",
	 * "Record name should be clicked to Approve", Status.PASS); } catch (Exception
	 * e) { e.printStackTrace();
	 * report.updateTestLog("Click on the Record Name to Approve",
	 * "Record name should be clicked to Approve", Status.FAIL); } }
	 */

	public int getTotNoOfRecords(By by) {
		String totNoOfRecordsB4Deleting = driver.findElement(by).getText().trim();
		String[] arr_TotNoOfRecordsB4Deleting = totNoOfRecordsB4Deleting.split(" ");
		int int_TotNoOfRecords = Integer.parseInt(arr_TotNoOfRecordsB4Deleting[0]);
		System.out.println("Total No of Records Before Deleting:" + int_TotNoOfRecords);
		return int_TotNoOfRecords;
	}

	public void switchFrameByID(By by) {
		try {
			System.out.println("Frame by ID");
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			WebElement webElementFrame = driver.findElement(by);
			driver.switchTo().frame(webElementFrame);
			System.out.println("Now you are inside the Frame id");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Frame found");
		}
	}

	public void switchFrameByClass(String className) {
		try {
			WebElement webElementFrame = driver.findElement(SignOnPage.fraLogin2);
			driver.switchTo().frame(webElementFrame);
			present = true;
			System.out.println("Now you are inside the Frame");
		} catch (NoSuchElementException e) {
			present = false;
		}
	}

	public void clickByXpath(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.elementToBeClickable(by));
			moveToElementToBeActive(by);
			driver.findElement(by).click();
			// driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	public void clickByLinkText(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.elementToBeClickable(by));
			moveToElementToBeActive(by);
			driver.findElement(by).click();
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	public void clickByTagname(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			moveToElementToBeActive(by);
			driver.findElement(by).click();
			present = true;
		} catch (NoSuchElementException e) {
			present = false;
		}
	}

	public void ddSelectByVisibleText(By by, String value) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			WebElement nat = driver.findElement(by);
			Select na = new Select(nat);
			na.selectByVisibleText(value);
			present = true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			present = false;
		}
	}

	public void searchLeaseIDNOpen(String contractID) throws InterruptedException {
		System.out.println("The Contract ID is " + contractID);
		searchLeaseContract(contractID);
		clickByXpath(LeasesPage.lnkSearchResults);
		switchWindow();
	}

	public void switchWindow() {
		try {
			Thread.sleep(3000);
			String parentWindow = driver.getWindowHandle();
			Set<String> multiplewin = driver.getWindowHandles();
			for (String iterator : multiplewin) {
				driver.switchTo().window(iterator);
				driver.manage().window().maximize();
			}
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			System.out
					.println("Now control successfully switched to the window whose URL is " + driver.getCurrentUrl());
			System.out.println("The title of the switched window is " + driver.getTitle());
			// report.updateTestLog("Switch to Window", "Driver should switch to the new
			// Window", Status.PASS);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("You have not switched to the new opened window");
			report.updateTestLog("Switch to Window", "Driver should switch to the new Window", Status.SCREENSHOT);
		}
	}

	public void switchToParentWindow() throws InterruptedException {
		// Thread.sleep(3000);
		System.out.println("Inside switchToParentWindow method");
		// String childWindow = driver.getWindowHandle();
		Set<String> multipleWindows = driver.getWindowHandles();
		for (String it : multipleWindows) {
			driver.switchTo().window(it);
			break;
		}
		// driver.switchTo().window(parentWindow);
		Thread.sleep(3000);
	}

	public void invokeApplication() throws InterruptedException {
		// driver.navigate().refresh();
		try {
			Thread.sleep(2000);
			System.out.println("Inside the invoke application");
			driver.manage().deleteAllCookies();
			// driver.get(properties.getProperty("ApplicationUrl"));
			driver.get(properties.getProperty("ApplicationUrl"));
			driver.navigate().refresh();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			Thread.sleep(3000);
			report.updateTestLog("Invoke Application",
					"Invoke the application under test @ " + properties.getProperty("ApplicationUrl"), Status.PASS);
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Invoke Application",
					"Invoke the application under test @ " + properties.getProperty("ApplicationUrl"), Status.FAIL);
		}
	}

	public void login() throws InterruptedException {
		String userName = dataTable.getData("General_Data", "Username");
		String password = dataTable.getData("General_Data", "Password");

		report.updateTestLog("Enter user credentials", "Specify " + "username = " + userName, Status.DONE);
		WebDriverWait wt = new WebDriverWait(driver, 60);
		wt.until(ExpectedConditions.visibilityOfElementLocated(SignOnPage.txtUsername));
		System.out.println("The username is " + userName);
		enterByXpath(SignOnPage.txtUsername, userName);
		enterByXpath(SignOnPage.txtPassword, password);
		clickByXpath(SignOnPage.btnLogin);
		// driver.findElement(SignOnPage.txtUsername).sendKeys(userName);
		// driver.findElement(SignOnPage.txtPassword).sendKeys(password);
		// driver.findElement(SignOnPage.btnLogin).click();
		Thread.sleep(4000);
		String tit = driver.getTitle();
		System.out.println(tit);
		System.out.println(title);
		if (tit.contentEquals(title)) {
			driver.switchTo().defaultContent();
			driver.switchTo().frame(0);
			driver.findElement(By.xpath("//*[@id='msgButtonAffirm']")).click();
		} else {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			// Post Login First Page title
			System.out.println("Title after login page " + driver.getTitle());
		}
		report.updateTestLog("Login", "Click the sign-in button", Status.DONE);
	}

	public void enterByXpath(By by, String value) throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			 WebDriverWait wt = new WebDriverWait(this.driver, 60);
		
			 System.out.println("Inside the enterby Xpath to enter the value");
			
			 
			 
			
			 if (!by.equals(MCDWebMailHomePage.txtSearch)) {
				driver.findElement(by).clear();
				
			}

			if (by.equals(LeaseAbstract_RentTabPage.txtBaseLeaseRate)
					|| by.equals(LeaseAbstract_SecurityDepositTabPage.txtDepositAmount)
					|| by.equals(LeaseAbstract_ImprovementOrAllowanceTab.txtAllowanceAmount)) {
				 //WebDriverWait wt = new WebDriverWait(this.driver, 60);
				// wt.until(ExpectedConditions.alertIsPresent());
				Thread.sleep(2000);
				isAlertPresent();
				// handleAlert();
			}
			/*
			 * moveToElementToBeActive(by); driver.findElement(by).clear();
			 * driver.findElement(by).sendKeys(value);
			 * System.out.println("The value entered is " +value);
			 */
			//driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			wt.until(ExpectedConditions.presenceOfElementLocated(by));
			
			moveToElementToBeActive(by);
			driver.findElement(by).clear();
			wt.until(ExpectedConditions.presenceOfElementLocated(by));
			//driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			driver.findElement(by).sendKeys(value);
			wt.until(ExpectedConditions.presenceOfElementLocated(by));
			// driver.findElement(by).sendKeys(Keys.TAB);
			tabOut(by);
			//driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			wt.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	public void selectCheckBox(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			if (!driver.findElement(by).isSelected()) {
				driver.findElement(by).click();
				present = true;
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			present = false;
		}
	}

	public int generateRandomNumber() {
		Random randNum = new Random();
		int randomNumber = randNum.nextInt();
		return randomNumber;
	}

	public void enterTextInsideTable(By tableName, By tableRow, By tableData) {
		try {
			WebElement table = driver.findElement(tableName);
			List<WebElement> rows = table.findElements(tableRow);
			System.out.println("Number of Rows:" + rows.size());
			for (int rnum = 0; rnum < rows.size() - 1; rnum++) {
				System.out.println("Entering iteration " + rnum);
				List<WebElement> columns = rows.get(rnum).findElements(tableData);
				// System.out.println("Number of columns:"+columns.size());
				for (int cnum = 0; cnum < columns.size() - 1; cnum++) {
					System.out.println(columns.get(cnum).getText());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getTotalNoOfRowsInTableNClickLastRow(By tableName, By lastRow) throws InterruptedException {
		System.out.println("Inside getTotalNoOfRowsInTableNClickLastRow module");
		WebElement table = driver.findElement(tableName);
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		System.out.println("Number of Rows:" + tableRows.size());
		int sizeOfTable = tableRows.size();
		System.out.println("The total no of rows is " + sizeOfTable);
		// tableRows.get(sizeOfTable-1).click();
		Thread.sleep(2000);
		driver.findElement(lastRow).click();
		// highlightWebElement(lastRow);
	}

	public void scrollIntoView(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			WebElement we = driver.findElement(by);
			js.executeScript("arguments[0].scrollIntoView(true);", we);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollDown(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			WebElement we = driver.findElement(by);
			we.sendKeys(Keys.PAGE_DOWN);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollUp(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			WebElement we = driver.findElement(by);
			we.sendKeys(Keys.PAGE_UP);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tabOut(By by) throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			System.out.println("Inside the tabOut function");
			moveToElementToBeActive(by);
			WebElement wb = driver.findElement(by);
			wb.sendKeys(Keys.TAB);
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doubleClick(By by) {
		try {
			// WebDriverWait wt = new WebDriverWait(driver, 60);
			// wt.until(ExpectedConditions.visibilityOfElementLocated(by));
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			Actions act = new Actions(driver);
			WebElement wb = driver.findElement(by);
			act.doubleClick(wb).build().perform();
			// act.doubleClick().build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void moveToElementToBeActive(By by) {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			Actions act = new Actions(driver.getWebDriver());
			WebElement wb = driver.findElement(by);
			act.moveToElement(wb).build().perform();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
	}

	public void pressEnter(By by) throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			System.out.println("Inside the press enter function");
			WebElement wb = driver.findElement(by);
			wb.sendKeys(Keys.ENTER);
			Thread.sleep(8000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleAlert() throws InterruptedException {
		try {
			// WebDriverWait wait = new WebDriverWait(this.driver, 60);
			// wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			System.out.println(alertText);
			Thread.sleep(8000);
			alert.accept();
			// alert.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public void switchingFrames() {
		try {
			List<WebElement> frames = driver.findElementsByTagName("iframe");
			System.out.println(frames.size());
			for (WebElement fr : frames) {
				driver.switchTo().frame(fr);
				WebElement ele = driver.findElementByXPath("//*[@id='filterValue0']");
				if (ele != null) {
					System.out.println("Element is Present");
				} else
					System.out.println("Element not Present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getText(By by, String fieldName) {
		String getTextOfWebElement = null;
		try {
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			getTextOfWebElement = driver.findElement(by).getText();
			report.updateTestLog("Get the Text of " + fieldName + "",
					"The text of " + fieldName + " is " + getTextOfWebElement, Status.DONE);
		} catch (NoSuchElementException e) {

			report.updateTestLog("Get the Text of " + fieldName +"", "Unable to locate the " + fieldName, Status.FAIL);
		}
		return getTextOfWebElement;
	}

	public void getTextAndValidate(By by, String validationText) throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			moveToElementToBeActive(by);
			String text = driver.findElement(by).getText();
			System.out.println("The text from Application is " + text);
			if (text.trim().equalsIgnoreCase(validationText.trim())) {
				System.out.println("The Text is " + validationText + " and is expected");
				highlightWebElement(by, text);
				report.updateTestLog("Validate Text", "The text is validated and the status is " + text, Status.DONE);
			} else {
				System.out.println("The Text is " + validationText + " and is not as expected and is not found");
				highlightWebElement(by, text);
				report.updateTestLog("Validate Text", "The text is incorrect", Status.FAIL);
			}

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			report.updateTestLog("Validate Text", "The text is incorrect", Status.FAIL);
		}
	}

	public void getTextAndValidateWithValueAttribute(By by, String validationText) throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			moveToElementToBeActive(by);
			String text = driver.findElement(by).getAttribute("value");
			System.out.println("The text from Application is " + text);
			if (text.trim().equals(validationText.trim()) || validationText.contains(text)) {
				System.out.println("The Text is " + validationText + " and is expected");
				highlightWebElement(by, text);
				report.updateTestLog("Validate Text", "The text is validated and the status is " + text, Status.DONE);
			} else {
				System.out.println("The Text is " + validationText + " and is not as expected and is not found");
				highlightWebElement(by, text);
				report.updateTestLog("Validate Text", "The text is incorrect", Status.FAIL);
			}

		} catch (NoSuchElementException e) {
			e.printStackTrace();
			report.updateTestLog("Validate Text", "The text is incorrect", Status.FAIL);
		}
	}

	public void highlightWebElement(By by, String text) throws InterruptedException {
		try {
			moveToElementToBeActive(by);
			WebElement elem = driver.getWebDriver().findElement(by);
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			// System.out.println("Inside the highlight Webelement method");
			// js.executeScript("arguments[0].setAttribute('style','background: yellow;
			// border: 3px solid red;');", elem);
			js.executeScript(
					"arguments[0].setAttribute('style','background: yellow; box-shadow: 0 0 0px 2px #f11919;');", elem);
			Thread.sleep(1500);
			report.updateTestLog("Highlight Text", "The text " + text + " is highlighted", Status.SCREENSHOT);
		} catch (NoSuchElementException e) {
			report.updateTestLog("Highlight Text", "The WebElement for " + text + " is not identifiyable",
					Status.SCREENSHOT);
			e.printStackTrace();
		}
	}

	public void scrollHorizonally(By by) {
		WebElement scrollHorizontal = driver.getWebDriver().findElement(by);
		JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
		js.executeScript("window.scrollBy(1000,0)", scrollHorizontal);
	}

	public WebElement findElement(By by) {
		WebElement elem = driver.findElement(by);
		// draw a border around the found element
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", elem);
		}
		return elem;
	}

	public void switchToDefaultNSwitchInsideFrame() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		switchFrameByXpath(LeaseAbstractPage.frameLAP3);
		Thread.sleep(2000);
	}

	public void getAllLinks() {
		List<WebElement> lnks = driver.findElementsByTagName("a");
		for (int i = 0; i < lnks.size(); i++) {
			System.out.println(lnks.get(i).getText());
		}
	}

	public void verifyLoginSuccessful() {
		driverUtil.waitUntilPageLoaded(5);
		if (driverUtil.objectExists(TririgaHomePage.lnkSignOff)
				&& driver.findElement(TririgaHomePage.welcomeUser).isDisplayed()) {
			report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
		} else {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Verify Login", "Login failed for valid user");
		}
	}

	public void verifyLoginFailed() {
		if (driverUtil.objectExists(TririgaHomePage.lnkSignOff)) {
			report.updateTestLog("Verify Login", "Login failed for invalid user", Status.PASS);
		} else {
			report.updateTestLog("Verify Login", "Login succeeded for invalid user", Status.FAIL);
		}
	}

	public void logout() {
		driver.findElement(TririgaHomePage.lnkSignOff).click();
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		report.updateTestLog("Logout", "Click the sign-off link", Status.PASS);

	}

	public void closeAllBrowser() {
		driver.quit();
	}

	public Statement getStatement() throws ClassNotFoundException, SQLException {
		try {
			String dbDriver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(dbDriver);
			System.out.println("driverloaded");
			String connURL = properties.getProperty("ConnectionURL");
			String uName = properties.getProperty("UserName");
			String pwd = properties.getProperty("Password");
			con = DriverManager.getConnection(connURL, uName, pwd);
			stmt = con.createStatement();
			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stmt;
	}

	public ResultSet getData(String query) throws ClassNotFoundException, SQLException {
		ResultSet data = getStatement().executeQuery(query);
		while (data.next()) {
			// System.out.println(data.getc);
			System.out.println(data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
		}
		return data;
	}

	public void closeAndSwitchWindowBack() {
		try {
			driver.switchTo().defaultContent();
			System.out.println("Close and Switch back");
			// driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.close();
			Set<String> multiplewins = driver.getWindowHandles();
			System.out.println(multiplewins.size());
			for (String it : multiplewins) {
				System.out.println(it);
				driver.switchTo().window(it);
				break;
			}
			System.out.println("You have switched to the window whose title is " + driver.getTitle());
			System.out.println("You have closed the " + driver.getCurrentUrl() + " window");
			report.updateTestLog("Switch Back Window", "Switched to Leases Window", Status.DONE);
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Switch Back Window", "Switched to Leases Window", Status.FAIL);
		}
	}

	public void SwitchWindow(String window, String closeWindowOrNot) {
		if (closeWindowOrNot.equalsIgnoreCase("yes")) {
			driver.close();
			Set<String> multiplewins = driver.getWindowHandles();
			Iterator<String> itr = multiplewins.iterator();
			while (itr.hasNext()) {
				String childWindow = itr.next();
				if (window.equals(childWindow)) {
					driver.switchTo().window(childWindow);
					System.out.println(driver.switchTo().window(childWindow).getTitle());
				}
			}
		} else {
			Set<String> multiplewins = driver.getWindowHandles();
			Iterator<String> itr = multiplewins.iterator();
			while (itr.hasNext()) {
				String childWindow = itr.next();
				if (window.equals(childWindow)) {
					driver.switchTo().window(childWindow);
					System.out.println(driver.switchTo().window(childWindow).getTitle());
				}
			}
		}

	}

	public void logoutTririgaApplication() {
		try {
			driver.switchTo().defaultContent();
			Thread.sleep(2000);
			driver.findElement(SignOnPage.lnkLogout).click();
			Thread.sleep(5000);
			System.out.println("The Title after logging out is " + driver.getTitle());
			// report.updateTestLog("Logout and Close Browser", "The user should be logged
			// Out and the browser should be closed", Status.PASS);
			Thread.sleep(3000);
			// driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Logout and Close Browser",
					"The user should be loged Out and the browser should be closed", Status.FAIL);
		}
	}

	public void logoutTririgaApplicationAndCloseAllBrowser() {
		try {
			driver.switchTo().defaultContent();
			Thread.sleep(2000);
			driver.findElement(SignOnPage.lnkLogout).click();
			Thread.sleep(5000);
			System.out.println("The Title after logging out is " + driver.getTitle());
			driver.quit();
			// report.updateTestLog("Logout and Close Browser", "The user should be logged
			// Out and the browser should be closed", Status.PASS);
			Thread.sleep(3000);
			// driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Logout and Close Browser",
					"The user should be loged Out and the browser should be closed", Status.FAIL);
		}
	}

	public void textPresent(String txt) {
		try {
			if (driver.getPageSource().contains(txt)) {
				System.out.println("Text is present");
			} else {
				System.out.println("Text is absent");
			}
			report.updateTestLog("Validate whether the Text is Present or Not", "The Text is Present", Status.PASS);
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Validate whether the Text is Present or Not", "The Text is not Present", Status.FAIL);
		}
	}

	/*
	 * public void isWebElementDisplayed(By by) { try { if(
	 * driver.findElement(by).isDisplayed()){
	 * System.out.println("Element is Visible"); }else{
	 * System.out.println("Element is InVisible"); }
	 * report.updateTestLog("Validate whether the WebElement is Present or Not",
	 * "The WebElement is Present", Status.PASS); } catch (NoSuchElementException e)
	 * { e.printStackTrace();
	 * report.updateTestLog("Validate whether the WebElement is Present or Not",
	 * "The WebElement is not Present", Status.FAIL); } }
	 */

	public void refreshUntillStatusChanged(String getText, String statusFromDatatable, By by, By imgRefreshProperty)
			throws InterruptedException {
		try {
			if (getText.equalsIgnoreCase(statusFromDatatable)) {
				getTextAndValidate(by, statusFromDatatable);
			} else {
				do {
					// driver.navigate().refresh();
					clickByXpath(imgRefreshProperty);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(2000);
					finalStatus = driver.findElement(by).getText().trim();
					System.out.println(finalStatus);
				} while (!finalStatus.equalsIgnoreCase(statusFromDatatable));
				getTextAndValidate(by, statusFromDatatable);
			}


			report.updateTestLog("Validate whether the refresh button is clicked untill the status is changed",
					"The refresh button should be clicked untill the status is changed", Status.DONE);
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Validate whether the refresh button is clicked untill the status is changed",
					"The refresh button is not clicked untill the status is changed", Status.FAIL);
		}
	}

	public void refreshWindowUntillStatusChanged(String getText, String statusFromDatatable, By status, By contentframe)
			throws InterruptedException {

		try {
			if (getText.equals(statusFromDatatable)) {
				getTextAndValidate(status, statusFromDatatable);
			} else {
				do {
					driver.navigate().refresh();
					// clickByXpath(imgRefreshProperty);
					// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(5000);
					driver.switchTo().defaultContent();
					this.switchFrameByXpath(contentframe);
					finalStatus = driver.findElement(status).getText();
					System.out.println("Text in status box is " + finalStatus);
				} while (!finalStatus.equals(statusFromDatatable));
			}
			getTextAndValidate(status, statusFromDatatable);
			report.updateTestLog("Validate whether the refresh button is clicked untill the status is changed",
					"The refresh button should be clicked untill the status is changed", Status.DONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			report.updateTestLog("Validate whether the window is refreshed untill the status is changed",
					"The window is not refreshed untill the status is changed", Status.FAIL);
		}
	}

	public void refreshUntillTheWebElementIsVisible(By imgRefresh, By by) throws InterruptedException {
		try {
			do {
				clickByXpath(imgRefresh);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(5000);
			} while (!driver.findElement(by).isDisplayed());
			report.updateTestLog("Validate whether the refresh button is clicked untill the WebElement is visisble",
					"The refresh button should be clicked untill the WebElement is Visible", Status.PASS);
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Validate whether the refresh button is clicked untill the WebElement is visisble",
					"The refresh button is not clicked untill the WebElement is Visible", Status.FAIL);
		}
	}

	/*
	 * public void isWebElementReadOnly(By by, String fieldName, String fieldType)
	 * throws InterruptedException { try { moveToElementToBeActive(by); //Text Box
	 * if (fieldType.equalsIgnoreCase("Text Box")) {
	 * driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS); WebElement
	 * we = driver.findElement(by); String readonly = we.getAttribute("class");
	 * //Assert.assertNotNull(read-only); if (readonly.contains("read-only")) {
	 * //highlightWebElement(by, fieldName);
	 * report.updateTestLog("Validate whether the TextBox is Editable or not",
	 * "The Text feild for " + fieldName + " is Read Only", Status.PASS); } else {
	 * highlightWebElement(by, fieldName);
	 * report.updateTestLog("Validate whether the TextBox is Editable or not",
	 * "The WebElement " + fieldName + " is editable", Status.FAIL); } } //Check Box
	 * else if(fieldType.equalsIgnoreCase("check box")){
	 * driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS); WebElement
	 * we = driver.findElement(by); String readonly = we.getAttribute("style"); if
	 * (readonly.contains("opacity")) { //highlightWebElement(by, fieldName);
	 * report.updateTestLog("Validate whether the Check Box is Editable or not",
	 * "The Text feild for " + fieldName + " is Read Only", Status.PASS); } else {
	 * highlightWebElement(by, fieldName);
	 * report.updateTestLog("Validate whether the Check Box is Editable or not",
	 * "The WebElement " + fieldName + " is editable", Status.FAIL); } } else {
	 * //Radio Button driver.manage().timeouts().implicitlyWait(40,
	 * TimeUnit.SECONDS); WebElement we = driver.findElement(by); String readonly =
	 * we.getAttribute("disabled");
	 * System.out.println("The attribute value of disabled is "+readonly); if
	 * (readonly == null||readonly.contains("true")) { //highlightWebElement(by,
	 * fieldName);
	 * report.updateTestLog("Validate whether the Radio Button is Editable or not",
	 * "The Text feild for " + fieldName + " is Read Only", Status.PASS); } else {
	 * highlightWebElement(by, fieldName);
	 * report.updateTestLog("Validate whether the Radio Button is Editable or not",
	 * "The WebElement " + fieldName + " is editable", Status.FAIL); } } } catch
	 * (NoSuchElementException e) { //throw new FrameworkException(e.getMessage());
	 * report.updateTestLog("Validate whether the webelement is Editable or not",
	 * "The WebElement "+fieldName+" not found", Status.FAIL); } }
	 */

	public void isWebElementReadOnly(By by, String fieldName, String fieldType) throws InterruptedException {
		try {
			String flag = IsWebElementDisplayed(by, fieldName);
			// System.out.println("The Flag of WebElement "+fieldName+" is displayed or not
			// "+flag);
			if (flag.equals("PASS")) {
				moveToElementToBeActive(by);
				// Text Box
				if (fieldType.equalsIgnoreCase("Text Box")) {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					WebElement we = driver.findElement(by);
					String readonly = we.getAttribute("class");
					// Assert.assertNotNull(read-only);
					if (readonly.contains("read-only")) {
						// highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the TextBox is Editable or not",
								"The Text feild for " + fieldName + " is displayed and Read Only", Status.DONE);
					} else {
						highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the TextBox is Editable or not",
								"The WebElement " + fieldName + " is displayed and editable", Status.FAIL);
					}
				}
				// Check Box
				else if (fieldType.equalsIgnoreCase("check box")) {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					WebElement we = driver.findElement(by);
					String readonly = we.getAttribute("style");
					if (readonly.contains("opacity")) {
						// highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Check Box is Editable or not",
								"The Text feild for " + fieldName + " is displayed and Read Only", Status.DONE);
					} else {
						highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Check Box is Editable or not",
								"The WebElement " + fieldName + " is displayed and editable", Status.FAIL);
					}
				} else { // Radio Button
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					WebElement we = driver.findElement(by);
					String readonly = we.getAttribute("disabled");
					System.out.println("The attribute value of disabled is " + readonly);
					if (readonly == null || readonly.contains("true")) {
						// highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Radio Button is Editable or not",
								"The Text feild for " + fieldName + " is displayed and Read Only", Status.DONE);
					} else {
						highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Radio Button is Editable or not",
								"The WebElement " + fieldName + " is displayed and editable", Status.FAIL);
					}
				}
			}
			/*
			 * else {
			 * report.updateTestLog("Validate whether the webelement is Editable or not",
			 * "The WebElement "+fieldName+" is  not displayed", Status.FAIL); }
			 */
		} catch (NoSuchElementException e) {
			// throw new FrameworkException(e.getMessage());
			report.updateTestLog("Validate whether the webelement is Editable or not",
					"The WebElement " + fieldName + " is not editable", Status.FAIL);
		}
	}

	public void isWebElementEditOnly(By by, String fieldName, String fieldType) throws InterruptedException {
		try {
			String flag = IsWebElementDisplayed(by, fieldName);
			if (flag.equals("PASS")) {
				moveToElementToBeActive(by);
				// Text Box
				if (fieldType.equalsIgnoreCase("Text Box")) {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					WebElement we = driver.findElement(by);
					String readonly = we.getAttribute("class");
					// Assert.assertNotNull(read-only);
					if (readonly.contains("read-only")) {
						highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the TextBox is Editable or not",
								"The Text feild for " + fieldName + " is displayed and Read Only", Status.FAIL);
					} else {
						// highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the TextBox is Editable or not",
								"The WebElement " + fieldName + " is displayed and editable", Status.DONE);
					}
				}
				// Check Box
				else if (fieldType.equalsIgnoreCase("check box")) {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					WebElement we = driver.findElement(by);
					String readonly = we.getAttribute("style");
					if (readonly.contains("opacity")) {
						highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Check Box is Editable or not",
								"The Text feild for " + fieldName + "is displayed and Read Only", Status.FAIL);
					} else {
						// highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Check Box is Editable or not",
								"The WebElement " + fieldName + " is displayed and editable", Status.DONE);
					}
				} else {
					driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
					WebElement we = driver.findElement(by);
					String readonly = we.getAttribute("disabled");
					System.out.println("The attribute value of disabled is " + readonly);
					if (readonly == null || readonly.contains("true")) {
						highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Radio Button is Editable or not",
								"The Text feild for " + fieldName + " is displayed and Read Only", Status.FAIL);
					} else {
						// highlightWebElement(by, fieldName);
						report.updateTestLog("Validate whether the Radio Button is Editable or not",
								"The WebElement " + fieldName + " is displayed and editable", Status.DONE);
					}
				}
			}
		} catch (NoSuchElementException e) {
			// throw new FrameworkException(e.getMessage());
			report.updateTestLog("Validate whether the webelement is Editable or not",
					"The WebElement " + fieldName + " not found", Status.FAIL);
		}
	}

	public void isButtonClickableOrNot(By by, String fieldname) throws InterruptedException {
		// driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		System.out.println("Checking  " + fieldname + "   is Displayed and clickable or not");
		String flag = IsWebElementDisplayed(by, fieldname);
		try {
			if (flag.equals("PASS")) {
				WebElement ele = driver.findElement((by));
				System.out.println("Text of element prsent in application is " + ele.getText());
				if ((ele.isEnabled() == true) && (ele.getText().trim().equalsIgnoreCase(fieldname.trim()))) {
					WebDriverWait wt = new WebDriverWait(driver, 60);
					wt.until(ExpectedConditions.elementToBeClickable(ele));
					System.out.println(fieldname + " is clickable");
					report.updateTestLog("Validate whether the Element is displayed and clickable",
							"The button: " + fieldname + " is displayed and also Clickable", Status.DONE);
				} else {
					report.updateTestLog("Validate whether the button is displayed and clickable",
							"The button: " + fieldname + " is not clickable", Status.FAIL);
				}
			} else {
				// System.out.println(fieldname + " " + " Element is not displayed");
				report.updateTestLog("Validate whether the button is displayed and clickable",
						"The Button is not displayed and clickable", Status.FAIL);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int getMonthsBetweenDates(String fromDate, String toDate, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate srtDate = LocalDate.parse(fromDate, formatter);
		System.out.println("Start Date after formatting " + srtDate);
		LocalDate endDate = LocalDate.parse(toDate, formatter);
		System.out.println("End Date after formatting " + endDate);
		Period subtractDate = Period.between(srtDate, endDate);
		int noOfMonths = subtractDate.getMonths();
		return noOfMonths;
	}

	public int getYearsBetweenDates(String fromDate, String toDate, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate srtDate = LocalDate.parse(fromDate, formatter);
		System.out.println("Start Date after formatting " + srtDate);
		LocalDate endDate = LocalDate.parse(toDate, formatter);
		System.out.println("End Date after formatting " + endDate);
		Period subtractDate = Period.between(srtDate, endDate);
		int noOfYears = subtractDate.getYears();
		return noOfYears;
	}

	public void searchLeaseContract(String contractID) throws InterruptedException {
		driver.switchTo().defaultContent();
		clickByXpath(TririgaHomePage.lnkContracts);
		Thread.sleep(1500);
		clickByLinkText(TririgaHomePage.lnkLeases);
		switchFrameByID(LeasesPage.frame1);
		switchFrameByID(LeasesPage.frame2);
		switchFrameByID(LeasesPage.frame3);
		clickByXpath(LeasesPage.lnkClearAllFilters);
		enterByXpath(LeasesPage.txtID, contractID);
		driver.findElement(LeasesPage.txtID).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
	}

	public void searchLicenseIDNOpen(String contractID) throws InterruptedException {
		System.out.println("The Contract ID is " + contractID);
		searchLicenseContract(contractID);
		clickByXpath(LicensePage.lnkSearchResults);
		switchWindow();
	}

	public void searchLicenseContract(String contractID) throws InterruptedException {
		clickByXpath(TririgaHomePage.lnkContracts);
		clickByLinkText(TririgaHomePage.lnkLicense);
		switchFrameByID(LicensePage.frame1);
		switchFrameByID(LicensePage.frame2);
		switchFrameByID(LicensePage.frame3);
		clickByXpath(LicensePage.lnkClearAllFilters);
		enterByXpath(LicensePage.txtID_License, contractID);
		driver.findElement(LicensePage.txtID_License).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
	}

	public void clickContractsTabNSearchID(String contractID, String contractType) throws InterruptedException {
		try {
			System.out.println("The Contract Id inside clickContractsTabsearchID func is " + contractID);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			clickByXpath(TririgaHomePage.lnkContracts);
			if (contractType.equalsIgnoreCase("License")) {
				searchLicenseContract(contractID);
			} else {
				searchLeaseContract(contractID);
			}
			clickByXpath(LeasesPage.lnkSearchResults);
			switchWindow();
			report.updateTestLog("Click on Contracts Tab", "Contracts link should be clicked", Status.DONE);
		} catch (NoSuchElementException e) {
			report.updateTestLog("Click on Contracts Tab", "Contracts link should be clicked", Status.FAIL);
		}
	}

	public void clickContractsTab() throws InterruptedException {
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			clickByXpath(TririgaHomePage.lnkContracts);
			report.updateTestLog("Click on Contracts Tab", "Contracts link should be clicked", Status.DONE);
		} catch (NoSuchElementException e) {
			report.updateTestLog("Click on Contracts Tab", "Contracts link should be clicked", Status.FAIL);

		}
	}

	public String IsWebElementDisplayed(By by, String elementname) {
		String status = "PASS";
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = driver.getWebDriver().findElements(by).size() > 0;
		System.out.println("The boolean variable of " + elementname + " is " + exists);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		if (exists == true) {
			// System.out.println("Inside If condition of element displayed fn");
			// report.updateTestLog("Check Whether the WebElement is Dispalyed or Not", "The
			// WebElement "+elementname+" is displayed", Status.PASS);
			System.out.println("The element is displayed " + elementname);
			return status;
		} else {
			status = "FAIL";
			report.updateTestLog("Check Whether the WebElement is Dispalyed or Not",
					"The WebElement " + elementname + " is not displayed", Status.FAIL);
			return status;
		}
	}

	public void IsWebElementNotDisplayed(By by, String elementname) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = driver.getWebDriver().findElements(by).size() != 0;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		if (exists == true) {
			report.updateTestLog("Check Whether the WebElement is Not Dispalyed or Not",
					"The WebElement " + elementname + " is displayed", Status.FAIL);
		} else {
			report.updateTestLog("Check Whether the WebElement is Not Dispalyed or Not",
					"The WebElement " + elementname + " is not displayed", Status.DONE);
		}
	}

	public String IsWebElementNotDisplayedReturn(By by, String elementname) {
		String status = "FAIL";
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
		boolean exists = (driver.getWebDriver().findElements(by).size()) != 0;
		System.out.println("The exists variable in String of IsWebElementNotDisplayedReturn is " + exists);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		if (exists == true) {
			// report.updateTestLog("Check Whether the WebElement is Not Dispalyed or Not",
			// "The WebElement "+elementname+" is displayed", Status.FAIL);
			return status;
		} else {
			status = "PASS";
			// report.updateTestLog("Check Whether the WebElement is Not Dispalyed or Not",
			// "The WebElement "+elementname+" is not displayed", Status.PASS);
			return status;
		}
	}

	public void isClickable(By by, String fieldname) throws InterruptedException {
		try {
			if (this.isButtonVisibleAndclickable(by, fieldname) == true) {
				System.out.println(fieldname + " button is displayed and clickable");
				highlightWebElement(by, fieldname);
				report.updateTestLog("Validate whether the button is clickable or not",
						"The Text feild for " + fieldname + " is Clickable Only", Status.PASS);
			} else {
				System.out.print("Button is not clickable and displayed");
				report.updateTestLog(" Validate Button is clickable", "The Button is not clickable", Status.FAIL);
			}
		} catch (NoSuchElementException e) {
			// System.out.println(e.printStackTrace());
			e.printStackTrace();
			// report.updateTestLog(" Validate Button is clickable", "The Button is not
			// clickable", Status.valueOf(e.getMessage()));
			report.updateTestLog(" Validate Button is clickable", "The Button is not clickable", Status.FAIL);
		}
	}

	public boolean isButtonVisibleAndclickable(By by, String fieldname) throws InterruptedException {
		// driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		WebElement ele = driver.findElement(by);
		String buttonname = ele.getText();
		System.out.println("The Button name is " + buttonname);
		if ((ele.isDisplayed() == true) && (ele.isEnabled() == true) && (buttonname.equalsIgnoreCase(fieldname))) {
			System.out.println("Button is displayed and checking for clickable ?  ");
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.elementToBeClickable(ele));
			System.out.println("Button is clickable");
			return true;
		} else {
			System.out.println("Element is not displayed");
			return false;
		}
	}

	/*public void clickOnRecordName() {
		try {
			// Thread.sleep(5000);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String leaseID = dataTable.getData("General_Data", "LeaseID");
			System.out.println("The lease ID inside clickRecordName Method is " + leaseID);
			// getWindowTitle = driver.getWindowHandle();
			// System.out.println("Parent Process id: "+getWindowTitle);
			clickByXpath(ApproveLeaseAbstract_ReviewerPage.lnkRecordName);
			// driver.getWebDriver().findElement(By.xpath("//span[contains(text(),'"+leaseID+"')]")).click();
			report.updateTestLog("Click on the Record Name to Approve", "Record name should be clicked to Approve",
					Status.PASS);
		} catch (Exception e) {
			e.printStackTrace();
			report.updateTestLog("Click on the Record Name to Approve", "Record name should be clicked to Approve",
					Status.FAIL);
		}
	}
*/
	
	
	
	public int getLastDateOfMonth(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int mapLastDay = 0;
		switch (month) {
		case 1:
			mapLastDay = 31;
			break;
		case 3:
			mapLastDay = 31;
			break;
		case 5:
			mapLastDay = 31;
			break;
		case 7:
			mapLastDay = 31;
			break;
		case 8:
			mapLastDay = 31;
			break;
		case 10:
			mapLastDay = 31;
			break;
		case 12:
			mapLastDay = 31;
			break;
		case 2:
			if (0 == year % 4 && 0 != year % 100 || 0 == year % 400) {
				mapLastDay = 29;
			} else {
				mapLastDay = 28;
			}
			break;
		case 4:
			mapLastDay = 30;
			break;
		case 6:
			mapLastDay = 30;
			break;
		case 9:
			mapLastDay = 30;
			break;
		case 11:
			mapLastDay = 30;
			break;
		}
		return mapLastDay;
	}

	public Date formatStringToDate(String dateInString) throws ParseException {
		// DateFormat df = DateFormat.getInstance();
		Date formatedtoDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateInString);
		// Date formatedtoDate = df.parse(dateInString);

		return formatedtoDate;
	}

	public int getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public String getFirstDay(Date d) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date dddd = calendar.getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		return sdf1.format(dddd);
	}

	public String getLastDay(Date d) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date dddd = calendar.getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		return sdf1.format(dddd);
	}

	public void validateTwoMaps(Map<String, Double> map_payments_PLI, Map<String, Double> mapOS) {
		Map<String, Double> dataEntryavailableMap = new LinkedHashMap<String, Double>();
		Map<String, Double> dataEntryMissingMap = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Double> entry : map_payments_PLI.entrySet()) {
			String key = entry.getKey();
			Double value = entry.getValue();
			if (mapOS.containsKey(key) && mapOS.containsValue(value)) {
				dataEntryavailableMap.put(key, value);

			} else {
				dataEntryMissingMap.put(key, value);
			}
		}
		System.out.println("The dataEntryavailableMap Map is " + dataEntryavailableMap.toString());
		System.out.println("The dataEntryMissingMap Map is " + dataEntryMissingMap.toString());
		if (dataEntryMissingMap.isEmpty()) {
			report.updateTestLog("Validate whether the PLI are available in FLI Operating schedule",
					"All the entries of PLI are available in FLI " + dataEntryavailableMap.toString()
							+ " The entries of PLI unavailable in FLI are " + dataEntryMissingMap.toString(),
					Status.PASS);
		} else {
			report.updateTestLog("Validate whether the PLI are available in FLI Operating schedule",
					"All the entries of PLI are available in FLI " + dataEntryavailableMap.toString()
							+ " The entries of PLI unavailable in FLI are " + dataEntryMissingMap.toString(),
					Status.FAIL);
			//Testing
		}
	}

	// history
	public static List<String> idlistbeforerevise = new ArrayList<String>();
	public static List<String> idlistafteractive = new ArrayList<String>();
	// public static List<String> idretain = new ArrayList<String>();
	public static List<String> newidlistafteractive = new ArrayList<String>();

	// Capture history
		public void captureHistoryRecords() throws InterruptedException {
			try { // *[@id='dataTab']/tbody/tr/th
				driver.switchTo().defaultContent();
				clickByXpath(ReviseContract_HistoryTabPage.tabHistory);
				System.out.println("History tab is clicked");
				switchFrameByID(ReviseContract_HistoryTabPage.frame_HistoryTab);
				switchFrameByID(ReviseContract_HistoryTabPage.frame_AmendmentsHistoryofRealEstateLease);
				String textOfHistory = driver.findElement(By.xpath(
						"//*[@summary='Amendments History of Real Estate Lease']/tbody/tr/th|//*[@summary='Amendments History of Real Estate Lease']/tbody/tr[1]/td[2]/span/a"))
						.getText();
				System.out.println("History Records: " + textOfHistory);
				if (textOfHistory.contains("No data")) {
					report.updateTestLog("History ID", textOfHistory, Status.DONE);
				} else {
					WebElement table_history = driver
							.findElement(ReviseContract_HistoryTabPage.table_AmendmentsHistoryofRealEstateLease);
					List<WebElement> table_history_rows = table_history.findElements(By.tagName("tr"));
					System.out.println("Number of Rows:" + table_history_rows.size());
					// sizeofidlist=table_history_rows.size();
					for (int tableRow = 1; tableRow <= table_history_rows.size(); tableRow++) {
						System.out.println("Iteration: " + tableRow);
						By history_table_TR = By
								.xpath("//*[@summary='Amendments History of Real Estate Lease']/tbody/tr[" + tableRow
										+ "]/td[2]/span|//*[@summary='Amendments History of Real Estate Lease']/tbody/tr["
										+ tableRow + "]/td[2]/span/a");
						String txt_history_id = driver.findElement(history_table_TR).getText().trim();
						idlistbeforerevise.add(txt_history_id);
						
					}
					
					System.out.println("History id before revising" + idlistbeforerevise);
					report.updateTestLog("History ID", "History ID is: " + idlistbeforerevise, Status.DONE);
				}
				driver.switchTo().defaultContent();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public void validateHistoryRecords() throws InterruptedException {

		// idlist.add("33");
		boolean isValueFnd = false;
		try {
			// idlist.add("33");
			// idlist.add("1164646");
			driver.switchTo().defaultContent();
			clickByXpath(ReviseContract_HistoryTabPage.tabHistory);
			System.out.println("History tab is clicked");
			switchFrameByID(ReviseContract_HistoryTabPage.frame_HistoryTab);
			switchFrameByID(ReviseContract_HistoryTabPage.frame_AmendmentsHistoryofRealEstateLease);
			WebElement table_history = driver
					.findElement(ReviseContract_HistoryTabPage.table_AmendmentsHistoryofRealEstateLease);
			List<WebElement> table_history_rows = table_history.findElements(By.tagName("tr"));
			System.out.println("Number of Rows:" + table_history_rows.size());
			// sizeofidlistafteractive=table_history_rows.size();
			for (int tableRow = 1; tableRow <= table_history_rows.size(); tableRow++) {
				isValueFnd = false;
				System.out.println("Iteration: " + tableRow);
				By history_table_TR = By.xpath(
						"//*[@id='dataTab']/tbody/tr[" + tableRow + "]/td[contains(@headers,'columnID')]/span/a");
				String txt_history_id = driver.findElement(history_table_TR).getText().trim();
				System.out.println("The History id in " + txt_history_id);
				idlistafteractive.add(txt_history_id);

				/*
				 * for(String s : idlistafteractive) { if(s.contains(txt_history_id)) {
				 * isValueFnd = true; break; } }
				 * 
				 * if(isValueFnd) { System.out.println("ID found  --> "+txt_history_id);
				 * report.updateTestLog("History ID Matched after active is ",
				 * "History ID Matched after active is " + txt_history_id, Status.PASS); }else {
				 * System.out.println("Id does not match new id is --> "+txt_history_id);
				 * report.updateTestLog("History ID", "History ID Not  Matched after active is "
				 * + txt_history_id, Status.FAIL); }
				 */

			}
			System.out.println("Before revise idlist is" + idlistbeforerevise);
			System.out.println("After active of contract idlist is" + idlistafteractive);
			newidlistafteractive.addAll(idlistafteractive);
			newidlistafteractive.removeAll(idlistbeforerevise);
			idlistafteractive.retainAll(idlistbeforerevise);
			System.out.println("ID found  --> " + idlistafteractive);
			report.updateTestLog("History ID Matched after active is ",
					"History ID Matched after active is " + idlistafteractive, Status.PASS);
			report.updateTestLog("New History ID ", "New History ID is " + newidlistafteractive, Status.PASS);
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static List<String> idlistbeforereviseli = new ArrayList<String>();
		public static List<String> idlistafteractiveli = new ArrayList<String>();
		// public static List<String> idretain = new ArrayList<String>();
		public static List<String> newidlistafteractiveli = new ArrayList<String>();
	public void captureHistoryRecordsLicense() throws InterruptedException {
		try {
			driver.switchTo().defaultContent();
			clickByXpath(ReviseContract_HistoryTabPage.tabHistory);
			System.out.println("History tab is clicked");
			switchFrameByID(ReviseContract_HistoryTabPage.frame_HistoryTab);
			switchFrameByID(ReviseContract_HistoryTabPage.frame_AmendmentsHistoryofRealEstateLease);
			String textOfHistory = driver.findElement(By.xpath("//*[@summary='Amendments History of Real Estate License']/tbody/tr[1]/td[2]/span/a|//*[@summary='Amendments History of Real Estate License']/tbody/tr/th")).getText();	
			System.out.println("History Records: " + textOfHistory);
			if (textOfHistory.contains("No data")) {
				report.updateTestLog("History ID", textOfHistory, Status.DONE);
			} else {
			
			WebElement table_history = driver
					.findElement(ReviseContract_HistoryTabPage.table_AmendmentsHistoryofRealEstateLicense);
			List<WebElement> table_history_rows = table_history.findElements(By.tagName("tr"));
			System.out.println("Number of Rows:" + table_history_rows.size());			
			for (int tableRow = 1; tableRow <= table_history_rows.size(); tableRow++) {
				System.out.println("Iteration: " + tableRow);
				By history_table_TR = By.xpath("//*[@summary='Amendments History of Real Estate License']/tbody/tr["
						+ tableRow + "]/td[2]/span/a|//*[@summary='Amendments History of Real Estate License']/tbody/tr/th");
				String txt_history_id = driver.findElement(history_table_TR).getText().trim();
				idlistbeforereviseli.add(txt_history_id);
				
			}			
			System.out.println("History id before revising" + idlistbeforereviseli);
			report.updateTestLog("History ID", "History ID is: " + idlistbeforereviseli, Status.DONE);
			
		} 
			driver.switchTo().defaultContent();
		}
			catch (Exception e) {
				System.out.println("History id before revising" + idlistbeforereviseli);
				report.updateTestLog("History ID", "History ID is: " + idlistbeforereviseli, Status.DONE);
				driver.switchTo().defaultContent();
			e.printStackTrace();
		}
	}

	public void validateHistoryRecordslicensess() throws InterruptedException 
		try {
			// idlist.add("33");
			// idlist.add("1164646");
			driver.switchTo().defaultContent();
			clickByXpath(ReviseContract_HistoryTabPage.tabHistory);
			System.out.println("History tab is clicked");
			switchFrameByID(ReviseContract_HistoryTabPage.frame_HistoryTab);
			switchFrameByID(ReviseContract_HistoryTabPage.frame_AmendmentsHistoryofRealEstateLease);
			String textOfHistory = driver.findElement(By.xpath("//*[@summary='Amendments History of Real Estate License']/tbody/tr[1]/td[2]/span/a|//*[@summary='Amendments History of Real Estate License']/tbody/tr/th")).getText();	
			System.out.println("History Records: " + textOfHistory);
			if (textOfHistory.contains("No data")) {
				report.updateTestLog("History ID", textOfHistory, Status.DONE);
			} else {
			WebElement table_history = driver
					.findElement(ReviseContract_HistoryTabPage.table_AmendmentsHistoryofRealEstateLease);
			List<WebElement> table_history_rows = table_history.findElements(By.tagName("tr"));
			System.out.println("Number of Rows:" + table_history_rows.size());
			// sizeofidlistafteractive=table_history_rows.size();
			for (int tableRow = 1; tableRow <= table_history_rows.size(); tableRow++) {
				isValueFnd = false;
				System.out.println("Iteration: " + tableRow);
				By history_table_TR = By.xpath(
						"//*[@id='dataTab']/tbody/tr[" + tableRow + "]/td[contains(@headers,'columnID')]/span/a");
				String txt_history_id = driver.findElement(history_table_TR).getText().trim();
				
				System.out.println("The History id in " + txt_history_id);
				idlistafteractiveli.add(txt_history_id);

				/*
				 * for(String s : idlistafteractive) { if(s.contains(txt_history_id)) {
				 * isValueFnd = true; break; } }
				 * 
				 * if(isValueFnd) { System.out.println("ID found  --> "+txt_history_id);
				 * report.updateTestLog("History ID Matched after active is ",
				 * "History ID Matched after active is " + txt_history_id, Status.PASS); }else {
				 * System.out.println("Id does not match new id is --> "+txt_history_id);
				 * report.updateTestLog("History ID", "History ID Not  Matched after active is "
				 * + txt_history_id, Status.FAIL); }
				 */

			}
			System.out.println("Before revise idlist is" + idlistbeforereviseli);
			System.out.println("After active of contract idlist is" + idlistafteractiveli);
			newidlistafteractiveli.addAll(idlistafteractiveli);
			newidlistafteractiveli.removeAll(idlistbeforereviseli);
			idlistafteractiveli.retainAll(idlistbeforereviseli);
			System.out.println("ID found  --> " + idlistafteractiveli);
			report.updateTestLog("History ID Matched after active is ",
					"History ID Matched after active is " + idlistafteractiveli, Status.PASS);
			if(newidlistafteractiveli.isEmpty()) {
                System.out.println("No New History Id found ");
          }
          else {
          report.updateTestLog("New History ID ", "New History ID is " + newidlistafteractiveli, Status.PASS);
          }

			}
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Before revise idlist is" + idlistbeforereviseli);
			System.out.println("After active of contract idlist is" + idlistafteractiveli);
			newidlistafteractiveli.addAll(idlistafteractiveli);
			newidlistafteractiveli.removeAll(idlistbeforereviseli);
			idlistafteractiveli.retainAll(idlistbeforereviseli);
			System.out.println("ID found  --> " + idlistafteractiveli);
			report.updateTestLog("History ID Matched after active is ",
					"History ID Matched after active is " + idlistafteractiveli, Status.PASS);
		}
		if(newidlistafteractiveli.isEmpty()) {
            System.out.println("No New History Id found ");
      }
      else {
      report.updateTestLog("New History ID ", "New History ID is " + newidlistafteractiveli, Status.PASS);
      }
		driver.switchTo().defaultContent();
	}
	/**Method to take screenshot
	 * 
	 */
	public void takeScreenshotReaccountSchedule() {
		report.updateTestLog("ReAccountSchedule Button is clicked", "ReAccountSchedule Button is clicked",Status.SCREENSHOT);
	}

}
