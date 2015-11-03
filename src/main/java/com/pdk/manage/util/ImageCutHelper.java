package com.pdk.manage.util;

import com.pdk.manage.exception.BusinessException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by liuhaiming on 2015/9/5.
 */
public class ImageCutHelper {
    /**
     * 图片切割
     * @param in  原图地址
     * @param x  目标切片坐标 X轴起点
     * @param y     目标切片坐标 Y轴起点
     * @param w  目标切片 宽度
     * @param h  目标切片 高度
     */
    public void cutImage(InputStream in, int x ,int y ,int w,int h, File cutFile) throws BusinessException{
        try {
            cutImage(in, cutFile, x, y, w, h);
//            Image img;
//            ImageFilter cropFilter;
//            // 读取源图像
//            BufferedImage bi = ImageIO.read(in);
//            int srcWidth = bi.getWidth();      // 源图宽度
//            int srcHeight = bi.getHeight();    // 源图高度
//
//            //若原图大小大于切片大小，则进行切割
//            Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
//            cropFilter = new CropImageFilter(x, y, w, h);
//            img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
//            BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//            Graphics g = tag.getGraphics();
//            g.drawImage(img, 0, 0, null); // 绘制缩小后的图
//            g.dispose();
//            // 输出为文件
//            ImageIO.write(tag, "png", cutFile);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public static void cutImage(InputStream in,File cutFile,int x,int y,int w,int h) throws IOException{
        String type = cutFile.getName().substring(cutFile.getName().indexOf(".") + 1);
        Iterator iterator = ImageIO.getImageReadersBySuffix(type);
        ImageReader reader = (ImageReader)iterator.next();

        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w,h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0,param);
        ImageIO.write(bi, type, cutFile);
    }

}
