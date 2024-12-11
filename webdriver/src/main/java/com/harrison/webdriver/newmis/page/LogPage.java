package com.harrison.webdriver.newmis.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * @Title LogPage
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/11 16:39
 * @version: 1.0.0
 **/
@Component
public class LogPage {

    @Autowired
    WebDriver driver;

    private final By logButton = By.cssSelector("div[title='日志填写']");


    public void open(){

    }
}
