package com.harrison.webdriver;

import com.harrison.webdriver.page.DashboardPage;
import com.harrison.webdriver.page.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/***
 * @Title WebDriverTest
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/9 17:27
 * @version: 1.0.0
 **/
@SpringBootTest
public class WebDriverTest {

    @Autowired
    private WebDriver driver;

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private DashboardPage dashboardPage;

    @BeforeEach
    public void setUp() {
        driver.get("https://10.253.164.113:8888/#/login");
    }

    @Test
    public void testLogin() {
        loginPage.login("admin", "Admin!123");
        assertTrue(driver.getCurrentUrl().contains("dept"));

//        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//        try {
//            FileUtils.copyFile(screenshot, new File("login_success_page.png"));
//            System.out.println("登录成功，截图已保存");
//        } catch (IOException e) {
//            System.out.println("截图保存失败");
//        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
