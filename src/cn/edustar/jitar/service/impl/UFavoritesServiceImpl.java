package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.pojos.UFavorites;
import cn.edustar.jitar.dao.UFavoritesDao;
import cn.edustar.jitar.service.UFavoritesService;

public class UFavoritesServiceImpl implements UFavoritesService {
    private UFavoritesDao favoritesDao;

    public void setUFavoritesDao(UFavoritesDao favoritesDao) {
        this.favoritesDao = favoritesDao;
    }
    public boolean Save(UFavorites favorate) {
        return favoritesDao.Save(favorate);
    }

    public boolean Save(int favUser, int ObjectType, String ObjectUuid, int ObjectID, String favTitle, String favInfo, int favTypeID, String favHref) {
        return favoritesDao.Save(favUser, ObjectType, ObjectUuid, ObjectID, favTitle, favInfo, favTypeID, favHref);
    }
    public boolean Del(UFavorites favorate) {
        return favoritesDao.Del(favorate);
    }

    public boolean Del(int favId) {
        UFavorites favorate = Load(favId);
        return favoritesDao.Del(favorate);
    }

    public boolean Exists(int favUser, int ObjectType, int ObjectID, String favHref) {
        return favoritesDao.Exists(favUser, ObjectType, ObjectID, favHref);
    }
    public UFavorites Load(int favId) {
        return favoritesDao.Load(favId);
    }
    public List<UFavorites> getUFavoritesList(int userId) {
        return favoritesDao.getUFavoritesList(userId);
    }
    public String GetHref(UFavorites favorate) {
        return favoritesDao.GetHref(favorate);
    }
    public String GetHref(int favId) {
        return favoritesDao.GetHref(favId);
    }
    public UFavorites getFavorites(int objType, int objId, int userId) {
        return favoritesDao.getFavorites(objType, objId, userId);
    }
}
