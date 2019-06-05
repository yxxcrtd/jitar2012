package cn.edustar.jitar.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;

public class PicProcessUtil {
	private static final String DEST_FILE_PATH = "images\\headImg";
	public  static final String IMAGE_ADD_FLAG = "1";
	public  static final String IMAGE_UPDATE_FLAG = "2";
	
	/**
	 * @desc 根据原图片剪裁并生成自定义大小的图片
	 * @param imageName 图片名称+格式
	 * @param width 希望截取后的图片的宽度
	 * @param height 希望截取后的图片的高度
	 * @throws IOException
	 * @date 2013-7-25
	 */
	public static String cutImage(byte[] imageData, int width, int height, String imageName, String operFlag) throws IOException {
		if(imageData == null || width<=0 || height<=0)
			return null;
		BufferedImage image = getDecompressedImage(imageData);
		if(image==null)
			return null;
		
	    int srcWidth = image.getWidth(null);      
	    int srcHeight = image.getHeight(null);      
	    int newWidth = 0, newHeight = 0;      
	    int x = 0, y = 0;      
	    double scale_w = (double)width/srcWidth;      
	    double scale_h = (double)height/srcHeight;      
	    //System.out.println("scale_w="+scale_w+",scale_h="+scale_h);
	    
	    //按原比例缩放图片
	    if(scale_w < scale_h) {
	        newHeight = height;      
	        newWidth = (int)(srcWidth * scale_h);      
	        x = (newWidth - width)/2;      
	    } else {
	        newHeight = (int)(srcHeight * scale_w);
	        newWidth = width;
	        y = (newHeight - height)/2;
	    }
	    
	    BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);      
	    newImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);      
	    
	    //保存缩放后的图片
//	    File destFile = new File(srcFile.getParent(), UUID.randomUUID().toString() + "." + fileSufix);
	    
	    String childPath = imageName;
	    if(IMAGE_ADD_FLAG.equals(operFlag))
	    	childPath = getNewImageName(imageName);
        String fileSufix = imageName.substring(imageName.lastIndexOf(".") + 1);
        if(fileSufix.length()==0){fileSufix="jpg";}
	    File destFile = new File(getUploadPath(), childPath);
	    //保存裁剪后的图片
	    ImageIO.write(newImage.getSubimage(x, y, width, height), fileSufix, destFile);	//ImageIO.write(newImage, fileSufix, destFile);
	    return childPath;
	}
	
	/**
	 * 图像编码,因为图像编码解码主要目的是针对图像在网络中的传输，所以编码之后的图像不必保存在硬盘上，可以直接放入一个字节数组。
	 * @param image
	 * @date 20130807
	 * @author lixiaoguang
	 * @return
	 */
	public byte[] getCompressedImage(BufferedImage image){
		byte[] imageData = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			imageData = baos.toByteArray();
		} catch (IOException ex) {
			imageData = null;
		}
		return imageData;
	}

	/**
	 * 图像解码
	 * 接收端接收到表示图像数据的字节数组后，对其进行解码，得到图像对象。因为我们在发送端将其编码成JPEG格式，所以可以直接在接收端使用ImageIO对其进行解码。
	 * @param image
	 * @date 20130807
	 * @author lixiaoguang
	 */
	public static BufferedImage getDecompressedImage(byte[] imageData){
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
			return ImageIO.read(bais);
		} catch (IOException ex) {
			return null;
		}
	}
	
	public BufferedImage getPathIMage(String srcPath){
		if(StringUtils.isBlank(srcPath))
			return null;
		File srcFile = new File(srcPath);      
		BufferedImage image = null;
	    try {
			image = ImageIO.read(srcFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return image;
	}
	
    /** 
     * 图片BASE64 编码 
     */  
    public static String getPicBASE64(String picPath) {
        String content = null;
        try {
            FileInputStream fis = new FileInputStream(picPath);  
            byte[] bytes = new byte[fis.available()];  
            fis.read(bytes);  
            content = new sun.misc.BASE64Encoder().encode(bytes);
            fis.close();  
          //System.out.println(content.length());
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return content;  
    }  
  
    /** 
     * 对图片BASE64解码
     */  
    public static String getPicFormatBASE64(String base64Str, String picName) {
    	String childPath = null;
        try {
            byte[] baseByteArr = new sun.misc.BASE64Decoder().decodeBuffer(base64Str.trim()); 
            childPath = getNewImageName(picName);
            String fileSufix = picName.substring(picName.lastIndexOf(".") + 1) ;
            
            String uploadPath=getUploadPath();
            File newFile = new File(uploadPath, childPath);
        	InputStream buffin = new ByteArrayInputStream(baseByteArr);
        	BufferedImage img = ImageIO.read(buffin);
            ImageIO.write(img, fileSufix, newFile);
        } catch (Exception e) {
            e.printStackTrace();  
            return null;
        }  
        return childPath;
    }  
    
    public static String getUploadPath(){
    	String uploadPath=ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
        return uploadPath += DEST_FILE_PATH;
    }
    
    public static String getNewImageName(String imageName){
    	if(StringUtils.isBlank(imageName))
	    	imageName=".jpg";
    	String fileSufix = imageName.substring(imageName.lastIndexOf(".") + 1);
        String prefix = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL", new Date());
        String childPath = imageName.substring(0, imageName.lastIndexOf(".")) + "_" + prefix + "." +fileSufix;
        
        
        return childPath;
    }
}
