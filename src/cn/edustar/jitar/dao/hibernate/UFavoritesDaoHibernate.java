package cn.edustar.jitar.dao.hibernate;

import java.util.List;
import java.util.Date;

import cn.edustar.jitar.dao.UFavoritesDao;
import cn.edustar.jitar.pojos.UFavorites;

public class UFavoritesDaoHibernate extends BaseDaoHibernate implements UFavoritesDao {
    public boolean Save(UFavorites favorate) {
        getSession().save(favorate);
        getSession().flush();
        return true;
    }

    public boolean Save(int favUser, int ObjectType, String ObjectUuid, int ObjectID, String favTitle, String favInfo, int favTypeID, String favHref) {
        Date now = new Date();
        UFavorites fav = new UFavorites(favUser, ObjectType, ObjectUuid, ObjectID, now, favTitle, favInfo, favTypeID, favHref);
        return Save(fav);
    }

    @SuppressWarnings("unchecked")
    public boolean Exists(int favUser, int ObjectType, int ObjectID, String favHref) {
        String hql = "FROM UFavorites WHERE favUser=? and objectType=? and objectId=? and favHref=?";
        List<UFavorites> result = getSession().createQuery(hql).setInteger(0, favUser).setInteger(1, ObjectType).setInteger(2, ObjectID)
                .setString(3, favHref).list();
        if (result == null || result.size() == 0)
            return false;
        else
            return true;
    }

    public boolean Del(UFavorites favorate) {
        getSession().delete(favorate);
        getSession().flush();
        return true;
    }
    @SuppressWarnings("unchecked")
    public List<UFavorites> getUFavoritesList(int userId) {
        String queryString = "FROM UFavorites WHERE favUser=?";

        List<UFavorites> result = getSession().createQuery(queryString).setInteger(0, userId).list();
        if (result == null || result.size() == 0)
            return null;
        else
            return result;
    }
    public UFavorites Load(int favId) {
        String queryString = "FROM UFavorites WHERE favId = ?";
        return (UFavorites) getSession().createQuery(queryString).setInteger(0, favId).uniqueResult();

    }
    @SuppressWarnings("unused")
    public String GetHref(UFavorites favorate) {
        String sHref = favorate.getFavHref();
        if (sHref.equals(null) || sHref.equals("")) {
            int typeId = favorate.getFavTypeId();
            int objectType = favorate.getObjectType();
            String objectUuid = favorate.getObjectUuid();
            return "";
        } else {
            return sHref;
        }

    }
    public String GetHref(int favId) {
        UFavorites favorate = Load(favId);
        return GetHref(favorate);
    }
    @SuppressWarnings("unchecked")
    public UFavorites getFavorites(int objType, int objId, int userId) {
        String queryString = "FROM UFavorites WHERE objectType = :objectType And objectId=:objectId And favUser=:favUser";
        List<UFavorites> l = this.getSession().createQuery(queryString).setInteger("objectType", objType).setInteger("objectId", objId)
                .setInteger("favUser", userId).list();
        if (l == null || l.size() == 0) {
            return null;
        } else {
            return l.get(0);
        }
    }
}
