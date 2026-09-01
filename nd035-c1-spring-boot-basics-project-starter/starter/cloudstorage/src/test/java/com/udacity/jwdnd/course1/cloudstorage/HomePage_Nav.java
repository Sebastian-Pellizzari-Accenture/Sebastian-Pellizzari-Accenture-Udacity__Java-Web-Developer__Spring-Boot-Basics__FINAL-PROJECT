package com.udacity.jwdnd.course1.cloudstorage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage_Nav {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteNav;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credNav;

    public HomePage_Nav(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton))
            .click();
        wait.until(ExpectedConditions.urlContains("/login"));
    }

    public void clickNotesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(noteNav))
            .click();
    }

    public void clickCredentialsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(credNav))
            .click();
    }
}
