package cn.edustar.jitar.dao.hibernate;

import java.util.List;

import cn.edustar.jitar.dao.BrowsingDao;
import cn.edustar.jitar.pojos.Browsing;

public class BrowsingDaoHibernate extends BaseDaoHibernate implements BrowsingDao {

    @SuppressWarnings({ "unchecked"})
    @Override
    public Browsing getBrowsing(int objType, int objId, int userId) {
        String hql = "FROM Browsing WHERE objType=:objType And objId=:objId And userId=:userId";
        List<Browsing> browsingList = this.getSession().createQuery(hql).setInteger("objType", objType).setInteger("objId", objId).setInteger("userId", userId).list();
        if(browsingList == null || browsingList.size() == 0){
            return null;
        }
        else
        {
            return (Browsing)browsingList.get(0);
        }
    }

    @Override
    public void saveBrowsing(Browsing browsing) {
        this.getSession().save(browsing);
    }
    
    public void saveOrUpdateBrowsing(Browsing browsing){
        this.getSession().saveOrUpdate(browsing);
    }

    @Override
    public void deleteBrowsing(Browsing browsing) {
        this.getSession().delete(browsing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Browsing> getBrowsingList(int objType, int objId) {
        String hql = "FROM Browsing WHERE objType=:objType And objId=:objId Order BY browsingId DESC";
        List<Browsing> browsingList = this.getSession().createQuery(hql).setInteger("objType", objType).setInteger("objId", objId).list();
        return browsingList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Browsing> getBrowsingTopList(int topNumber, int objType, int objId) {
        String hql = "FROM Browsing WHERE objType=:objType And objId=:objId Order BY browsingId DESC";
        List<Browsing> browsingList = this.getSession().createQuery(hql).setInteger("objType", objType).setInteger("objId", objId).setFirstResult(0).setMaxResults(topNumber).list();
        return browsingList;
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
