package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By signupLoginLink = By.linkText("Signup / Login");
    private final By logoutLink = By.linkText("Logout");
    private final By deleteAccountLink = By.linkText("Delete Account");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage goToLoginPage() {
        click(signupLoginLink);
        return new LoginPage(driver);
    }

    public boolean isUserLoggedIn(String username) {
        return isVisible(By.xpath("//a[contains(.,'Logged in as " + username + "')]"));
    }

    public void logout() {
        click(logoutLink);
    }

    public void deleteAccount() {
        click(deleteAccountLink);
    }

    public boolean isLoaded() {
        return getPageTitle().contains("Automation Exercise");
    }
}
