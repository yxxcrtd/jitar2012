package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.service.PhotoService;

public class ShowPhotoAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4692653547247863169L;

    private PhotoService photoService;

    private Integer photoId = null;

    @Override
    protected String execute(String cmd) throws Exception {
        photoId = this.params.getIntParamZeroAsNull("photoId");
        if (null == photoId) {
            this.addActionError("无效的标识。");
            return ERROR;
        }
        Photo photo = this.photoService.findById(photoId);
        if (null == photo) {
            this.addActionError("无法加载对象。");
            return ERROR;
        }

        request.setAttribute("photo", photo);
        return SUCCESS;
    }

    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

}
