package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.dao.PraiseDao;
import cn.edustar.jitar.pojos.Praise;
import cn.edustar.jitar.service.PraiseService;

/**
 * 赞服务实现类.
 * 
 * @author mxh
 * 
 */
public class PraiseServiceImpl implements PraiseService {

    private PraiseDao praiseDao;
    /** 添加一个赞 */
    public void savePraise(Praise praise) {
        this.praiseDao.savePraise(praise);
    }

    /** 取消赞 */
    public void deletePraise(Praise praise) {
        this.praiseDao.deletePraise(praise);
    }

    /** 用户身份已经赞过了 */
    public Praise getPraiseByTypeAndUserId(int objId, int objType, int userId){
        return this.praiseDao.getPraiseByTypeAndUserId(objId, objType, userId);
    }

    /** 得到某对象的赞列表 */
    public List<Praise> getPraiseByObjectType(int objId, int objType){
        return this.praiseDao.getPraiseByObjectType(objId, objType);
    }
    
    /** 得到某对象的赞的数量 */
    public int getPraiseCountByObjectType(int objId, int objType){
        return this.praiseDao.getPraiseCountByObjectType(objId, objType);
    }
    
    public void setPraiseDao(PraiseDao praiseDao) {
        this.praiseDao = praiseDao;
    }

}
