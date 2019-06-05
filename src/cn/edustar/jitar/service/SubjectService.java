package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectWebpart;
import cn.edustar.jitar.pojos.User;

/**
 * 学科/学段及其相关服务.
 * 
 *
 */
public interface SubjectService {
	
	/**
	 * 清空内存中的缓存
	 */
	public void clearCacheData();
	
	/**
	 * 得到元学科列表, 注意是从缓存中获取的.
	 * 
	 * @return
	 */
	public List<MetaSubject> getMetaSubjectList();

	/**
	 * 得到元学科列表, 将强制刷新内存缓存.
	 * 
	 * @return
	 */
	public List<MetaSubject> getMetaSubjectListForceReload();

	/**
	 * 得到指定标识的元学科对象, 注意是从缓存中获取的.
	 * 
	 * @param metaSubjectId
	 * @return
	 */
	public MetaSubject getMetaSubjectById(int metaSubjectId);

	/**
	 * 得到指定标识的学科对象, 注意是从缓存中获取的.
	 * 
	 * @param id
	 * @return
	 */
	public Subject getSubjectById(int subjectId);

	/**
	 * 根据学科的MetaSubject和MetaGrade得到一个学科
	 *
	 * @param metaSubjectId
	 * @param metaGradeId
	 * @return
	 */
	public Subject getSubjectByMetaData(int metaSubjectId, int metaGradeId);

	/**
	 * 得到 idGrade = true 的学段
	 *
	 * @return
	 */
	public List<Grade> getMainGradeList();

	/**
	 * 得到某学段下的所有学科
	 * 
	 * @param gradeId
	 * @return
	 */
	public List<Subject> getSubjectByGradeId(int gradeId);

	/**
	 * 得到某学段下的某学科
	 * 
	 * @param metagradeId,metasubjectId
	 * @return
	 */
	public List<Subject> getSubjectByMetaGradeSubjectId(int metagradeId, int metasubjectId);

	/**
	 * 得到某学科下的学科
	 * 
	 * @param metasubjectId
	 * @return
	 */
	public List<Subject> getSubjectByMetaSubjectId(int metasubjectId);


	/**
	 * 按名称查询
	 * @param searchSubjectName
	 * @return
	 */
	public List<Subject> getSubjectList(String searchSubjectName);
	/**
	 * 得到指定学科名的学科对象, 注意是从缓存中获取的.
	 * 
	 * @param subjectName
	 * @return
	 */

	public Subject getSubjectByName(String subjectName);
	/**
	 * 根据元学科名称得到指定元学科的学科对象
	 *
	 * @param msubjName
	 * @return
	 */
	
	public List<Grade> getMetaGradeListByMetaSubjectId(int metaSubjectId);
	public List<Grade> getGradeLevelListByGradeId(int gradeId);
	
	
	public MetaSubject getMetaSubjectByName(String msubjName);

	/**
	 * 根据元学科代码得到指定元学科的学科对象
	 *
	 * @param msubjCode
	 * @return
	 */
	public MetaSubject getMetaSubjectByCode(String msubjCode);

	/**
	 * 得到指定代码的学科对象, 注意是从缓存中获取的.
	 * 
	 * @param subjectCode
	 * @return
	 */
	public Subject getSubjectByCode(String subjectCode);

	/**
	 * 得到所有学科的列表, 注意是从缓存中获取的.
	 * 
	 * @return
	 */
	public List<Subject> getSubjectList();

	/**
	 * 得到所有学科的列表, 强制从数据库重新加载.
	 * 
	 * @return
	 */
	public List<Subject> getSubjectListForceReload();

	/**
	 * 刷新学科内存缓存.
	 */
	public void refreshCache();

	/**
	 * 保存或修改指定学科对象.
	 * 
	 * @param subject
	 */
	public void saveOrUpdateSubject(Subject subject);

	/**
	 * 保存或修改指定元学段对象
	 *
	 * @param grade
	 */
	public void saveOrUpdateGrade(Grade grade);

	/**
	 * 更新元学段的信息
	 *
	 * @param grade
	 * @param oldGradeId
	 */
	public void updateGrade(Grade grade, int oldGradeId);

	/**
	 * 保存或修改指定元学科对象.
	 *
	 * @param msubject
	 */
	public void saveOrUpdateMetaSubject(MetaSubject msubject);

	/**
	 * 删除指定学科.
	 * 
	 * @param subject
	 */
	public void deleteSubject(Subject subject);

	/**
	 * 删除指定元学科.
	 * 
	 * @param msubject
	 */
	public void deleteMetaSubject(MetaSubject msubject);

	/**
	 * 删除指定元学段.
	 *
	 * @param grade
	 */
	public void deleteGrade(Grade grade);

	/**
	 * 得到元学科最大排序号.
	 *
	 * @return
	 */
	public int getMetaSubjectMaxOrderNum();

	/**
	 * 得到学科的最大排序号.
	 *
	 * @return
	 */
	public int getSubjectMaxOrderNum();

	/**
	 * 得到指定标识的学段对象.
	 * 
	 * @param gradeId
	 * @return
	 */
	public Grade getGrade(int gradeId);
	public Grade getGradeLevel(int gradeId);

	/**
	 * 得到学段列表.
	 * 
	 * @return
	 */
	public List<Grade> getGradeList();

	/**
	 * 得到指定标识的站点新闻.
	 * 
	 * @param newsId
	 * @return
	 */
	public SiteNews getSiteNews(int newsId);

	/**
	 * 新建或更新指定的站点新闻.
	 * 
	 * @param news
	 */
	public void saveOrUpateSiteNews(SiteNews news);

	/**
	 * 删除指定的站点新闻.
	 * 
	 * @param news
	 */
	public void deleteSiteNews(SiteNews news);

	/**
	 * 设置指定新闻的审核状态.
	 * 
	 * @param news
	 * @param audit
	 */
	public void auditSiteNews(SiteNews news, boolean audit);

	/**
	 * 添加/减少指定站点新闻的浏览数.
	 * 
	 * @param newsId
	 * @param incCount
	 */
	public void incSiteNewsViewCount(int newsId, int incCount);

	/**
	 * 得到指定条件下的站点/学科新闻列表.
	 * 
	 * @param param
	 * @param pager
	 */
	public DataTable getSiteNewsDataTable(SiteNewsQueryParam param, Pager pager);

	/**
	 * 每隔'30分钟'更新'学科统计'
	 * 
	 * @param
	 */
	public void subjectAutoStat();
	
	/**
	 * 得到年级
	 * @return
	 */
	public List<Grade> getGradeListOnlyIsGrade();
	
	/**
	 * 得到学科的所有内容块
	 * @param subjectId
	 * @return
	 */
	public List<SubjectWebpart> getSubjectWebpartList(int subjectId, Boolean isShow);
	
	/**
	 * 更新学科内容块
	 * @param subjectWebpart
	 */
	public void saveOrUpdateSubjectWebpart(SubjectWebpart subjectWebpart);
	
	/**
	 * 得到学科的全部自定义内容块列表
	 * @param subjectId
	 * @return
	 */
	public List<SubjectWebpart> getWebpartList(int subjectId, Boolean isSystemModule);
	
	/**
	 * 加载一个内容块
	 * @param subjectWebpartId
	 * @return
	 */
	public SubjectWebpart getSubjectWebpartById(int subjectWebpartId);
	
	/**
	 * 删除一个内容块
	 * @param subjectWebpart
	 */
	public void deleteSubjectWebpart(SubjectWebpart subjectWebpart);
	
	/**
	 * 移动内容块的位置
	 * @param subjectWebpart
	 * @param columnIndex
	 * @param widgetBeforeId
	 * @return
	 */
	public int setSubjectWebpartPosition(SubjectWebpart subjectWebpart,int columnIndex, int widgetBeforeId);
	
	/**
	 * 根据GUID加载对象
	 * @param objectGuid
	 * @return
	 */
	public Subject getSubjectByGuid(String objectGuid);
	
	/**
	 * 检查某用户是否属于某学科学段
	 * @param userId
	 * @param subjectId
	 * @return
	 */
	public boolean checkUserInSubject(User user, int subjectId);
	

	/**
	 * 检查用户的是否具有学科内容管理权限
	 * @param user
	 * @param subject
	 * @return
	 */
	public boolean checkSubjectContentManage(User user, Subject subject);
	
	/**
	 * 检查用户的是否具有学科管理权限
	 * @param user
	 * @param subject
	 * @return
	 */
	public boolean checkSubjectAdminManage(User user, Subject subject);
	
	/** 更新相关的显示问题 */
	public void updateAccessControlSubjectTitle(Subject subject);
	
	/** 得到实际存在的学段 ID */
	public List<Integer> getGradeIdList();

}
