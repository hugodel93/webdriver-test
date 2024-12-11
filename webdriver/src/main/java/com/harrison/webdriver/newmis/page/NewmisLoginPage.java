package com.harrison.webdriver.newmis.page;

import com.harrison.webdriver.util.CaptchaUtil;
import com.harrison.webdriver.util.CommonUtil;
import com.harrison.webdriver.util.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/***
 * @Title LoginPage
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/9 17:18
 * @version: 1.0.0
 **/
@Component
public class NewmisLoginPage {

    private int retryTimes = 0;

    @Autowired
    private WebDriver driver;

    private final By usernameField = By.id("userName");
    private final By passwordField = By.id("password");
    private final By captchaField = By.id("code");
    private final By loginButton = By.xpath("//*[@id=\"login-body\"]/div[1]/div[2]/div[3]");

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        captchaCheck();
    }

    private void captchaCheck(){
        retryTimes ++;
        try {
            WebElement captchaEle = driver.findElement(By.xpath("//img[@alt='验证码']"));
            Point location = captchaEle.getLocation();  // 获取验证码图片的位置
            Dimension size = captchaEle.getSize();     // 获取验证码图片的尺寸
            // 获取整个页面的截图
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // 将截图转换为 BufferedImage
            BufferedImage fullScreenshot = ImageIO.read(screenshotFile);
            // 获取验证码的位置和尺寸
            int x = location.getX();
            int y = location.getY();
            int width = size.getWidth();
            int height = size.getHeight();
            // 裁剪验证码区域
            BufferedImage captchaImage = fullScreenshot.getSubimage(x, y, width, height);
            String captchaStr = CaptchaUtil.doRecognize(captchaImage, "", "100").toLowerCase().substring(0,5);
            driver.findElement(captchaField).sendKeys(captchaStr);
            ScreenshotUtil.shot("login_success_page_" + retryTimes + ".png");
            //点击登录
            driver.findElement(loginButton).click();
            WebDriverWait wait = new WebDriverWait(driver, 2);
            try {
                // 等待提示框出现并可见
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//p[@class='el-message__content' and contains(text(), '验证码无效')]")));
                if ( retryTimes < 5) {
                    System.out.println("错误提示：验证码无效 重试");
                    CommonUtil.threadSleep(3000);
                    captchaCheck();
                }else {
                    System.out.println("重试结束！");
                }
            } catch (Exception ignore) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
