package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.PraiseDao;
import cn.edustar.jitar.pojos.Praise;

/**
 * 赞Dao实现类。
 * 
 * @author mxh
 * 
 */
public class PraiseDaoHibernate extends BaseDaoHibernate implements PraiseDao {

    public void savePraise(Praise praise) {
        this.getSession().save(praise);
    }

    public void deletePraise(Praise praise) {
        this.getSession().delete(praise);
    }

    /** 用户身份已经赞过了 */
    @SuppressWarnings("unchecked")
    public Praise getPraiseByTypeAndUserId(int objId, int objType, int userId) {
        String hql = "FROM Praise WHERE objId=:objId And objType=:objType And userId=:userId";
        List<Praise> plist = this.getSession().createQuery(hql).setInteger("objId", objId).setInteger("objType", objType).setInteger("userId", userId).list();
        if (plist == null || plist.size() == 0) {
            return null;
        }
        return plist.get(0);
    }

    /** 得到某对象的赞列表 */
    public List<Praise> getPraiseByObjectType(int objId, int objType) {
        String hql = "FROM Praise WHERE objId=:objId And objType=:objType Order BY praiseId DESC";
        List<Praise> plist = this.getSession().createQuery(hql).setInteger("objId", objId).setInteger("objType", objType).list();
        if (plist == null || plist.size() == 0) {
            return null;
        }
        return plist;
    }
    
    /** 得到某对象的赞的数量 */
    public int getPraiseCountByObjectType(int objId, int objType){
        String hql = "SELECT COUNT(*) FROM Praise WHERE objId=:objId And objType=:objType";
        int count = Integer.valueOf(this.getSession().createQuery(hql).setInteger("objId", objId).setInteger("objType", objType).uniqueResult().toString()).intValue();
        return count;
    }
    @Override
    public void evict(Object object) {
        // TODO Auto-generated method stub

    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

}
