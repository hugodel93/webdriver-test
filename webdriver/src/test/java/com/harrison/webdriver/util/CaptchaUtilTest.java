package com.harrison.webdriver.util;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/***
 * @Title CaptchaUtilTest
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/11 17:08
 * @version: 1.0.0
 **/
public class CaptchaUtilTest {

    @Test
    public void testGenerateCaptcha() throws IOException {
        for (int i = 0; i < 10; i++) {
            URL imageUrl = new URL("https://ksgmis.kingstartech.com/newmis/m?xwl=sys/session/get-verify-image");
            // 下载图片并保存
            BufferedImage captchaBufferedImage = ImageIO.read(imageUrl);
            CaptchaUtil.doRecognize(captchaBufferedImage,"","96");
            CommonUtil.threadSleep(3000);
        }

    }
}
