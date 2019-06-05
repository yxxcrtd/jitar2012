package cn.edustar.jitar.util;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片切割：获取图片中的一部分图片
 * @author 孟宪会
 *
 */
public class CropImage {
	/*
	 * getSubimage(int x, int y, int w, int h) Returns a subimage defined by a
	 * specified rectangular region.
	 */
	public static void cropImage(String sourceDirPath, String targetDirPath,String sourceFileName, String targetFileName, int x,	int y, int w, int h) {
		if (sourceFileName.indexOf(".") == -1) {
			return;
		}
		String imageExtName = sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1);
		String sourcePath = sourceDirPath + "\\" + sourceFileName;
		try {
			File tempFile = new File(targetDirPath + "\\" + targetFileName);
			if(tempFile.exists())
			{
				tempFile.delete();
			}
				OutputStream tmp = null;
			tmp = new FileOutputStream(tempFile);
			Image image = new ImageIcon(sourcePath).getImage();
			BufferedImage outImage = null;
			outImage = toBufferedImage(image);
			BufferedImage img = null;
			img = outImage.getSubimage(x, y, w, h);
			ImageIO.write(img, imageExtName, tempFile);
			tmp.close();
			tempFile = null;
			outImage = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * BufferedImage是Image的一个子类
	 * BufferedImage生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便的操作这个图片，通常用来做图片修改操作如大小变换、图片变灰、设置图片透明或不透明等。
	 * @param image
	 * @return
	 */
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent Pixels
		boolean hasAlpha = hasAlpha(image);

		// boolean hasAlpha = false;

		// Create a buffered image with a format that's compatible with the screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null),image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}

	// This method returns true if the specified image has transparent pixels
	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
	
	/**
	 * 将图片文件剪切
	 * @param srcImageFile		原始图片文件
	 * @param dirImageFile		剪切后保存的图片文件
	 * @param wholeWidth		原始图片文件缩放后的宽度
	 * @param wholeHeight		原始图片文件缩放后的高度
	 * @param x					剪切的开始x坐标
	 * @param y					剪切的开始y坐标
	 * @param destWidth			剪切的宽度 
	 * @param destHeight		剪切的高度
	 */
	public static void cropPhoto(String srcImageFile,String dirImageFile,
			int wholeWidth,int wholeHeight,
			int x, int y, 
			int destWidth,int destHeight){
		//得到缩放后的Image
		BufferedImage tag = scale(srcImageFile,wholeWidth,wholeHeight,0,0);
		//再剪切 ，并保存到 dirImageFile
		abscut(tag,dirImageFile,x,y,destWidth,destHeight);
	}
    /**  
     * 图像切割（改）     *  
     * @param srcImageFile            源图像地址 
     * @param dirImageFile            新图像地址 
     * @param x                       目标切片起点x坐标 
     * @param y                      目标切片起点y坐标 
     * @param destWidth              目标切片宽度 
     * @param destHeight             目标切片高度 
     */  
    public static void abscut(String srcImageFile,String dirImageFile, int x, int y, int destWidth,  
            int destHeight) {  
        try {  
            // 读取源图像  
            BufferedImage bi = ImageIO.read(new File(srcImageFile));  
            abscut(bi,dirImageFile,x,y,destWidth,destHeight);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
    /**
     * 图像切割
     * @param bi  				原始图片BufferedImage
     * @param dirImageFile		保存图片
     * @param x					剪切x
     * @param y					剪切y
     * @param destWidth			剪切宽度
     * @param destHeight		剪切高度
     */
    public static void abscut(BufferedImage bi,String dirImageFile, int x, int y, int destWidth,  
            int destHeight) {  
        try {  
            Image img;  
            ImageFilter cropFilter;  
            // 读取源图像  
            int srcWidth = bi.getWidth(); // 源图宽度  
            int srcHeight = bi.getHeight(); // 源图高度            
            if (srcWidth >= destWidth && srcHeight >= destHeight) {  
                Image image = bi.getScaledInstance(srcWidth, srcHeight,  
                        Image.SCALE_DEFAULT);  
                // 改进的想法:是否可用多线程加快切割速度  
                // 四个参数分别为图像起点坐标和宽高  
                // 即: CropImageFilter(int x,int y,int width,int height)  
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);  
                img = Toolkit.getDefaultToolkit().createImage(  
                        new FilteredImageSource(image.getSource(), cropFilter));  
                BufferedImage tag = new BufferedImage(destWidth, destHeight,  
                        BufferedImage.TYPE_INT_RGB);  
                Graphics g = tag.getGraphics();  
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图  
                g.dispose();  
                // 输出为文件  
                ImageIO.write(tag, "JPEG", new File(dirImageFile));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

	/** 
	* 同比例缩放图像 
	*  
	* @param srcImageFile       源图像文件地址 
	* @param result             缩放后的图像地址 
	* @param scale              缩放比例 
	* @param flag               缩放选择:true 放大; false 缩小; 
	*/  
	public static void scale(String srcImageFile, String result, int scale,  
			boolean flag) {  
		try {  
	           BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件  
	           int width = src.getWidth(); // 得到源图宽  
	           int height = src.getHeight(); // 得到源图长  
	           if (flag) {  
	               // 放大  
	               width = width * scale;  
	               height = height * scale;  
	           } else {  
	               // 缩小  
	                width = width / scale;  
	                height = height / scale;  
	            }  
	            Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);  
	            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
	            Graphics g = tag.getGraphics();  
	            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
	            g.dispose();  
	            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	   }  
	     
    /** 
     * 重新生成按指定宽度和高度的图像 
     * @param srcImageFile       源图像文件地址 
     * @param result             新的图像地址 
     * @param _width             设置新的图像宽度 
     * @param _height            设置新的图像高度 
     */  
    public static void scale(String srcImageFile, String result, int _width,int _height) {        
        scale(srcImageFile,result,_width,_height,0,0);  
    }  
      
   public static void scale(String srcImageFile, String result, int _width,int _height,int x,int y) {  
        BufferedImage tag = scale(srcImageFile,_width,_height,x,y);
        if(tag!=null){
        	try {
				ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }  

   /**
    * 重新生成按指定宽度和高度的图像 
    * @param srcImageFile	 源图像文件地址 
    * @param _width			设置新的图像宽度 
    * @param _height		设置新的图像高度 
    * @param x				开始的x坐标
    * @param y				开始的y坐标
    * @return 剪切后的 BufferedImage
    */
   public static BufferedImage scale(String srcImageFile, int _width,int _height,int x,int y) {  
       try {  
             
           BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件  
             
           int width = src.getWidth(); // 得到源图宽  
           int height = src.getHeight(); // 得到源图长  
           if (width > _width) {  
                width = _width;  
           }  
           if (height > _height) {  
               height = _height;  
           }             
           Image image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);  
           BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
           Graphics g = tag.getGraphics();  
           g.drawImage(image, x, y, null); // 绘制缩小后的图  
           g.dispose();              
           return tag;
       } catch (IOException e) {  
           e.printStackTrace(); 
           return null;
       }  
   }     
	    /** 
	     * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X) 
	     */  
	    public static void convert(String source, String result) {  
	        try {  
	            File f = new File(source);  
	            f.canRead();  
	            f.canWrite();  
	            BufferedImage src = ImageIO.read(f);  
	            ImageIO.write(src, "JPG", new File(result));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	       }  
	   }  
	  
	    /** 
	     * 彩色转为黑白 
	     *  
	     * @param source 
	     * @param result 
	     */  
	    public static void gray(String source, String result) {  
	        try {  
	            BufferedImage src = ImageIO.read(new File(source));  
	            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
	           ColorConvertOp op = new ColorConvertOp(cs, null);  
	            src = op.filter(src, null);  
	            ImageIO.write(src, "JPEG", new File(result));  
	       } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  

}
