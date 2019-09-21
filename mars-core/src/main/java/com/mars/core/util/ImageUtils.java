package com.mars.core.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by lixl on 2017/2/17.
 */
public class ImageUtils {

    private static LoggerUtils logger = new LoggerUtils(ImageUtils.class);

    /**
     * 生成缩略图
     * @param fromFileStr
     * @param saveToFileStr
     * @param width
     * @param hight
     * @param imgBytes
     * @return
     * @throws Exception
     */
    public static boolean saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight, byte[] imgBytes) throws Exception {
        boolean result = false;
        BufferedImage srcImage = null;
        String imgType = "JPEG";
        if (fromFileStr.toLowerCase().endsWith(".png")) {
            imgType = "PNG";
        }
        File saveFile = new File(saveToFileStr);
        try {
            InputStream stream = new ByteArrayInputStream(imgBytes);
            srcImage = ImageIO.read(stream);
            if (width > 0 || hight > 0) {
                srcImage = resize(srcImage, width, hight);
            }
            ImageIO.write(srcImage, imgType, saveFile);
            result = true;
        } catch (Exception e) {
            logger.error(fromFileStr + " 的源文件不存在，不能生成缩略图！", e);
        }
        return result;
    }

    public static boolean saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight)
            throws Exception {
        boolean result = false;
        BufferedImage srcImage;
        String imgType = "JPEG";
        if (fromFileStr.toLowerCase().endsWith(".png")) {
            imgType = "PNG";
        }
        File saveFile = new File(saveToFileStr);

        try {
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            srcImage = ImageIO.read(new File(fromFileStr));
            if (width > 0 || hight > 0) {
                srcImage = resize(srcImage, width, hight);

            }
            ImageIO.write(srcImage, imgType, saveFile);
            result = true;
        } catch (Exception e) {
            logger.error(fromFileStr + " 的源文件不存在，不能生成缩略图！", e);
        }

        return result;
    }

    /**
     * 等比缩放
     * @param source
     * @param targetW 目标宽度
     * @param targetH 目标高度
     * @return
     */
    public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        return resize(source, targetW, targetH, sx, sy);
    }

    /**
     * 按传参的固定大小缩放(图片有可能出现拉伸变形现象)
     * @param source
     * @param targetW 目标宽度
     * @param targetH 目标高度
     * @return
     */
    public static BufferedImage resizeFixed(BufferedImage source, int targetW, int targetH) {
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        return resize(source, targetW, targetH, sx, sy);
    }

    private static BufferedImage resize(BufferedImage source, int targetW, int targetH, double sx, double sy) {
        int type = source.getType();
        BufferedImage target;
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }


    /**
     * 根据图片地址保存图片，最大不能超过4M
     * @param picUrl 网络资源地址
     * @param savePath 保存路径
     * @return
     */
    public static boolean saveImageByUrl(String picUrl, String savePath) {
        try {
			/*将网络资源地址传给,即赋值给url*/
            URL url = new URL(picUrl);
			/*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
			/*此处也可用BufferedInputStream与BufferedOutputStream*/
            DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
			/*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/
            byte[] buffer = new byte[4096];
            int count = 0;
			/*将输入流以字节的形式读取并写入buffer中*/
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
			/*后面三行为关闭输入输出流以及网络资源的固定格式*/
            out.close();
            in.close();
            connection.disconnect();
			/*网络资源截取并存储本地成功返回true*/
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据图片数据流保存图片，最大不能超过4M
     * @param buffer 图片byte[]数据
     * @param savePath 保存路径
     * @return
     */
    public static boolean saveImageByByte(byte[] buffer, String savePath) {
        try {
            FileImageOutputStream imgout = new FileImageOutputStream(new File(savePath));
            imgout.write(buffer, 0, buffer.length);
            imgout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将图片转换成byte[]，最大不能超过4M
     * @return
     */
    public static byte[] imageToBytes(String imagePath) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(imagePath));
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);

            byte[] temp = new byte[4096];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            in.close();
            byte[] content = out.toByteArray();

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 图片压缩方法
     * @param srcfile 目标文件
     * @param destPath 转换后的文件
     * @param contentType 文件类型
     * @param width 宽度
     * @param heigth 高度
     */
    public static void reduceImage(File srcfile, String destPath, String contentType, int width, int heigth) {
        try {
            if (!srcfile.exists()) {
                return;
            }
            Image src = javax.imageio.ImageIO.read(srcfile);
            int srcWidth = src.getWidth(null);
            int srcHeigh = src.getHeight(null);
            if (srcWidth > width) {
                srcWidth = width;
            }
            if (srcHeigh > heigth) {
                srcHeigh = heigth;
            }

            BufferedImage tag = new BufferedImage(srcWidth, srcHeigh, BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D g2d = tag.createGraphics();

            g2d.setStroke(new BasicStroke(1));
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g2d.drawImage( src.getScaledInstance(srcWidth, srcHeigh, Image.SCALE_SMOOTH), 0, 0, null);
            //释放对象
            g2d.dispose();

            ImageIO.write(tag, "jpg", new File(destPath));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
    public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static Random random = new Random();

    //手机验证码 纯数字
    public static final String PHONE_VERIFY_CODES = "0123456789";

    /**
     * 使用系统默认字符源生成验证码
     *
     * @param verifySize 验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    /**
     * 获取手机验证码
     *
     * @param verifySize
     * @return
     */
    public static String generatePhoneVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, PHONE_VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param verifySize 验证码长度
     * @param sources    验证码字符源
     * @return
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 生成随机验证码文件,并返回验证码值
     *
     * @param w
     * @param h
     * @param outputFile
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, outputFile, verifyCode);
        return verifyCode;
    }

    /**
     * 输出随机验证码图片流,并返回验证码值
     *
     * @param w
     * @param h
     * @param os
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, os, verifyCode);
        return verifyCode;
    }

    /**
     * 生成指定验证码图像文件
     *
     * @param w
     * @param h
     * @param outputFile
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
        if (outputFile == null) {
            return;
        }
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            outputImage(w, h, fos, code);
            fos.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w
     * @param h
     * @param os
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN,
                Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w, h - 4);

        //绘制干扰线
//        Random random = new Random();
//        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
//        for (int i = 0; i < 20; i++) {
//            int x = random.nextInt(w - 1);
//            int y = random.nextInt(h - 1);
//            int xl = random.nextInt(6) + 1;
//            int yl = random.nextInt(12) + 1;
//            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
//        }

        // 添加噪点
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 3);
        }

        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }

    }

    public static void main(String[] args) throws IOException {
        File dir = new File("F:/verifies");
        int w = 200, h = 80;
        for (int i = 0; i < 50; i++) {
            String verifyCode = generateVerifyCode(4);
            File file = new File(dir, verifyCode + ".jpg");
            outputImage(w, h, file, verifyCode);
        }
    }
}


    class AuthImage extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    static final long serialVersionUID = 1L;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = ImageUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        session.setAttribute("rand", verifyCode.toLowerCase());
        //生成图片
        int w = 200, h = 80;
        ImageUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

    }

}
