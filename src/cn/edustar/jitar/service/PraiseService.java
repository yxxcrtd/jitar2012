package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.Praise;

public interface PraiseService {
    /** 添加一个赞 */
    public void savePraise(Praise praise);
    
    /** 取消赞 */
    public void deletePraise(Praise praise);
    
    /** 用户身份已经赞过了 */
    public Praise getPraiseByTypeAndUserId(int objId, int objType, int userId);
    
    /** 得到某对象的赞列表 */
    public List<Praise> getPraiseByObjectType(int objId, int objType);
    
    /** 得到某对象的赞的数量 */
    public int getPraiseCountByObjectType(int objId, int objType);
}
