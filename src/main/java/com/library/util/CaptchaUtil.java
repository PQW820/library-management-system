package com.library.util;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptchaUtil {

    public static final String CAPTCHA_SESSION_KEY = "captcha_code";

    private static final int WIDTH = 140;
    private static final int HEIGHT = 50;
    private static final int CODE_LENGTH = 4;
    private static final int LINE_COUNT = 8;
    private static final int DOT_COUNT = 20;

    private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * 生成验证码图片
     */
    public static void generateCaptcha(HttpSession session, HttpServletResponse response) throws Exception {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 设置抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置背景色（浅灰色背景，更柔和）
        g.setColor(new Color(245, 245, 245));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 设置字体（更大更粗）
        g.setFont(new Font("Arial", Font.BOLD, 36));

        // 生成验证码
        String captcha = generateCode();

        // 将验证码存入session
        session.setAttribute(CAPTCHA_SESSION_KEY, captcha);

        // 绘制验证码
        Random random = new Random();
        int[] xPositions = {15, 50, 85, 120};
        for (int i = 0; i < CODE_LENGTH; i++) {
            char c = captcha.charAt(i);
            
            // 使用深色且清晰的颜色
            Color textColor = getRandomDarkColor(random);
            g.setColor(textColor);
            
            // 添加文字阴影，增加辨识度
            g.setColor(new Color(200, 200, 200));
            g.drawString(String.valueOf(c), xPositions[i] + 2, 38);
            
            // 绘制主文字
            g.setColor(textColor);
            
            // 添加轻微倾斜
            double angle = (random.nextDouble() - 0.5) * 0.3;
            g.rotate(angle, xPositions[i], 30);
            g.drawString(String.valueOf(c), xPositions[i], 36);
            g.rotate(-angle, xPositions[i], 30);
        }

        // 绘制干扰线（大幅减少数量，使用浅色）
        for (int i = 0; i < LINE_COUNT; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.setColor(new Color(200, 200, 200));
            g.setStroke(new BasicStroke(1));
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制干扰点（减少数量）
        for (int i = 0; i < DOT_COUNT; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            g.setColor(new Color(180, 180, 180));
            g.drawOval(x, y, 2, 2);
        }

        // 设置响应头
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 输出图片
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "jpeg", os);
        os.flush();
        os.close();
    }

    /**
     * 生成深色随机颜色
     */
    private static Color getRandomDarkColor(Random random) {
        // 使用深蓝色、深绿色、深红色等深色系
        Color[] colors = {
            new Color(0, 51, 153),   // 深蓝色
            new Color(0, 102, 0),     // 深绿色
            new Color(153, 0, 0),     // 深红色
            new Color(102, 0, 153),   // 深紫色
            new Color(153, 102, 0),   // 深橙色
            new Color(0, 102, 102)    // 深青色
        };
        return colors[random.nextInt(colors.length)];
    }

    /**
     * 生成验证码字符串
     */
    private static String generateCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }
}
