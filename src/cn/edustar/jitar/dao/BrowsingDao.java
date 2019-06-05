package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.jitar.pojos.Browsing;

public interface BrowsingDao extends Dao{

    /** 得到某类型对象的一个访问记录 */
    public Browsing getBrowsing(int objType, int objId, int userId);
    
    /** 添加一个访问记录 */
    public void saveBrowsing(Browsing browsing);
    
    /** 添加或者更新一个访问记录 */
    public void saveOrUpdateBrowsing(Browsing browsing);
    
    /** 删除一个访问记录 */
    public void deleteBrowsing(Browsing browsing);
    
    /** 得到某对象的所有访问记录列表 */
    public List<Browsing> getBrowsingList(int objType, int objId);
    
    /** 得到最新的访问记录列表 */
    public List<Browsing> getBrowsingTopList(int topNumber, int objType, int objId);
}
