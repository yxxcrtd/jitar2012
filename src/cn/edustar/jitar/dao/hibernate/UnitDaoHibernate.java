package cn.edustar.jitar.dao.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.jdbc.Work;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.UnitDao;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.UnitDayCount;
import cn.edustar.jitar.pojos.UnitLinks;
import cn.edustar.jitar.pojos.UnitNews;
import cn.edustar.jitar.pojos.UnitSubject;
import cn.edustar.jitar.pojos.UnitWebpart;
import cn.edustar.jitar.service.UnitQueryParam;

public class UnitDaoHibernate extends BaseDaoHibernate implements UnitDao {

	//得到某单位的所有下级单位，注意得到的是多层下级
	@SuppressWarnings("unchecked")
	public List<Unit> getDownUnitList(Unit unit) {
		String queryString;
		if (null == unit) {			
			return this.getAllUnitOrChildUnitList(null);
		} else {
			queryString = "From Unit Where unitId != "+unit.getUnitId()+" And unitPathInfo LIKE '%/" + unit.getUnitId() + "/%' Order By itemIndex ASC";
			return (List<Unit>)this.getSession().createQuery(queryString).list();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Unit> getAllUnitOrChildUnitList(Unit unit,Boolean...showDeleted) {
		String queryString;
		if (null == unit) {
		    if(showDeleted == null || showDeleted.length < 1){
			queryString = "From Unit Order By itemIndex ASC";
			return (List<Unit>) this.getSession().createQuery(queryString).list();
			}
		    else
		    {
		        queryString = "From Unit Where delState=:delState Order By itemIndex ASC";
	            return (List<Unit>) this.getSession().createQuery(queryString).setBoolean("delState", showDeleted[0]).list();
		    }
			
		} else {
		    if(showDeleted == null || showDeleted.length < 1){
			queryString = "From Unit Where parentId = :parentId Order By itemIndex ASC";
			return (List<Unit>)  this.getSession().createQuery(queryString).setInteger("parentId", unit.getUnitId()).list();
		    }
		    else
		    {
	            queryString = "From Unit Where parentId = :parentId And delState=:delState Order By itemIndex ASC";
	            return (List<Unit>)  this.getSession().createQuery(queryString).setInteger("parentId", unit.getUnitId()).setBoolean("delState", showDeleted[0]).list();	        
		    }		    
		}
	}

	public Unit getUnitByGuid(String unitGuid) {
		String queryString = "From Unit Where unitGuid=:unitGuid";
		return (Unit) this.getSession().createQuery(queryString).setString("unitGuid", unitGuid).uniqueResult();
	}

	public Unit getUnitById(int unitId) {
		return (Unit)this.getSession().get(Unit.class, unitId);
	}

	public void deleteUnit(Unit unit) {
		// this.getSession().delete(unit);
		boolean delState = true;
		String hql = "UPDATE Unit SET delState = :delState WHERE unitId = :unitId";
		getSession().createQuery(hql).setBoolean("delState", delState).setInteger("unitId", unit.getUnitId()).executeUpdate();
	
	}
	
	public Unit getUnitByName(String unitName) {
		String queryString = "From Unit Where unitName= :unitName";
		return (Unit)this.getSession().createQuery(queryString).setString("unitName", unitName).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Unit getUnitByTitle(String unitTitle){
		String queryString = "From Unit Where unitTitle= :unitTitle";
		List<Unit> list=this.getSession().createQuery(queryString).setString("unitTitle", unitTitle).list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public void saveOrUpdateUnit(Unit Unit) {
		this.getSession().saveOrUpdate(Unit);
		this.getSession().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Unit> getChildUnitListByParenId(int parentId, Boolean...showDeleted) {
		String queryString;
		if(showDeleted == null || showDeleted.length < 1){
		    queryString = "From Unit Where parentId = :parentId Order By itemIndex ASC";
		    return (List<Unit>)this.getSession().createQuery(queryString).setInteger("parentId", parentId).list();
		}
		else{
		    queryString = "From Unit Where parentId = :parentId And delState=:delState Order By itemIndex ASC";
		    return (List<Unit>)this.getSession().createQuery(queryString).setInteger("parentId", parentId).setBoolean("delState", showDeleted[0]).list();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Unit> getChildUnitListByUnitType(String unitType){
		String queryString;
		queryString = "From Unit Where unitType = :unitType Order By itemIndex ASC";
		return (List<Unit>)this.getSession().createQuery(queryString).setString("unitType", unitType).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitWebpart> getUnitWebpartList(int unitId) {
		String queryString = "FROM UnitWebpart Where unitId = :unitId And visible = 1 Order By webpartZone ASC,rowIndex ASC";
		return (List<UnitWebpart>) this.getSession().createQuery(queryString).setInteger("unitId", unitId).list();
	}

	@SuppressWarnings("unchecked")
	public List<UnitLinks> getUnitLinksByUnitId(int unitId) {
		String queryString = "FROM UnitLinks Where unitId = :unitId Order By linkId DESC";
		return (List<UnitLinks>)this.getSession().createQuery(queryString).setInteger("unitId", unitId).list();
	}

	public void saveOrUpdateUnitWebpart(UnitWebpart unitWebpart) {
		this.getSession().saveOrUpdate(unitWebpart);
	}

	public void saveOrUpdateUnitSubject(UnitSubject unitSubject) {
		this.getSession().saveOrUpdate(unitSubject);
	}

	public UnitSubject getUnitSubjectById(int unitSubjectId) {
		return (UnitSubject) this.getSession().get(UnitSubject.class,unitSubjectId);
	}

	public void deleteUnitSubject(UnitSubject unitSubject) {
		this.getSession().delete(unitSubject);
	}

	@SuppressWarnings("unchecked")
	public List<UnitSubject> getSubjectByUnitId(int unitId) {
		String queryString = "FROM UnitSubject Where unitId=:unitId Order BY subjectId ASC";
		return (List<UnitSubject>) this.getSession().createQuery(queryString).setInteger("unitId", unitId).list();
	}

	@SuppressWarnings("unchecked")
	public boolean checkUnitSubjectIsExists(Subject subject, Unit unit) {
		String queryString = "FROM UnitSubject Where unitId=:unitId And subjectId = :subjectId";
		List<UnitSubject> us = (List<UnitSubject>)  this.getSession().createQuery(queryString).setInteger("unitId", unit.getUnitId()).setInteger("subjectId", subject.getSubjectId()).list();
				
		if (us == null)
			return false;
		else
			return !us.isEmpty();
	}
	/**
	 * 得到自定义的块内容
	 * @param unitId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UnitWebpart> getCustormUnitWebpart(int unitId)
	{
		String queryString = "FROM UnitWebpart Where unitId = :unitId And systemModule = 0";
		return (List<UnitWebpart>)this.getSession().createQuery(queryString).setInteger("unitId", unitId).list();
	}
	
	/**
	 * 根据标识加载机构内容块
	 * @param unitWebpartId
	 * @return
	 */
	public UnitWebpart getUnitWebpartById(int unitWebpartId)
	{
		return (UnitWebpart)this.getSession().get(UnitWebpart.class, unitWebpartId);
	}
	
	/**
	 * 文章数
	 * @param unitPath
	 * @return
	 */
	public int getUnitArtilceCount(int unitId)
	{
		String queryString = "SELECT COUNT(*) FROM Article WHERE unitId = " + unitId;	
		return ((Integer) this.getSession().createQuery(queryString).iterate().next() ).intValue();		
	}
	
	/**
	 * 相册数
	 * @param unitPath
	 * @return
	 */
	public int getUnitPhotoCount(int unitId)
	{
		String queryString = "SELECT COUNT(*) FROM Photo WHERE unitId = " + unitId;
		return ((Integer) this.getSession().createQuery(queryString).iterate().next() ).intValue();
	}
	
	/**
	 * 视频数
	 * @param unitPath
	 * @return
	 */
	public int getUnitVideoCount(int unitId)
	{
		String queryString = "SELECT COUNT(*) FROM Video WHERE unitId = " + unitId ;
		return ((Integer) this.getSession().createQuery(queryString).iterate().next() ).intValue();
	}
	
	/**
	 * 资源数
	 * @param unitPath
	 * @return
	 */
	public int getUnitResourceCount(int unitId)
	{
		String queryString = "SELECT COUNT(*) FROM Resource WHERE unitId = " + unitId ;
		return ((Integer) this.getSession().createQuery(queryString).iterate().next() ).intValue();
	}
	
	/**
	 * 投票数
	 * @param unitPath
	 * @return
	 */
	public int getUnitVoteCount(String unitGuid)
	{
		String queryString = "SELECT COUNT(*) FROM Vote WHERE parentGuid = '" + unitGuid + "'";
		//String count = this.getSession().executeScalar(queryString);
		return ((Integer) this.getSession().createQuery(queryString).iterate().next() ).intValue();
	}
	
	/**
	 * 用户数
	 * @param unitId
	 * @return
	 */
	public int getUnitUserCount(int unitId)
	{
		String queryString = "SELECT COUNT(*) FROM User WHERE unitId = " + unitId;
		return ((Integer) this.getSession().createQuery(queryString).iterate().next() ).intValue();
	}
	
	
	/**
	 * 设置机构内容块位置
	 * @param unitWebpart
	 * @param columnIndex
	 * @param widgetBeforeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int setUnitWebpartPosition(UnitWebpart unitWebpart,int columnIndex, int widgetBeforeId)
	{
		String find_hql = "FROM UnitWebpart WHERE unitId = :unitId AND webpartZone = :webpartZone ORDER BY rowIndex ASC, unitWebpartId ASC";
		List<UnitWebpart> unitWebpart_list = this.getSession().createQuery(find_hql).setInteger("unitId", unitWebpart.getUnitId()).setInteger("webpartZone", columnIndex).list();
		

		int this_row_index = 1;
		int update_count = 0;
		for (UnitWebpart witer : unitWebpart_list) {
			// 如果找到了插入位置, 则插入 widget 到这个 this_row_index.
			if (witer.getUnitWebpartId() == widgetBeforeId) {
				String ins_hql = "UPDATE UnitWebpart SET webpartZone = :webpartZone, rowIndex = :rowIndex WHERE unitWebpartId = :unitWebpartId";
				update_count = this.getSession().createQuery(ins_hql).setInteger("webpartZone", columnIndex).setInteger("rowIndex", this_row_index).setInteger("unitWebpartId", unitWebpart.getUnitWebpartId()).executeUpdate();
				++this_row_index;
			} else if (witer.getUnitWebpartId() == unitWebpart.getUnitWebpartId())
				// 碰到自己忽略.
				continue;

			// 如果应该的位置 和 witer 的位置不符合, 则现在更新它.
			if (this_row_index != witer.getRowIndex()) {
				String move_hql = "UPDATE UnitWebpart SET rowIndex = :rowIndex WHERE unitWebpartId = :unitWebpartId";
				this.getSession().createQuery(move_hql).setInteger("rowIndex", this_row_index).setInteger("unitWebpartId", witer.getUnitWebpartId()).executeUpdate();
				
			}
			++this_row_index;
		}

		// 可能没找到插入位置, 则插入到末尾.
		if (update_count == 0) {
			String append_hql = "UPDATE UnitWebpart SET webpartZone = :webpartZone, rowIndex = :rowIndex WHERE unitWebpartId = :unitWebpartId";
			update_count = this.getSession().createQuery(append_hql).setInteger("webpartZone", columnIndex).setInteger("rowIndex", this_row_index).setInteger("unitWebpartId", unitWebpart.getUnitWebpartId()).executeUpdate();
		}
		return update_count;
		
	}
	
	public int getMaxId(){
		String queryString = "SELECT MAX(unitId) FROM Unit ";
		return ((Integer) this.getSession().createQuery(queryString).iterate().next() ).intValue();
	}
	
	/**
	 * 添加机构新闻
	 * @param unitNews
	 */
	public void saveOrUpdateUnitNews(UnitNews unitNews)
	{
		this.getSession().saveOrUpdate(unitNews);
	}
	
	/**
	 * 删除机构新闻
	 * @param unitNews
	 */
	public void deleteUnitNews(UnitNews unitNews)
	{
		this.getSession().delete(unitNews);
	}

	/**
	 * 更新 UnitLinks
	 * @param unitLinks
	 */
	public void saveOrUpdateUnitLinks(UnitLinks unitLinks)
	{
		this.getSession().saveOrUpdate(unitLinks);
	}
	
	/**
	 * 加载 UnitLinks
	 * @param unitLinksId
	 * @return
	 */
	public UnitLinks getUnitLinksById(int unitLinksId)
	{
		return (UnitLinks)this.getSession().get(UnitLinks.class, unitLinksId);
	}
	
		
	/**
	 * 得到系统的块内容
	 * @param unitId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UnitWebpart> getSystemUnitWebpart(int unitId)
	{
		String queryString = "FROM UnitWebpart Where unitId = :unitId And systemModule = 1";
		return (List<UnitWebpart>)this.getSession().createQuery(queryString).setInteger("unitId", unitId).list();
	}
	
	/**
	 * 删除一个 UnitWebpart
	 * @param unitId
	 */
	public void deleteUnitWebpart(UnitWebpart unitWebpart)
	{
		this.getSession().delete(unitWebpart);
		this.getSession().flush();
	}
	
	/**
	 * 根据标识加载机构新闻
	 * @param unitNewsId
	 * @return
	 */
	public UnitNews getUnitNewsById(int unitNewsId)
	{
		return (UnitNews)this.getSession().get(UnitNews.class, unitNewsId);
	}
	
	/**
	 * 根据 id 删除友情链接
	 * @param linkId
	 */
	public void deleteUnitLinksByLinkId(int linkId)
	{
		UnitLinks unitLinks = (UnitLinks)this.getSession().get(UnitLinks.class, linkId);
		if(null != unitLinks) this.getSession().delete(unitLinks);
	}
	
	/** 更新对象的显示名 */
	public void updateAccessControlUnitTitle(Unit unit){
		if(unit == null ) return;
		String queryString = "UPDATE AccessControl Set objectTitle =:objectTitle Where objectId=:objectId And (objectType="+AccessControl.OBJECTTYPE_UNITCONTENTADMIN+" or objectType="+AccessControl.OBJECTTYPE_UNITSYSTEMADMIN+" Or objectType="+AccessControl.OBJECTTYPE_UNITUSERADMIN+")";
		this.getSession().createQuery(queryString).setString("objectTitle", unit.getUnitTitle()).setInteger("objectId", unit.getUnitId()).executeUpdate();

	}
	
	/** 删除单位的所有链接 */
	public void deleteAllUnitLinks(Unit unit)
	{
		if(unit == null) return;
		String queryString = "DELETE FROM UnitLinks Where unitId = :unitId";
		this.getSession().createQuery(queryString).setInteger("unitId", unit.getUnitId()).executeUpdate();
		
		//this.getSession().bulkUpdate(queryString, unit.getUnitId());
	}
	
	/** 删除机构的所有新闻 */
	public void deleteAllUnitNews(Unit unit)
	{
		if(unit == null) return;
		String queryString = "DELETE FROM UnitNews Where unitId = :unitId";
		this.getSession().createQuery(queryString).setInteger("unitId", unit.getUnitId()).executeUpdate();
		//this.getSession().bulkUpdate(queryString, unit.getUnitId());
	}
	
	public void deleteUnitWebpart(Unit unit)
	{
		if(unit == null) return;
		String queryString = "DELETE FROM UnitWebpart Where unitId= :unitId";
		this.getSession().createQuery(queryString).setInteger("unitId", unit.getUnitId()).executeUpdate();
		//this.getSession().bulkUpdate(queryString, unit.getUnitId());
	}
	
	public void updateUnitStat(final int unitId, final String beginDate, final String endDate) {	
		 this.getSession().doWork(new Work(){
				public void execute(Connection connection)throws SQLException{
					//通过JDBC API执行用于批量更新的SQL语句
					CallableStatement cs=connection.prepareCall("{call statAllUnit(?, ?, ?)}");						
					cs.setInt(1, unitId);
					cs.setString(2, beginDate);
					cs.setString(3, endDate);
					cs.execute();
					}
					});		
				
	}
	
	@SuppressWarnings("unchecked")
	public List<Unit> getAllUnitByUnitId(int unitId)
	{
		String queryString;
		queryString = "From Unit Where unitId = :unitId Order By itemIndex ASC";
		return (List<Unit>) this.getSession().createQuery(queryString).setInteger("unitId",unitId).list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Unit> getUnitList(UnitQueryParam param, Pager pager) {	
		QueryHelper query = param.createQuery();
		if (pager == null) {
			return query.queryData(this.getSession(), -1, param.count);
		} else {
			return query.queryDataAndTotalCount(this.getSession(), pager);
		}
	
	}
	
	/**
	 * 返回当前机构及其下级所有机构的用户数
	 */
	public Long getAllUserCount(Unit unit){
		if(unit == null) return 0L;
		String queryString = "SELECT SUM(userCount) FROM UnitDayCount WHERE unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		if(count == null)
			return 0L;
		else
			return Long.valueOf(count.toString());
	}
	
	/**
	 * 返回当前机构及其下级所有机构的文章数
	 */
	public Long getAllArticleCount(Unit unit){
		if(unit == null) return 0L;
		String queryString = "SELECT SUM(articleCount) + SUM(historyArticleCount) FROM UnitDayCount WHERE unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		if(count == null)
			return 0L;
		else
			return Long.valueOf(count.toString());
	}
	
	public Long getAllResourceCount(Unit unit){
		if(unit == null) return 0L;
		String queryString = "SELECT SUM(resourceCount) FROM UnitDayCount WHERE unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		if(count == null)
			return 0L;
		else
			return Long.valueOf(count.toString());
		
	}
	
	public Long getAllPhotoCount(Unit unit){
		if(unit == null) return 0L;
		String queryString = "SELECT SUM(photoCount) FROM UnitDayCount WHERE unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		if(count == null)
			return 0L;
		else
			return Long.valueOf(count.toString());
	}
	
	
	public Long getAllVideoCount(Unit unit)
	{
		if(unit == null) return 0L;
		String queryString = "SELECT SUM(videoCount) FROM UnitDayCount WHERE unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		if(count == null)
			return 0L;
		else
			return Long.valueOf(count.toString());
	}
	
	
	 /**
     * 以下4个统计只包含子级所有的数量
     * @param unit
     * @return
     */
    public Long getOnlyChildUserCount(Unit unit){
        if(unit == null) return 0L;
        String queryString = "SELECT SUM(userCount) FROM UnitDayCount WHERE unitId != " + unit.getUnitId() + " And unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
        Object count = this.getSession().createQuery(queryString).uniqueResult();
        if(count == null)
            return 0L;
        else
            return Long.valueOf(count.toString());
    }
    public Long getOnlyChildArticleCount(Unit unit){
        if(unit == null) return 0L;
        String queryString = "SELECT SUM(articleCount) + SUM(historyArticleCount) FROM UnitDayCount WHERE unitId != " + unit.getUnitId() + " And unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
        Object count = this.getSession().createQuery(queryString).uniqueResult();
        if(count == null)
            return 0L;
        else
            return Long.valueOf(count.toString());
    }
    public Long getOnlyChildResourceCount(Unit unit){
        if(unit == null) return 0L;
        String queryString = "SELECT SUM(resourceCount) FROM UnitDayCount WHERE unitId != " + unit.getUnitId() + " And unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
        Object count = this.getSession().createQuery(queryString).uniqueResult();
        if(count == null)
            return 0L;
        else
            return Long.valueOf(count.toString());
    }
    public Long getOnlyChildPhotoCount(Unit unit){
        if(unit == null) return 0L;
        String queryString = "SELECT SUM(photoCount) FROM UnitDayCount WHERE unitId != " + unit.getUnitId() + " And unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
        Object count = this.getSession().createQuery(queryString).uniqueResult();
        if(count == null)
            return 0L;
        else
            return Long.valueOf(count.toString());
    }
    public Long getOnlyChildVideoCount(Unit unit){
        if(unit == null) return 0L;
        String queryString = "SELECT SUM(videoCount) FROM UnitDayCount WHERE unitId != " + unit.getUnitId() + " And unitPathInfo LIKE '%/" + unit.getUnitId() + "/%'";
        Object count = this.getSession().createQuery(queryString).uniqueResult();
        if(count == null)
            return 0L;
        else
            return Long.valueOf(count.toString());
    }
    
	
	public void resetParentUnitCount(int parentId){
	    Unit parentUnit = this.getUnitById(parentId);
	    if(parentUnit == null){
	        return;
	    }
	    List<Unit> us = this.getAllUnitOrChildUnitList(parentUnit, false);
	    if(us == null || us.size() == 0){
	        parentUnit.setHasChild(false);
	    }
	    else{
            parentUnit.setHasChild(true);
	    }
        this.saveOrUpdateUnit(parentUnit);
	}
	
	/**
	 * 每天的统计
	 */
	public void statUnitDayCount(){
	    this.getSession().doWork(new Work(){
            public void execute(Connection connection)throws SQLException{
                    //通过JDBC API执行用于批量更新的SQL语句
                    CallableStatement cs = connection.prepareCall("{call UnitDayCount()}"); 
                    cs.execute();
            }
        });
	}
	
	public UnitDayCount queryUnitDayCount(int unitId){
	    return (UnitDayCount)this.getSession().get(UnitDayCount.class, unitId);
	}
	
	/**
	 * 更新机构排名
	 * @param unitId
	 */
	public void statUnitRank(final int unitId){
        this.getSession().doWork(new Work(){
            public void execute(Connection connection)throws SQLException{
                    CallableStatement cs = connection.prepareCall("{call UnitRank(?)}"); 
                    cs.setInt(1, unitId);
                    cs.execute();
            }
        });
    }
	
	public void saveOrUpdateUnitDayCount(UnitDayCount unitDayCount){
	    this.getSession().merge(unitDayCount);
	}
	
}
