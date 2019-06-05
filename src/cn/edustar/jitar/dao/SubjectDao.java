package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectWebpart;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.SiteNewsQueryParam;

/**
 * 访问学科信息的数据库接口定义.
 * 
 *
 */
public interface SubjectDao {

	/**
	 * 得到所有元学科列表.
	 * 
	 * @return
	 */
	public List<MetaSubject> getMetaSubjectList();

	/**
	 * 根据元学科名称得到元学科.
	 * 
	 * @return
	 */
	public MetaSubject getMetaSubjectByName(String msubjName);

	/**
	 * 根据元学科代码得到元学科.
	 * 
	 * @return
	 */
	public MetaSubject getMetaSubjectByCode(String msubjCode);

	/**
	 * 得到所有学科列表.
	 * 
	 * @return
	 */
	public List<Subject> getAllSubject();

	/**
	 * 查询
	 * @param searchSubjectName
	 * @return
	 */
	public List<Subject> getSubjectList(String searchSubjectName);
	/**
	 * 保存或修改学科对象.
	 * 
	 * @param subject
	 */
	public void saveOrUpdateSubject(Subject subject);

	/**
	 * 保存或修改元学科对象.
	 * 
	 * @param msubject
	 */
	public void saveOrUpdateSubject(MetaSubject msubject);

	/**
	 * 保存或修改元学段对象.
	 * 
	 * @param grade
	 */
	public void saveOrUpdateGrade(Grade grade);

	/**
	 * 更新元学段的信息.
	 * 
	 * @param grade
	 * @param gradeId
	 */
	public void updateGrade(Grade grade, int oldGradeId);

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
	 * 得到元学科的最大排序号.
	 */
	public int getMetaSubjectMaxOrderNum();

	/**
	 * 得到学科的最大排序号.
	 * 
	 * @return
	 */
	public int getSubjectMaxOrderNum();
	
	//得到学段
	public List<Grade> getMetaGradeListByMetaSubjectId(int metaSubjectId);
	//得到学段下的年级
	public List<Grade> getGradeLevelListByGradeId(int gradeId);
	

	/**
	 * 得到指定标识的学段对象.
	 * 
	 * @param gradeId
	 * @return
	 */
	public Grade getGrade(int gradeId);
	public Grade getGradeLevel(int gradeId);
	
	/**
	 * 得到所有学段列表.
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
	 * 设置指定新闻的状态.
	 * 
	 * @param news
	 * @param status
	 */
	public void updateSiteNewsStatus(SiteNews news, int status);

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
	
	/** 更新相关的显示问题 */
	public void updateAccessControlSubjectTitle(Subject subject);
}
