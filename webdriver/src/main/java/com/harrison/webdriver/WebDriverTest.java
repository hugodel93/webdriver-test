package com.harrison.webdriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

/***
 * @Title WebDriverTest
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/9 16:08
 * @version: 1.0.0
 **/
public class WebDriverTest {

    public static void main(String[] args) {
        // 设置 ChromeDriver 的路径（确保已经下载了 ChromeDriver）
        // https://sites.google.com/chromium.org/driver/downloads
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Harrison\\Downloads\\chromedriver-win64\\chromedriver.exe");

        // 设置 ChromeOptions（可选）
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // 无头模式，不显示浏览器窗口
//        options.addArguments("--disable-gpu"); // 禁用GPU加速（在无头模式下常用）
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-web-security"); // 禁用网页安全检查（适用于一些开发场景）

        // 创建 ChromeDriver 实例
        WebDriver driver = new ChromeDriver(options);

        try {
            // 打开网页
            driver.get("https://10.253.164.113:8888/#/login");

//            System.out.println(driver.getPageSource());

            // 找到用户名输入框并输入账号
            WebDriverWait wait = new WebDriverWait(driver, 3); // 最多等待10秒
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='请输入登录账号']")));
            usernameField.sendKeys("admin"); // 替换为实际的用户名

            // 找到密码输入框并输入密码
            WebElement passwordField = driver.findElement(By.xpath("//input[@placeholder='请输入密码']"));
            passwordField.sendKeys("Admin!123"); // 替换为实际的密码

            // 找到登录按钮并点击
            WebElement loginButton = driver.findElement(By.id("loginBtn"));

            // 检查按钮是否启用（如果按钮被禁用则无法点击）
            if (!loginButton.isEnabled()) {
                System.out.println("登录按钮被禁用，无法点击");
            } else {
                loginButton.click(); // 点击登录按钮
                System.out.println("点击登录按钮");
            }

            // 可以在这里等待一段时间，或者验证登录是否成功
            // WebDriverWait 或者其他等待机制可以根据实际情况使用
            // 使用 WebDriverWait 等待页面加载，确保某个元素出现，最大等待10秒
            WebDriverWait wait2 = new WebDriverWait(driver, 10);
            WebElement userToolsIcon = wait2.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.user-tools-icon.pointer.ks-icon-status-out")
            ));
            System.out.println(driver.getCurrentUrl());

            // 判断是否成功加载该元素来确定登录是否成功
            if (userToolsIcon != null && userToolsIcon.isDisplayed()) {
                System.out.println("登录成功！");
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(screenshot, new File("login_success_page.png"));
                    System.out.println("登录成功，截图已保存");
                } catch (IOException e) {
                    System.out.println("截图保存失败");
                }
            } else {
                System.out.println("登录失败！");
            }
        } finally {
            // 关闭浏览器
            driver.quit();
        }
    }
}
