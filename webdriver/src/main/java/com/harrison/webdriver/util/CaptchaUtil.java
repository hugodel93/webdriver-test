package com.harrison.webdriver.util;

import cn.hutool.core.util.StrUtil;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/***
 * @Title CaptchaUtil
 * @Description:
 * @author: hang.hu
 * @date: 2024/12/10 15:08
 * @version: 1.0.0
 **/
public class CaptchaUtil {

    public static void main(String[] args) throws Exception {
        File file = new File("C:\\Users\\Harrison\\Desktop\\test\\m.jpg");
        System.out.println("-->" + doRecognize(file, "", "jpg", "30"));
    }


    /**
     * file: 图片文件
     * rectangle: 图片中需要识别的区域。如new Rectangle(x, y, w, h), 坐标及宽高
     * dataPath: tessData的路径（文章开头已添加到环境变量，也可传入）
     * formateName: file文件的格式。如 JPG, PNG, DICOM等,
     * dpi: tesseractOcr识别时传入的参数。如：tesseract.setVariable("user_defined_dpi", dpi);
     */
    public static String doRecognize(File file, String dataPath, String formateName, String dpi) {
        if (!file.exists()) {
            System.err.println("待识别文件不存在");
            return "";
        }
        try {
            BufferedImage img = readFile(file, formateName);
            if (img == null) {
                System.out.println("读取图像异常: img == null");
                return "";
            }
            return doOcrImpl(img, dataPath, true, dpi, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String doRecognize(BufferedImage img, String dataPath, String dpi) {
        try {
            return doOcrImpl(img, dataPath, true, dpi, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @Description: 把文件转为BufferedImage对象，并截取指定区域
     **/
    private static BufferedImage readFile(File file, String formateName) throws IOException {
        ImageReader reader = null;
        try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
            // 创建 ImageInputStream 对象
            // 获取 指定 文件的 ImageReader 实例
            reader = ImageIO.getImageReadersByFormatName(formateName).next();
            // 设置解码器
            reader.setInput(iis);
            // 如果需要截取图像，获取完整的BufferedImage，然后截取指定区域
            BufferedImage image = reader.read(0);
            // 释放完整的BufferedImage
            image.flush();
            return image;
        } finally {
            // 关闭资源
            if (reader != null) {
                reader.dispose();
            }
        }
    }


    /**
     * ocr识别
     *
     * @param img         dataPath: tessData的路径（文章开头已添加到环境变量，也可传入）
     * @param replacedEmp 是否替换回车和空格为空, true:替换, false, 不替换（含回车和空格符）
     * @param dpi         分辨率, 默认 96
     * @param charNoLimit 识别空格: true, 不识别空格: false
     * @return
     * @throws TesseractException
     */
    private static String doOcrImpl(BufferedImage img, String dataPath, boolean replacedEmp, String dpi, boolean charNoLimit) throws TesseractException {
        //简单的灰度化和二值化
        BufferedImage subImage = preprocessImage(img);
        //处理黑边 subImage = removeBlackBorder(subImage);
        int bold = 2;
        Rectangle region = new Rectangle(bold, bold, img.getWidth() - 2*bold, img.getHeight() - 2*bold);
        subImage = subImage.getSubimage(region.x, region.y, region.width, region.height);

        // 初始化 OCR 引擎
        Tesseract tesseract = new Tesseract();
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        tesseract.setDatapath(tessDataFolder.getAbsolutePath());
        //语言包位置  根据实际环境修改替换
        if (StrUtil.isNotBlank(dataPath)) {
            tesseract.setDatapath(dataPath);
        }
        //配置使用的语言  中文
        tesseract.setLanguage("eng");
        if (!charNoLimit) {
            //限制只识别数字字母
            tesseract.setVariable("tessedit_char_whitelist", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        }
        //设置分辨率
        tesseract.setVariable("user_defined_dpi", dpi);
        String result = tesseract.doOCR(subImage);
        if (replacedEmp) {
            // 文字识别-过滤空白、换行符
            result = result.replace(StrUtil.SPACE, StrUtil.EMPTY).replace(StrUtil.LF, StrUtil.EMPTY);
        }
        // 测试用
        File outputFile = new File("C:\\Users\\Harrison\\Desktop\\test\\rst_" + result + ".jpg");
        // 保存为 PNG 格式的文件
        try {
            ImageIO.write(subImage, "JPG", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static BufferedImage preprocessImage(BufferedImage image) {
        // 转换为灰度图像
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                int gray = (int) (c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114);
                grayImage.setRGB(i, j, new Color(gray, gray, gray).getRGB());
            }
        }

        // 二值化处理
        BufferedImage binaryImage = new BufferedImage(grayImage.getWidth(), grayImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < grayImage.getWidth(); i++) {
            for (int j = 0; j < grayImage.getHeight(); j++) {
                Color c = new Color(grayImage.getRGB(i, j));
                int binaryColor = (c.getRed() > 128) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                binaryImage.setRGB(i, j, binaryColor);
            }
        }
        return binaryImage;
    }

    // 去除黑边的方法
    public static BufferedImage removeBlackBorder(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // 计算边界，找到非黑色区域
        int left = 0, top = 0, right = width - 1, bottom = height - 1;

        // 查找左边界
        outerLeft:
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (image.getRGB(x, y) != Color.BLACK.getRGB()) {
                    left = x;
                    break outerLeft;
                }
            }
        }

        // 查找右边界
        outerRight:
        for (int x = width - 1; x >= 0; x--) {
            for (int y = 0; y < height; y++) {
                if (image.getRGB(x, y) != Color.BLACK.getRGB()) {
                    right = x;
                    break outerRight;
                }
            }
        }

        // 查找上边界
        outerTop:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (image.getRGB(x, y) != Color.BLACK.getRGB()) {
                    top = y;
                    break outerTop;
                }
            }
        }

        // 查找下边界
        outerBottom:
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                if (image.getRGB(x, y) != Color.BLACK.getRGB()) {
                    bottom = y;
                    break outerBottom;
                }
            }
        }

        // 裁剪图像，去掉黑边
        return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
    }
}
