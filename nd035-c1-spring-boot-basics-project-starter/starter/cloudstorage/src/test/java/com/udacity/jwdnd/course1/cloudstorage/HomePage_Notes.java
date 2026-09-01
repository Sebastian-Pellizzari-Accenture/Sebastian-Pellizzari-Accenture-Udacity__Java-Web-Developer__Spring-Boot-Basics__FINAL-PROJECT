package com.udacity.jwdnd.course1.cloudstorage;

import static org.mockito.ArgumentMatchers.contains;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage_Notes {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(id = "add-new-note-button")
    private WebElement newNoteButton;

    @FindBy(id = "note-title")
    private WebElement add__noteTitle;

    @FindBy(id = "note-description")
    private WebElement add__noteDescription;
    
    @FindBy(id = "noteSubmit")
    private WebElement add__noteSubmit;

    @FindBy(id = "userTable")
    private WebElement userTable;

    @FindBy(className = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(name = "noteTitle")
    private WebElement edit__noteTitle;

    @FindBy(name = "noteDescription")
    private WebElement edit__noteDescription;

    @FindBy(id = "save-note-button")
    private WebElement edit__noteSubmit;

    @FindBy(className = "delete-note-button")
    private WebElement deleteNoteButton;

    public HomePage_Notes(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void addNewNote(String title, String description){
        // open the form
        wait.until(ExpectedConditions.elementToBeClickable(newNoteButton))
            .click();
        
        // fill in the data
        wait.until(ExpectedConditions.visibilityOf(add__noteTitle))
            .sendKeys(title);
        add__noteDescription.sendKeys(description);
        add__noteSubmit.click();
            
        // wait that the changes reflet on the website
        wait.until(ExpectedConditions.textToBePresentInElement(userTable, description));
    }

    /**
     * Assume for testing that we only create one note
     *  */ 
    public void editOnlyNote(String newTitle, String newDescription){
        // click button
        wait.until(ExpectedConditions.elementToBeClickable(editNoteButton))
            .click();
        
        // edit data
        wait.until(ExpectedConditions.visibilityOf(edit__noteTitle));
        edit__noteTitle.clear();
        edit__noteTitle.sendKeys(newTitle);

        edit__noteDescription.clear();
        edit__noteDescription.sendKeys(newDescription);

        wait.until(ExpectedConditions.elementToBeClickable(edit__noteSubmit))
            .click();

        // wait until changes are reflected on page
        wait.until(ExpectedConditions.textToBePresentInElement(userTable, newDescription));
    }

    /**
     * Assume for testing that we only create one note
     *  */ 
    public void deleteOnlyNote(String title) {
        // click button
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton))
            .click();

        // wait until the entry is NOT present any more
        wait.until(ExpectedConditions.not(
            ExpectedConditions.textToBePresentInElement(userTable, title)
        ));
    }

    public boolean noteTableContains(String text){
        wait.until(ExpectedConditions.visibilityOf(userTable));
        String contentTable = userTable.getText();
        return contentTable.contains(text);
    }    
}
