package com.harrison.webdriver.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * @Title LoginPage
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/9 17:18
 * @version: 1.0.0
 **/
@Component
public class LoginPage {

    @Autowired
    private WebDriver driver;

    private By usernameField = By.xpath("//input[@placeholder='请输入登录账号']");
    private By passwordField = By.xpath("//input[@placeholder='请输入密码']");
    private By loginButton = By.id("loginBtn");

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}
