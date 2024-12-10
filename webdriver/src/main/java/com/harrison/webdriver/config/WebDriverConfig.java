package com.harrison.webdriver.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/***
 * @Title WebDriverConfig
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/9 17:21
 * @version: 1.0.0
 **/
@Configuration
public class WebDriverConfig {
    @Value("${webdriver.driver}")
    private String driverType;

    @Value("${webdriver.chrome.driver-path}")
    private String chromeDriverPath;

    @Value("${webdriver.gecko.driver-path}")
    private String geckoDriverPath;

    @Bean
    @Scope("singleton") //所有测试案例共同使用一个 WebDriver 实例
//    @Scope("prototype") // 每个测试用例使用一个独立的 WebDriver 实例
    public WebDriver webDriver() {
        if ("chrome".equalsIgnoreCase(driverType)) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless"); // 无头模式，不显示浏览器窗口
//            options.addArguments("--disable-gpu"); // 禁用GPU加速（在无头模式下常用）
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--disable-web-security"); // 禁用网页安全检查（适用于一些开发场景）
            return new ChromeDriver(options);
        } else if ("firefox".equalsIgnoreCase(driverType)) {
            System.setProperty("webdriver.gecko.driver", geckoDriverPath);
            return new FirefoxDriver();
        } else if ("edge".equalsIgnoreCase(driverType)) {
            System.setProperty("webdriver.edge.driver", geckoDriverPath);
            return new EdgeDriver();
        }
        throw new IllegalArgumentException("Unsupported driver type: " + driverType);
    }
}
