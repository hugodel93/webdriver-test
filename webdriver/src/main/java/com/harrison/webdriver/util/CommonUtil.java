package com.harrison.webdriver.util;

/***
 * @Title CommonUtil
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/10 14:30
 * @version: 1.0.0
 **/
public class CommonUtil {

    public static void threadSleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch (Exception ignore){}
    }
}
