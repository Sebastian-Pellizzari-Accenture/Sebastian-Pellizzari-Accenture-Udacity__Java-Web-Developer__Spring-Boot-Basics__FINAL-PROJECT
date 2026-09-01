package com.udacity.jwdnd.course1.cloudstorage;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SignupPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // I need the 4 text fields
    @FindBy(id = "inputFirstName")
    private WebElement firstname_field;

    @FindBy(id = "inputLastName")
    private WebElement lastname_field;

    @FindBy(id = "inputUsername")
    private WebElement username_field;

    @FindBy(id = "inputPassword")
    private WebElement password_field;

    // I also need the submit button
    @FindBy(id = "buttonSignUp")
    private WebElement submitButton;

    // and the success / error message for the sake of feedback
    @FindBy(id = "success-msg")
    private WebElement success_msg;

    @FindBy(id = "error-msg")
    private WebElement error_msg;

    // misc
    @FindBy(id = "back-to-login")
    private WebElement back_to_login;


    // and some data
    private final String FIRSTNAME = "firstname1";          // 0
    private final String LASTNAME = "lastname1";            // 1
    private final String USERNAME = "user1";                // 2
    private final String PASSWORD = "pwd1";                 // 3
    
    private final String INVALID_FIRSTNAME = "firstname1firstname1firstname1";
    private final String INVALID_LASTNAME = "lastname1lastname1lastname1lastname1";
    private final String INVALID_USERNAME = "user1user1user1user1user1user1user1";
    private final String INVALID_PASSWORD = "pwd1pwd1pwd1pwd1pwd1pwd1pwd1pwd1";

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * defines the workflow of entering all the data
     */
    public void register(List<Integer> invalid_pos) {
        // clear all the textfields
        firstname_field.clear();
        lastname_field.clear();
        username_field.clear();
        password_field.clear();

        // enter the data
        firstname_field.sendKeys(invalid_pos.contains(0)? INVALID_FIRSTNAME : FIRSTNAME);
        lastname_field.sendKeys(invalid_pos.contains(1)? INVALID_LASTNAME : LASTNAME);
        username_field.sendKeys(invalid_pos.contains(2)? INVALID_USERNAME : USERNAME);
        password_field.sendKeys(invalid_pos.contains(3)? INVALID_PASSWORD : PASSWORD);

        // submit the data
        submitButton.click();
    }

    /**
     * checks what the website returns after the button was pressed. 
     * Also wait for 10 seconds after the button was pressed.
     */
    public boolean isSuccessDisplayed(){
        try{
            wait.until(ExpectedConditions.visibilityOf(success_msg));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean isErrorDisplayed(){
        try{
            wait.until(ExpectedConditions.visibilityOf(error_msg));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // this may also become handy
    public void clickLogin(){
        wait.until(ExpectedConditions.elementToBeClickable(back_to_login));
        back_to_login.click();
    }

}
