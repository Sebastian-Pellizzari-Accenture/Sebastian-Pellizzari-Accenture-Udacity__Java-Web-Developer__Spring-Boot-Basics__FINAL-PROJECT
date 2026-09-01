package com.udacity.jwdnd.course1.cloudstorage;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // I need both fields
    @FindBy(id = "inputUsername")
    private WebElement username_field;

    @FindBy(id = "inputPassword")
    private WebElement password_field;

    // and the success / error message for the sake of feedback
    @FindBy(id = "logout-msg")
    private WebElement logout_msg;

    @FindBy(id = "error-msg")
    private WebElement error_msg;

    // I also need the submit button
    @FindBy(id = "login-button")
    private WebElement submitButton;

    // misc
    @FindBy(id = "signup-link")
    private WebElement back_to_signup;

    // and some data
    private final String USERNAME = "user1";                    // 0
    private final String PASSWORD = "pwd1";                     // 1
    private final String INVALID_USERNAME = "invalid_user1";               
    private final String INVALID_PASSWORD = "invalid_pwd1"; 
         
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * defines the workflow of entering all the data
     */
    public void login(List<Integer> invalid_pos) {
        // clear all the textfields
        username_field.clear();
        password_field.clear();

        // enter the data
        username_field.sendKeys(invalid_pos.contains(0)? INVALID_USERNAME : USERNAME);
        password_field.sendKeys(invalid_pos.contains(1)? INVALID_PASSWORD : PASSWORD);

        // submit the data
        submitButton.click();
    }
    
    /**
     * checks what the website returns.
     * Also wait for 10 seconds after the button was pressed.
     */
    public boolean isLogoutDisplayed(){
        try{
            wait.until(ExpectedConditions.visibilityOf(logout_msg));
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
    public void clickSignup(){
        wait.until(ExpectedConditions.elementToBeClickable(back_to_signup));
        back_to_signup.click();
    }

}
