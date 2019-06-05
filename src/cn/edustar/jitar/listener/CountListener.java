package cn.edustar.jitar.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.ContextLoader;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.VideoService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 会话结束时把计数器写入到数据库。
 * 
 * @author mxh
 * 
 */
public class CountListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // System.out.println("sessionDestroyed 执行了…………");
        // 所有需要更新的计数器
        int[] allSaveObject = new int[]{ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),
                ObjectType.OBJECT_TYPE_PHOTO.getTypeId(), ObjectType.OBJECT_TYPE_VIDEO.getTypeId()};
        // 全局对象
        String sessionId = event.getSession().getId();
        CacheService cacheService = ContextLoader.getCurrentWebApplicationContext().getBean("cacheService", CacheService.class);
        for (int typeId : allSaveObject) {
            String prex = ObjectType.fromTypeId(typeId).getTypeName();
            try {
                String cacheKey = prex + "_" + sessionId;
                String sessionObjId = (String) cacheService.get(cacheKey);
                
                  /*if(typeId == 3){
                      System.out.println("失效时的 key = " + cacheKey + " , value = " + sessionObjId);
                      System.out.println("失效时的  expire ：" + cacheService.getExpireTime());        
                      System.out.println("失效时的 cacheService 类型：" + cacheService.getClass().getCanonicalName());
                  }*/
                 
                if (sessionObjId == null || sessionObjId.length() == 0) {
                    continue;
                }

                String cacheObjKey = null;
                String cacheObjCount = "0";
                String[] objIdArr = sessionObjId.split("\\|");

                if (typeId == ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
                    ArticleService articleService = ContextLoader.getCurrentWebApplicationContext().getBean("articleService", ArticleService.class);
                    for (int i = 0; i < objIdArr.length; i++) {
                        if (objIdArr[i].trim().length() > 0 && CommonUtil.isInteger(objIdArr[i])) {
                            cacheObjKey = ObjectType.OBJECT_TYPE_ARTICLE.getTypeId() + "_" + objIdArr[i];
                            cacheObjCount = (String) cacheService.get(cacheObjKey);
                            int objectId = Integer.valueOf(objIdArr[i]).intValue();
                            if (CommonUtil.isInteger(cacheObjCount)) {
                                articleService.increaseViewCount(objectId, Integer.valueOf(cacheObjCount).intValue());
                            }
                            // 删除键
                            cacheService.remove(cacheObjKey);
                        }
                    }
                } else if (typeId == ObjectType.OBJECT_TYPE_RESOURCE.getTypeId()) {
                    ResourceService resourceService = ContextLoader.getCurrentWebApplicationContext().getBean("resourceService",
                            ResourceService.class);
                    for (int i = 0; i < objIdArr.length; i++) {
                        if (objIdArr[i].trim().length() > 0 && CommonUtil.isInteger(objIdArr[i])) {
                            cacheObjKey = ObjectType.OBJECT_TYPE_RESOURCE.getTypeId() + "_" + objIdArr[i];
                            cacheObjCount = (String) cacheService.get(cacheObjKey);
                            int objectId = Integer.valueOf(objIdArr[i]).intValue();
                            if (CommonUtil.isInteger(cacheObjCount)) {
                                resourceService.increaseViewCount(objectId, Integer.valueOf(cacheObjCount).intValue());
                            }
                            // 删除键
                            cacheService.remove(cacheObjKey);
                        }
                    }
                }

                else if (typeId == ObjectType.OBJECT_TYPE_VIDEO.getTypeId()) {
                    VideoService videoService = ContextLoader.getCurrentWebApplicationContext().getBean("videoService", VideoService.class);
                    for (int i = 0; i < objIdArr.length; i++) {
                        if (objIdArr[i].trim().length() > 0 && CommonUtil.isInteger(objIdArr[i])) {
                            cacheObjKey = ObjectType.OBJECT_TYPE_VIDEO.getTypeId() + "_" + objIdArr[i];
                            cacheObjCount = (String) cacheService.get(cacheObjKey);
                            int objectId = Integer.valueOf(objIdArr[i]).intValue();
                            if (CommonUtil.isInteger(cacheObjCount)) {
                                videoService.increaseViewCount(objectId, Integer.valueOf(cacheObjCount).intValue());
                            }
                            // 删除键
                            cacheService.remove(cacheObjKey);
                        }
                    }
                } else if (typeId == ObjectType.OBJECT_TYPE_PHOTO.getTypeId()) {
                    PhotoService photoService = ContextLoader.getCurrentWebApplicationContext().getBean("photoService", PhotoService.class);
                    for (int i = 0; i < objIdArr.length; i++) {
                        if (objIdArr[i].trim().length() > 0 && CommonUtil.isInteger(objIdArr[i])) {
                            cacheObjKey = ObjectType.OBJECT_TYPE_PHOTO.getTypeId() + "_" + objIdArr[i];
                            cacheObjCount = (String) cacheService.get(cacheObjKey);
                            int objectId = Integer.valueOf(objIdArr[i]).intValue();
                            if (CommonUtil.isInteger(cacheObjCount)) {
                                photoService.increaseViewCount(objectId, Integer.valueOf(cacheObjCount).intValue());
                            }
                            // 删除键
                            cacheService.remove(cacheObjKey);
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("CountListener 统计插入数据库出现问题：" + e.getLocalizedMessage());
            }
        }
    }
}
