package cn.edustar.jitar.service;
import java.util.List;
import cn.edustar.jitar.pojos.UFavorites;

public interface UFavoritesService {
	public boolean Save(UFavorites favorate);
	public boolean Save(int favUser,int ObjectType,String ObjectUuid,int ObjectID,String favTitle,String favInfo,int favTypeID,String favHref);
	public boolean Del(UFavorites favorate);
	public boolean Del(int favId);
	public boolean Exists(int favUser,int ObjectType,int ObjectID,String favHref);
	public UFavorites Load(int favId);
	public List<UFavorites> getUFavoritesList(int userId);
	public String GetHref(UFavorites favorate);
	public String GetHref(int favId);
	public UFavorites getFavorites(int objType,int objId, int userId);
}
