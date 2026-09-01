# Drive Application
## Source 
This project originated from Udacity's [Java Web Developer course](https://www.udacity.com/enrollment/nd035) and was the final project of chapter 2 (Spring Boot Basics). The provided code can be found [here](https://github.com/udacity/nd035-c1-spring-boot-basics-project-starter/tree/master/starter/cloudstorage). 
The task description of the code owner is located in the README files inside the nd035.. folder.

`NOTE:` This code was implemented to run in Udacity's workspace. It should be possible to execute the code on a local machine by removing the `/proxy/8080` from the html-file, however the code was not tested on a local machine!

## Competences - Outline copied from [here](https://learn.udacity.com/nd035?version=6.0.6&partKey=cd0626&lessonKey=ls14002&conceptKey=62eaa587-4357-4304-9729-ce0de62cfa78)
* Basics of `Java server architecture`, dependency management in Java, and how Spring integrates with both.
* `Core Spring principles`. We'll be covering dependency injection, bean configuration, service development, and server-wide configuration.
* `Spring MVC and Thymeleaf`, an HTML template engine. We'll talk about Spring controllers, Thymeleaf template attributes, and connecting the two with the MVC pattern.
* `Connecting your Spring app to a database` and securing it with `Spring Security`. We'll cover the basics of ORM and MyBatis, an ORM tool for Java. We'll use the database to store user credentials securely and use them to authenticate users with Spring Security.
* `Testing and web browser automation with Selenium`. We'll cover how to set up and run tests with JUnit, how a web driver works, and how to simulate user actions in the browser with Selenium. We'll also discuss page objects, Selenium's powerful abstraction tool.

## Screenshots of working application
### Sign up
![](/images/Screenshot+2026-09-01+122850.png)
Successful sign up.
![](/images/Screenshot+2026-09-01+122850.png)
Un-successful sign up. (Username already exists)
### Login
If the username and password match and exist, the website automatically forwards to the homepage.
Otherwise, a respective error message is displayed describing the problem.
![](/images/Screenshot+2026-09-01+122817.png)
### Files
Files can be uploaded to the homepage by choosing a file and clicking the upload button.
![](/images/Screenshot+2026-09-01+122319.png)
They can be downloaded again by pressing the `view` button and deleted by the `delete` button.
![](/images/Screenshot+2026-09-01+122350.png)
### Notes
TBA

### Credentials 
TBA
