package com.harrison.webdriver.monitor.page;

import com.harrison.webdriver.util.CommonUtil;
import com.harrison.webdriver.util.ScreenshotUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/***
 * @Title DashboardPage
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/9 17:18
 * @version: 1.0.0
 **/
@Component
public class DashboardPage {

    private  WebDriverWait wait;

    @Autowired
    private WebDriver driver;

    @PostConstruct
    public void init() {
        wait = new WebDriverWait(driver, 3);
    }

    public void open() {
        driver.findElement(By.xpath("//*[@id=\"tab-20231213000000\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[3]/div[2]/div[1]/div[1]/div/div/div[1]/div/div[1]/div")).click();
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[3]/div[2]/div[1]/div[1]/div/div/div[1]/div/div[2]/div[2]/div/div/span/span")).click();
        CommonUtil.threadSleep(5000);
        ScreenshotUtil.shot("components_monitor.png");
    }

    public void fullScreen(){
        //全屏按钮可以点击
        WebElement fullScreenBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"htbusiness_id\"]/div[1]/div[3]/i")));
        fullScreenBtn.click();
        ScreenshotUtil.shot("components_monitor_full_screen.png");
        //取消全屏
        fullScreenBtn.click();
    }

    public void setRefreshTime(String time){
        //刷新频率弹窗
        WebElement dialogBtn = driver.findElement(By.xpath("//*[@id=\"htbusiness_id\"]/div[1]/div[3]/img"));
        dialogBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog' and @aria-label='设置']")));
        ScreenshotUtil.shot("components_monitor_refresh_1.png");
        //设置时间 5秒
        driver.findElement(By.xpath("//*[@id=\"htbusiness_id\"]/div[5]/div/div[2]/div/div/input")).sendKeys(time);
        //保存
        WebElement saveBtn = driver.findElement(By.xpath("//*[@id=\"htbusiness_id\"]/div[5]/div/div[3]/span/button[1]"));
        saveBtn.click();
        //重新打开截图
        dialogBtn.click();
        ScreenshotUtil.shot("components_monitor_refresh_2.png");
        WebElement cancleBtn = driver.findElement(By.xpath("//*[@id=\"htbusiness_id\"]/div[5]/div/div[3]/span/button[2]"));
        cancleBtn.click();
    }
}
