package com.udacity.jwdnd.course1.cloudstorage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage_Credentials {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(id = "add-new-credential-button")
    private WebElement addButton;

    @FindBy(id = "credential-url")
    private WebElement add__url;
    
    @FindBy(id = "credential-username")
    private WebElement add__username;

    @FindBy(id = "credential-password")
    private WebElement add__password;

    @FindBy(id = "credentialSubmit")
    private WebElement add__submit;

    @FindBy(id = "credentialTable")
    private WebElement credTable;

    @FindBy(className = "edit-credential-button")
    private WebElement editButton;

    @FindBy(id = "edit-credential-url")
    private WebElement edit__url;

    @FindBy(id = "edit-credential-username")
    private WebElement edit__username;

    @FindBy(id = "edit-credential-password")
    private WebElement edit__password;

    @FindBy(id = "save-credential-button")
    private WebElement edit__submit;

    @FindBy(className = "delete-credential-button")
    private WebElement deleteButton;

    public HomePage_Credentials(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void addCredential(String url, String username, String password){
        // click the button
        wait.until(ExpectedConditions.elementToBeClickable(addButton))
            .click();
        
        // fill in the data
        wait.until(ExpectedConditions.visibilityOf(add__url));
        add__url.sendKeys(url);
        add__username.sendKeys(username);
        add__password.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(add__submit))
            .click();

        // wait until the changes are shown on the page
        wait.until(ExpectedConditions.textToBePresentInElement(credTable, url));
    }

    public String getPlainPassword(){
        // click the edit button to uncipher
        wait.until(ExpectedConditions.elementToBeClickable(editButton))
            .click();

        // retrieve password 
        String pwd = wait.until(ExpectedConditions.visibilityOf(edit__password))
                        .getAttribute("value");

        return pwd;
    }

    public void editOnlyCredential(String newUrl, String newUsername, String newPassword){
        // click the button
        wait.until(ExpectedConditions.elementToBeClickable(editButton))
            .click();
        
        // fill in the data
        wait.until(ExpectedConditions.visibilityOf(edit__url));
        edit__url.clear();
        edit__url.sendKeys(newUrl);

        edit__username.clear();
        edit__username.sendKeys(newUsername);

        edit__password.clear();
        edit__password.sendKeys(newPassword);

        wait.until(ExpectedConditions.elementToBeClickable(edit__submit))
            .click();

        // Wait until change is present on website
        wait.until(ExpectedConditions.textToBePresentInElement(credTable, newUrl));
    }

    public boolean credTableContains(String text){
        wait.until(ExpectedConditions.visibilityOf(credTable));
        String contentTable = credTable.getText();
        return contentTable.contains(text);
    } 

    public void deleteOnlyCredential(String url){
        // click the delete button of the only element
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton))
            .click();

        // wait until page refreshes
        wait.until(ExpectedConditions.not(
            ExpectedConditions.textToBePresentInElement(credTable, url)
        ));
        
    }
}
