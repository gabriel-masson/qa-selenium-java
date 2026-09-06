package com.qa.automationexercise.pages;

import com.qa.automationexercise.models.AccountInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Representa a página "ENTER ACCOUNT INFORMATION", preenchida logo após o
 * início do cadastro em LoginPage.startSignup(...).
 */
public class AccountInfoPage extends BasePage {

    private final By titleMrRadio = By.id("id_gender1");
    private final By titleMrsRadio = By.id("id_gender2");
    private final By passwordInput = By.id("password");
    private final By daysSelect = By.id("days");
    private final By monthsSelect = By.id("months");
    private final By yearsSelect = By.id("years");
    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By companyInput = By.id("company");
    private final By address1Input = By.id("address1");
    private final By address2Input = By.id("address2");
    private final By countrySelect = By.id("country");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipcodeInput = By.id("zipcode");
    private final By mobileNumberInput = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");

    public AccountInfoPage(WebDriver driver) {
        super(driver);
    }

    public AccountCreatedPage fillForm(AccountInfo accountInfo) {
        selectTitle(accountInfo.getTitle());
        type(passwordInput, accountInfo.getPassword());

        selectByValue(daysSelect, accountInfo.getBirthDay());
        selectByValue(monthsSelect, accountInfo.getBirthMonth());
        selectByValue(yearsSelect, accountInfo.getBirthYear());

        type(firstNameInput, accountInfo.getFirstName());
        type(lastNameInput, accountInfo.getLastName());
        type(companyInput, accountInfo.getCompany());
        type(address1Input, accountInfo.getAddress1());
        type(address2Input, accountInfo.getAddress2());

        selectByVisibleText(countrySelect, accountInfo.getCountry());

        type(stateInput, accountInfo.getState());
        type(cityInput, accountInfo.getCity());
        type(zipcodeInput, accountInfo.getZipcode());
        type(mobileNumberInput, accountInfo.getMobileNumber());

        click(createAccountButton);
        return new AccountCreatedPage(driver);
    }

    private void selectTitle(String title) {
        By radio = "Mrs".equalsIgnoreCase(title) ? titleMrsRadio : titleMrRadio;
        click(radio);
    }

    private void selectByValue(By locator, String value) {
        Select select = new Select(waitForVisible(locator));
        select.selectByValue(value);
    }

    private void selectByVisibleText(By locator, String visibleText) {
        Select select = new Select(waitForVisible(locator));
        select.selectByVisibleText(visibleText);
    }
}