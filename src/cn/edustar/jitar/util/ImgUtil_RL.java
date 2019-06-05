package cn.edustar.jitar.util;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;


public class ImgUtil_RL {

    /**
     * 实现图像的等比缩放
     * 
     * @param source
     * @param targetW
     * @param targetH
     * @return
     */
    public static HttpServletResponse res = null;
    private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx < sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static void setRes(HttpServletResponse res) {
        ImgUtil_RL.res = res;
    }

    /**
     * 实现图像的等比缩放和缩放后的截取
     * 
     * @param inFilePath
     *            要截取文件的路径
     * @param outFilePath
     *            截取后输出的路径
     * @param width
     *            要截取宽度
     * @param hight
     *            要截取的高度
     * @param proportion
     * @throws Exception
     */

    public static void saveImageAsJpg(String inFilePath, String outFilePath, int width, int hight) throws Exception{
//      File file = new File(inFilePath);
        URL url = new URL(inFilePath);
        URLConnection con =  url.openConnection();
//      InputStream in = new FileInputStream(file);
        InputStream in =null;
        BufferedImage srcImage  = null;
        try{
             in = con.getInputStream();
             srcImage =ImageIO.read(in);
        }catch(Exception e){
            return; 
        }
        File saveFile = new File(outFilePath);
        
        if (width > 0 || hight > 0) {
            // 原图的大小
            int sw = srcImage.getWidth();
            int sh = srcImage.getHeight();
            // 如果原图像的大小小于要缩放的图像大小，直接将要缩放的图像复制过去
            // 加=号可以实现与原图相同width或者height的缩放
            if (sw >= width && sh >= hight) {
                srcImage = resize(srcImage, width, hight);
            } else {
                String fileName = saveFile.getName();
                String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
                ImageIO.write(srcImage, formatName, saveFile);
                return;
            }
        }
        // 缩放后的图像的宽和高
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();
        // 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取
        if (w == width) {
            // 计算X轴坐标
            int x = 0;
            int y = h / 2 - hight / 2;
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
        }
        // 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取
        else if (h == hight) {
            // 计算X轴坐标
            int x = w / 2 - width / 2;
            int y = 0;
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
        }
        in.close();
    }

    /**
     * 实现缩放后的截图
     * 
     * @param image
     *            缩放后的图像
     * @param subImageBounds
     *            要截取的子图的范围
     * @param subImageFile
     *            要保存的文件
     * @throws IOException
     */
    private static void saveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile) throws IOException {
        if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth()
                || subImageBounds.height - subImageBounds.y > image.getHeight()) {
            System.out.println("Bad subimage bounds");
            return;
        }
        BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width, subImageBounds.height);
        String fileName = subImageFile.getName();
        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
        OutputStream out = res.getOutputStream();
        ImageIO.write(subImage, null==formatName||formatName.trim().equals("")?"jpg":formatName,out);
        out.flush();
        out.close();
    }
}
