package cn.edustar.jitar.action;

import java.io.File;
import java.util.List;

import org.apache.struts2.util.ServletContextAware;

import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.action.AbstractServletAction;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.util.ImgUtil;
import cn.edustar.jitar.util.ParamUtil;

public class ImgCut extends AbstractServletAction implements ServletContextAware {
    /**
     * 
     */
    private static final long serialVersionUID = 3008514127077316026L;
    private PhotoService photoService;

    public String execute() {
        //对某些浏览器，需要返回内容类型。
        response.setContentType("text/html");
        ParamUtil param = new ParamUtil(request);
        if (!param.existParam("startPhotoId")) {
            return "Init";
        }
        int startPhotoId = param.safeGetIntParam("startPhotoId");
        int endPhotoId = param.safeGetIntParam("endPhotoId");
        int step = param.safeGetIntParam("step");
        //如果结束的id小于开始的id了，就不进行转换了。
        if(endPhotoId < startPhotoId || endPhotoId < 1){
            return NONE;
        }
        int startId = endPhotoId - step;
        if(startId < startPhotoId){
            startId = startPhotoId - 1;
        }
        if(startId < 0){
            startId = 0;
        }
        
        //System.out.println("startPhotoId="+startId + " endPhotoId=" + endPhotoId);
       
        List<String> list = GetPhotoHref(startId, endPhotoId);
        if (list == null || list.size() == 0) {
            return NONE;
        }

        String v = request.getServletContext().getRealPath("/");
        if (!v.endsWith(File.separator)) {
            v += File.separator;
        }
        for (int i = 0; i < list.size(); i++) {
            String destFile = list.get(i);
            if(destFile == null || destFile.length() == 0){
                continue;
            }
            //为了确保原来的路径里面没有\
            destFile = destFile.replace("\\", "/");
            String prefix = destFile.substring(0, destFile.lastIndexOf("/") + 1);
            String fileName = destFile.substring(destFile.lastIndexOf("/") + 1, destFile.length());
            //Linux系统就不用替换了。
            if(File.separator.equals("\\")){
                destFile = destFile.replace("/", File.separator);
                prefix = prefix.replace("/", File.separator);
            }
            
            //System.out.println("destFile="+ destFile);
            //System.out.println("prefix="+ prefix);
            //System.out.println("v + destFile="+ v + destFile);
            //System.out.println("v + prefix + 320x240_ + fileName="+ v + prefix + "320x240_" + fileName);
            //System.out.println("************************************");
            
            try {
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "320x240_" + fileName, JitarConst.DEFAULT_IMG_WIDTH, JitarConst.DEFAULT_IMG_HEIGHT);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "320x240_" + fileName, JitarConst.DEFAULT_IMG_WIDTH, JitarConst.DEFAULT_IMG_HEIGHT);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "200x240_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_1, JitarConst.DEFAULT_IMG_HEIGHT_1);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "200x120_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_2, JitarConst.DEFAULT_IMG_HEIGHT_2);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "160x120_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_3, JitarConst.DEFAULT_IMG_HEIGHT_3);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "565x280_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_4, JitarConst.DEFAULT_IMG_HEIGHT_4);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "70x70_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_5, JitarConst.DEFAULT_IMG_HEIGHT_5);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "690x400_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_6, JitarConst.DEFAULT_IMG_HEIGHT_6);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "230x250_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_7, JitarConst.DEFAULT_IMG_HEIGHT_7);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "230x136_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_8, JitarConst.DEFAULT_IMG_HEIGHT_8);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "230x100_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_9, JitarConst.DEFAULT_IMG_HEIGHT_9);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "665x500_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_10, JitarConst.DEFAULT_IMG_HEIGHT_10);
                ImgUtil.saveImageAsJpg(v + destFile, v + prefix + "125x100_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_11, JitarConst.DEFAULT_IMG_HEIGHT_11);
            } catch (Exception e) {
                log.error("图片截取失败,图片名称为:" + fileName);
            }
        }
        return NONE;
    }


    private List<String> GetPhotoHref(int sId, int eId) {
        return photoService.getPhotos(sId, eId);
    }

    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

}
