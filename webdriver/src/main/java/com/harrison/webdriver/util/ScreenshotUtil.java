package com.harrison.webdriver.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

/***
 * @Title ScreenshotUtil
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/10 11:06
 * @version: 1.0.0
 **/
public class ScreenshotUtil {

    public static void shot(String fileName){
        WebDriver driver = SpringUtil.getBean("webDriver",WebDriver.class);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("C:\\Users\\Harrison\\Desktop\\test\\" + fileName));
            System.out.println("登录成功，截图已保存");
        } catch (IOException e) {
            System.out.println("截图保存失败");
        }
    }
}
