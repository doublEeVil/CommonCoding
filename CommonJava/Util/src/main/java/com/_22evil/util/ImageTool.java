package com._22evil.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ImageTool {

    /**
     * 图片格式转换
     * @param originFile
     * @param dstFile
     * @param format
     * @throws Exception
     */
    public static void transFormat(String originFile, String dstFile, String format) throws Exception{
        long t1 = System.currentTimeMillis();
        FileImageInputStream fin = new FileImageInputStream(new File(originFile));
        FileImageOutputStream fout = new FileImageOutputStream(new File(dstFile));
        ImageReader reader = null;
        Iterator<ImageReader> it1 = ImageIO.getImageReaders(fin);
        if (it1.hasNext()) reader = it1.next();
        reader.setInput(fin);

        ImageWriter writer = null;
        Iterator<ImageWriter> it2 = ImageIO.getImageWritersByFormatName(format);
        if (it2.hasNext())
            writer = it2.next();
        writer.setOutput(fout);
        BufferedImage br = reader.read(0);
        writer.write(br);
        fin.close();
        fout.close();
        System.out.println("图片转换完成 耗时：" + (System.currentTimeMillis() - t1) + " ms");
    }

    public static void resize(String file) throws Exception{

    }

    public static void waterline(String originFile, String dstFile, String text) throws IOException {

    }

    public static void base64() {

    }

    public static void textImg(String file) throws Exception{

    }

    /**
     * 转为灰度图
     * @param originFile
     * @param dstFile
     * @throws Exception
     */
    public static void gray(String originFile, String dstFile) throws Exception{
        long t1 = System.currentTimeMillis();
        FileImageInputStream fin = new FileImageInputStream(new File(originFile));
        FileImageOutputStream fout = new FileImageOutputStream(new File(dstFile));
        ImageReader reader = null;
        Iterator<ImageReader> it1 = ImageIO.getImageReaders(fin);
        if (it1.hasNext()) reader = it1.next();
        reader.setInput(fin);

        ImageWriter writer = null;
        Iterator<ImageWriter> it2 = ImageIO.getImageWritersByFormatName("png");
        if (it2.hasNext())
            writer = it2.next();
        writer.setOutput(fout);
        BufferedImage br = reader.read(0);
        int width = br.getWidth();
        int height = br.getHeight();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                int[] rgb = getRGB(br.getRGB(i, j));
                //int avg = (rgb[0] + rgb[1] + rgb[2]) / 3;
                int avg = grayValue(rgb[0], rgb[1], rgb[2]);
                br.setRGB(i, j, getRGB(avg, avg, avg));
            }
        writer.write(br);
        fin.close();
        fout.close();
        System.out.println("图片转灰度图完成 耗时：" + (System.currentTimeMillis() - t1) + " ms");
    }

    /**
     * 获得RGB值
     * @param pix
     * @return
     */
    public static int[] getRGB(int pix) {
        int r = (pix >> 16) & 0xff;
        int g = (pix >> 8) & 0xff;
        int b = pix & 0xff;
        return new int[]{r, g, b};
    }


    public static int[] getRGBAlpha(int pix) {
        int r = (pix >> 16) & 0xff;
        int g = (pix >> 8) & 0xff;
        int b = pix & 0xff;
        int alpha = (pix >> 24) & 0xff;
        return new int[]{r, g, b, alpha};
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static int getRGB(int r, int g, int b) {
        return getRGB(r, b, b, 255);
    }

    public static int getRGB(int r, int g, int b, int alpha) {
        int pix = b & 0xff;
        pix += ((g << 8) & 0xff00);
        pix += ((r << 16) & 0xff0000);
        pix += (alpha << 24) & 0xff000000;
        return pix;
    }

    /**
     * 根据rgb值得到灰度值
     * @param r
     * @param g
     * @param b
     * @return
     */
    public static int grayValue(int r, int g, int b) {
        // Gray = (R*30 + G*59 + B*11 + 50) / 100 心理学效果, 诚心而论，我是没看出有啥区别（与 (r + g + b) / 3 相比），视觉上
        // 等同于 Gray = (R*313524 + G*615514 + B*119538) >> 20
        return (r * 313524 + g * 615514 + b * 119538) >> 20;
    }
}
