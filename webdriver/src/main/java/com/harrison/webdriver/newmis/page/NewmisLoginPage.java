package com.harrison.webdriver.newmis.page;

import com.harrison.webdriver.util.CaptchaUtil;
import com.harrison.webdriver.util.CommonUtil;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.By;
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
import java.net.MalformedURLException;
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

    @Autowired
    private WebDriver driver;

    private final By usernameField = By.id("userName");
    private final By passwordField = By.id("password");
    private final By captchaField = By.id("code");
    private final By loginButton = By.xpath("//*[@id=\"login-body\"]/div[1]/div[2]/div[3]");

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        WebElement captchaImage = driver.findElement(By.xpath("//img[@alt='验证码']"));
        String captchaSrc = captchaImage.getAttribute("src");
        try {
            // 将相对路径转换为完整的 URL
            URL imageUrl = new URL(captchaSrc);
            // 下载图片并保存
            BufferedImage captchaBufferedImage = ImageIO.read(imageUrl);
            String captchaStr = CaptchaUtil.doRecognize(captchaBufferedImage,"","jpg","70");
            System.out.println("-->" + captchaStr);
            driver.findElement(captchaField).sendKeys(captchaStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(loginButton).click();
        CommonUtil.threadSleep(5000);
    }
}
