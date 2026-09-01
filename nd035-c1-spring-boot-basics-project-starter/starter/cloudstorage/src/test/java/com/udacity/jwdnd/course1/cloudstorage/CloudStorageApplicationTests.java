package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private HomePage_Nav home_nav_page;
	private HomePage_Notes home_notes_page;
	private HomePage_Credentials home_creds_page;

	private String signupLink;
	private String loginLink;
	private String homeLink;

	private final String url1 = "https://some-website.at";
	private final String username1 = "username1";
	private final String password1 = "password1";

	private final String url2 = "https://another-website.at";
	private final String username2 = "username2";
	private final String password2 = "password2";

	private final String test_title_1 = "1: Example test title";
	private final String test_description_1 = "1: Example test description";
	private final String test_title_2 = "2: Example test title";
	private final String test_description_2 = "2: Example test description";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		home_nav_page = new HomePage_Nav(driver);
		home_notes_page = new HomePage_Notes(driver);
		home_creds_page = new HomePage_Credentials(driver);

		signupLink = "http://localhost:" + this.port + "/signup";
		loginLink = "http://localhost:" + this.port + "/login";
		homeLink = "http://localhost:" + this.port + "/home";
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 - Forbidden"));

	}
	/********************************************************************
	 * 				HERE IS THE IMPLEMENTATION OF MY OWN TESTS			*
	 ********************************************************************/

	 // Write a test that verifies that an unauthorized user can only access the login and signup pages.
	@Test
	public void testUnauthorisedAccess() {
		driver.get(loginLink);
		Assertions.assertEquals(loginLink, driver.getCurrentUrl());
		driver.get(signupLink);
		Assertions.assertEquals(signupLink, driver.getCurrentUrl());
		driver.get(homeLink);
		Assertions.assertEquals(loginLink, driver.getCurrentUrl());
	}

	private void checkPageAccess(String access, String goal) {
		driver.get(access);
		Assertions.assertEquals(goal, driver.getCurrentUrl());
	}


	// Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, 
	// 		and verifies that the home page is no longer accessible.
	@Test
	public void testLoginLogoutAccess() {
		// Create a test account
		doMockSignUp("a","a","a_username","a_password");
		doLogIn("a_username","a_password");

		// Check availability of the home page
		checkPageAccess(homeLink, homeLink);
		home_nav_page.logout();
		checkPageAccess(homeLink, loginLink);
	}

	// 
	// 1) Write a test that creates a note, and verifies it is displayed.
	// 2) Write a test that edits an existing note and verifies that the changes are displayed.
	// 3) Write a test that deletes a note and verifies that the note is no longer displayed.

	@Test
	public void addNoteTest() {
		// Create a test account
		doMockSignUp("g","g","g_username","g_password");
		doLogIn("g_username","g_password");
		checkPageAccess(homeLink, homeLink);

		// click on the notes Tab and add new note tab
		home_nav_page.clickNotesTab();
		home_notes_page.addNewNote(test_title_1, test_description_1);

		// Verify the note is shown
		Assertions.assertTrue(home_notes_page.noteTableContains(test_title_1));
		Assertions.assertTrue(home_notes_page.noteTableContains(test_description_1));
	}
	// Test 2
	@Test
	public void editNoteTest() {
		// Create a test account
		doMockSignUp("b","b","b_username","b_password");
		doLogIn("b_username","b_password");
		checkPageAccess(homeLink, homeLink);

		// click on the notes Tab and add new note tab
		home_nav_page.clickNotesTab();
		home_notes_page.addNewNote(test_title_1, test_description_1);
		home_notes_page.editOnlyNote(test_title_2, test_description_2);

		// Verify the updated note is shown
		Assertions.assertTrue(home_notes_page.noteTableContains(test_title_2));
		Assertions.assertTrue(home_notes_page.noteTableContains(test_description_2));
		Assertions.assertFalse(home_notes_page.noteTableContains(test_title_1));
		Assertions.assertFalse(home_notes_page.noteTableContains(test_description_1));
	}

	// Test 3
	@Test
	public void deleteNoteTest() {
		// Create a test account
		doMockSignUp("c","c","c_username","c_password");
		doLogIn("c_username","c_password");
		checkPageAccess(homeLink, homeLink);

		// click on the notes Tab and add new note tab
		home_nav_page.clickNotesTab();
		home_notes_page.addNewNote(test_title_1, test_description_1);
		home_notes_page.deleteOnlyNote(test_title_1);

		// Verify the updated note is gone
		Assertions.assertFalse(home_notes_page.noteTableContains(test_title_1));
		Assertions.assertFalse(home_notes_page.noteTableContains(test_description_1));
	}

	/**
	* 1) Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
	* 2) Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	* 3) Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
	 */

	 // Test 1
	@Test
	public void addCredentialTest() {
		// Create a test account
		doMockSignUp("f","f","f_username","f_password");
		doLogIn("f_username","f_password");
		checkPageAccess(homeLink, homeLink);

		// click on the notes Tab and add new note tab
		home_nav_page.clickCredentialsTab();
		home_creds_page.addCredential(url1, username1, password1);

		// Verify the credential is shown
		Assertions.assertTrue(home_creds_page.credTableContains(url1));
		Assertions.assertTrue(home_creds_page.credTableContains(username1));
		Assertions.assertFalse(home_creds_page.credTableContains(password1)); // ciphered
		Assertions.assertEquals(password1, home_creds_page.getPlainPassword());
	}

	// Test 2
	@Test
	public void editCredentialTest() {
		// Create a test account
		doMockSignUp("d","d","d_username","d_password");
		doLogIn("d_username","d_password");
		checkPageAccess(homeLink, homeLink);

		// click on the notes Tab and add new note tab
		home_nav_page.clickCredentialsTab();
		home_creds_page.addCredential(url1, username1, password1);
		home_creds_page.editOnlyCredential(url2, username2, password2);

		Assertions.assertTrue(home_creds_page.credTableContains(url2));
		Assertions.assertTrue(home_creds_page.credTableContains(username2));
		Assertions.assertFalse(home_creds_page.credTableContains(password2)); // ciphered
		Assertions.assertFalse(home_creds_page.credTableContains(url1));
		Assertions.assertFalse(home_creds_page.credTableContains(username1));
		Assertions.assertEquals(password2, home_creds_page.getPlainPassword());
	}	

	// Test 3
	@Test
	public void deleteCredentialTest() {
		// Create a test account
		doMockSignUp("e","e","e_username","e_password");
		doLogIn("e_username","e_password");
		checkPageAccess(homeLink, homeLink);

		// click on the notes Tab and add new note tab
		home_nav_page.clickCredentialsTab();
		home_creds_page.addCredential(url1, username1, password1);
		home_creds_page.deleteOnlyCredential(url1);

		// Verify the updated note is gone
		Assertions.assertFalse(home_creds_page.credTableContains(url1));
		Assertions.assertFalse(home_creds_page.credTableContains(username1));
	}
}
