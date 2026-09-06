package com.qa.automationexercise.models;

/**
 * Representa os dados preenchidos no formulário "ENTER ACCOUNT INFORMATION"
 * (AccountInfoPage). Construído via Builder para deixar explícito, no teste,
 * quais campos importam para aquele cenário — sem exigir 15 parâmetros
 * posicionais nem depender de chaves de Map digitadas à mão.
 */
public class AccountInfo {

    private final String title;
    private final String password;
    private final String birthDay;
    private final String birthMonth;
    private final String birthYear;
    private final String firstName;
    private final String lastName;
    private final String company;
    private final String address1;
    private final String address2;
    private final String country;
    private final String state;
    private final String city;
    private final String zipcode;
    private final String mobileNumber;

    private AccountInfo(Builder builder) {
        this.title = builder.title;
        this.password = builder.password;
        this.birthDay = builder.birthDay;
        this.birthMonth = builder.birthMonth;
        this.birthYear = builder.birthYear;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.company = builder.company;
        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.country = builder.country;
        this.state = builder.state;
        this.city = builder.city;
        this.zipcode = builder.zipcode;
        this.mobileNumber = builder.mobileNumber;
    }

    public String getTitle() { return title; }
    public String getPassword() { return password; }
    public String getBirthDay() { return birthDay; }
    public String getBirthMonth() { return birthMonth; }
    public String getBirthYear() { return birthYear; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCompany() { return company; }
    public String getAddress1() { return address1; }
    public String getAddress2() { return address2; }
    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public String getZipcode() { return zipcode; }
    public String getMobileNumber() { return mobileNumber; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        // Valores padrão: cobrem o "caminho feliz" sem o teste precisar
        // especificar todos os 15 campos toda vez.
        private String title = "Mr";
        private String password = "Senha@123";
        private String birthDay = "10";
        private String birthMonth = "5";
        private String birthYear = "1995";
        private String firstName = "QA";
        private String lastName = "Portfolio";
        private String company = "Automation Exercise Study";
        private String address1 = "Rua dos Testes, 123";
        private String address2 = "";
        private String country = "Canada";
        private String state = "xxxxx";
        private String city = "xxxxxxx";
        private String zipcode = "15000000";
        private String mobileNumber = "99999999999";

        public Builder title(String title) { this.title = title; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder birthDay(String birthDay) { this.birthDay = birthDay; return this; }
        public Builder birthMonth(String birthMonth) { this.birthMonth = birthMonth; return this; }
        public Builder birthYear(String birthYear) { this.birthYear = birthYear; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder company(String company) { this.company = company; return this; }
        public Builder address1(String address1) { this.address1 = address1; return this; }
        public Builder address2(String address2) { this.address2 = address2; return this; }
        public Builder country(String country) { this.country = country; return this; }
        public Builder state(String state) { this.state = state; return this; }
        public Builder city(String city) { this.city = city; return this; }
        public Builder zipcode(String zipcode) { this.zipcode = zipcode; return this; }
        public Builder mobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; return this; }

        public AccountInfo build() {
            return new AccountInfo(this);
        }
    }
}