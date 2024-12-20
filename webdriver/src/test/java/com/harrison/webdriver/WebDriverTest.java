package com.harrison.webdriver;

import com.harrison.webdriver.monitor.page.DashboardPage;
import com.harrison.webdriver.monitor.page.LoginPage;
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
 * @Title WebDriverTest
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/9 17:27
 * @version: 1.0.0
 **/
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebDriverTest {

    @Autowired
    private WebDriver driver;

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private DashboardPage dashboardPage;


    @BeforeAll
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://10.253.164.113:8888/#/login");
    }

    @Test
    public void testLogin() {
        loginPage.login("admin", "Admin!123");
        assertTrue(driver.getCurrentUrl().contains("dept"));
        ScreenshotUtil.shot("login_success_page.png");
    }

    @Test
    public void testDashboard() {
        dashboardPage.open();
        dashboardPage.fullScreen();
        dashboardPage.setRefreshTime("10");
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
