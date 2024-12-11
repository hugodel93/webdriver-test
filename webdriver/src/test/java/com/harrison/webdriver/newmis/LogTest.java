package com.harrison.webdriver.newmis;

import com.harrison.webdriver.newmis.page.NewmisLoginPage;
import com.harrison.webdriver.util.ScreenshotUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

/***
 * @Title LogTest
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/10 14:58
 * @version: 1.0.0
 **/
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogTest {

    @Autowired
    NewmisLoginPage loginPage;

    @Autowired
    private WebDriver driver;

    @BeforeAll
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://ksgmis.kingstartech.com/newmis");
    }

    @Test
    public void testLogin() {
        loginPage.login("5616", "1wsx2qaz@");
        System.out.println(driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().contains("home"));
        ScreenshotUtil.shot("loginsuccess.jpg");
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
