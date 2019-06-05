package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.SubjectDao;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectWebpart;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.SiteNewsQueryParam;
import cn.edustar.jitar.service.SubjectService;

/**
 * 学科服务具体实现.
 * 
 * 
 */
public class SubjectServiceImpl implements SubjectService {

    /** 数据库访问接口 */
    private SubjectDao subj_dao;

    /** 新的缓存服务 */
    private CacheService cacheService;
    
    /** 权限服务 */
    private AccessControlService accessControlService;

    /** 数据库访问接口 */
    public void setSubjectDao(SubjectDao subj_dao) {
        this.subj_dao = subj_dao;
    }

    /** 缓存使用的 key */
    private String CACHEKEY_ALL_SUBJECT = "all_subject";
    private String CACHEKEY_ALL_GRADE = "all_grade";
    private String CACHEKEY_ALL_METASUBJECT = "all_meta_subject";

    public void clearCacheData() {
        this.cacheService.remove(CACHEKEY_ALL_SUBJECT);
        this.cacheService.remove(CACHEKEY_ALL_GRADE);
        this.cacheService.remove(CACHEKEY_ALL_METASUBJECT);

    }

    /** 兼容以前的方法，方法名保留 */
    public List<MetaSubject> getMetaSubjectList() {
        return getMetaSubjectListForceReload();
    }

    /*
     * 得到 isGrade = true 的学段
     */
    public List<Grade> getMainGradeList() {
        List<Grade> allGrade = this.getGradeList();
        List<Grade> mainGrade = new ArrayList<Grade>();
        for (int i = 0; i < allGrade.size(); i++) {
            if (allGrade.get(i).getIsGrade()) {
                mainGrade.add(allGrade.get(i));
            }
        }
        if (mainGrade.size() > 0) {
            return mainGrade;
        } else {
            return null;
        }
    }

    public List<Subject> getSubjectByMetaGradeSubjectId(int metagradeId, int metasubjectId) {
        List<Subject> allSub = this.getSubjectList();
        List<Subject> subByGrade = new ArrayList<Subject>();
        if (allSub == null) {
            return null;
        }

        for (int i = 0; i < allSub.size(); i++) {
            if ((allSub.get(i).getMetaSubject().getMsubjId() == metasubjectId) && (allSub.get(i).getMetaGrade().getGradeId() == metagradeId)) {
                subByGrade.add(allSub.get(i));
            }
        }
        if (subByGrade.size() > 0) {
            return subByGrade;
        } else {
            return null;
        }
    }

    public List<Subject> getSubjectByMetaSubjectId(int metasubjectId) {
        List<Subject> allSub = this.getSubjectList();
        List<Subject> subByGrade = new ArrayList<Subject>();
        if (allSub == null) {
            return null;
        }

        for (int i = 0; i < allSub.size(); i++) {
            if (allSub.get(i).getMetaSubject().getMsubjId() == metasubjectId) {
                subByGrade.add(allSub.get(i));
            }
        }
        if (subByGrade.size() > 0) {
            return subByGrade;
        } else {
            return null;
        }
    }

    /*
     * 得到某学段的所有学科。
     */
    public List<Subject> getSubjectByGradeId(int gradeId) {
        List<Subject> allSub = this.getSubjectList();
        List<Subject> subByGrade = new ArrayList<Subject>();
        if (allSub == null) {
            return null;
        }

        for (int i = 0; i < allSub.size(); i++) {
            if (allSub.get(i).getMetaGrade().getGradeId() == gradeId) {
                subByGrade.add(allSub.get(i));
            }
        }
        if (subByGrade.size() > 0) {
            return subByGrade;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<MetaSubject> getMetaSubjectListForceReload() {
        List<MetaSubject> meta_subj_list = (List<MetaSubject>) this.cacheService.get(CACHEKEY_ALL_METASUBJECT);
        if (meta_subj_list == null) {
            meta_subj_list = subj_dao.getMetaSubjectList();
            if(meta_subj_list != null){
                this.cacheService.put(CACHEKEY_ALL_METASUBJECT, meta_subj_list);
            }
        }
        return meta_subj_list;
    }

    
    public MetaSubject getMetaSubjectById(int metaSubjectId) {
        // 得到所有.
        List<MetaSubject> msubj_list = this.getMetaSubjectList();
        if(msubj_list != null){
            // 遍历查找, 我们假定的事实是元学科数量有限, 例如不超过 20 个.
            for (MetaSubject msubj : msubj_list)
                if (msubj.getMsubjId() == metaSubjectId)
                    return msubj;
        }
        // 未找到则返回 null.
        return null;
    }

    /*
     * 根据 subjectId得到对象。
     */
    public Subject getSubjectById(int id) {
        List<Subject> subj_list = this.getSubjectList();
        for(Subject sub : subj_list){
            if(sub.getSubjectId() == id){
                return sub;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Grade> getMetaGradeListByMetaSubjectId(int metaSubjectId) {
        List<Grade> mg = (List<Grade>)this.cacheService.get("MetaGradeList_" + metaSubjectId);
        if(mg == null){
            mg = this.subj_dao.getMetaGradeListByMetaSubjectId(metaSubjectId);
            this.cacheService.put("MetaGradeList_" + metaSubjectId, mg);
        }
        return mg;
    }

    @SuppressWarnings("unchecked")
    public List<Grade> getGradeLevelListByGradeId(int gradeId) {
        List<Grade> gl = (List<Grade>)this.cacheService.get("GradeLevelList_" + gradeId);
        if(gl == null){
            gl = this.subj_dao.getGradeLevelListByGradeId(gradeId);
            this.cacheService.put("GradeLevelList_" + gradeId, gl);
        }
        return gl;
    }
    /*
     * 根据元数据得到学科。
     */
    public Subject getSubjectByMetaData(int metaSubjectId, int metaGradeId) {
        // 将 5200 格式化成 5000
        metaGradeId = metaGradeId / 1000 * 1000;
        List<Subject> subj_list = this.getSubjectList();
        for (int i = 0; i < subj_list.size(); i++) {
            Subject s = (Subject) subj_list.get(i);
            if (s.getMetaSubject().getMsubjId() == metaSubjectId && s.getMetaGrade().getGradeId() == metaGradeId) {
                return s;
            }
        }
        return null;
    }

    public List<Subject> getSubjectList(String searchSubjectName) {
        return subj_dao.getSubjectList(searchSubjectName);
    }

    /*
     * 根据学科名称查找。
     */
    public Subject getSubjectByName(String subjectName) {
        if (subjectName == null)
            return null;

        List<Subject> subj_list = this.getSubjectList();
        for (Subject subject : subj_list) {
            if (subjectName.equalsIgnoreCase(subject.getSubjectName()))
                return subject;
        }
        return null;
    }

  
    public MetaSubject getMetaSubjectByName(String msubjName) {
        return subj_dao.getMetaSubjectByName(msubjName);
    }

    public MetaSubject getMetaSubjectByCode(String msubjCode) {
        return subj_dao.getMetaSubjectByCode(msubjCode);
    }

  
    public Subject getSubjectByCode(String subjectCode) {
        if (subjectCode == null)
            return null;

        List<Subject> subj_list = this.getSubjectList();
        for (Subject subject : subj_list) {
            if (subjectCode.equalsIgnoreCase(subject.getSubjectCode()))
                return subject;
        }
        return null;
    }

    public List<Subject> getSubjectList() {
        return getSubjectListForceReload();
    }

    /**
     * 从数据库中得到。
     */
    @SuppressWarnings("unchecked")
    public List<Subject> getSubjectListForceReload() {
        List<Subject> subj_list = (List<Subject>) this.cacheService.get(CACHEKEY_ALL_SUBJECT);
        if (subj_list == null) {
            subj_list = subj_dao.getAllSubject();
            if (subj_list != null) {
                this.cacheService.put(this.CACHEKEY_ALL_SUBJECT, subj_list);
            }
        }
        return subj_list;
    }

    /*
     * 刷新缓存。
     */
    public void refreshCache() {
        this.clearCacheData();
    }

    /**
     * 根据 gradeId 得到对象。
     */
    public Grade getGrade(int gradeId) {
        List<Grade> grade_list = (List<Grade>)this.getGradeList();
        if(grade_list != null){
            for(Grade grade : grade_list){
                if(grade.getGradeId() == gradeId){
                    return grade;
                }
            }
        }
        return null;
    }

    public Grade getGradeLevel(int gradeId) {
        return subj_dao.getGradeLevel(gradeId);
    }

    /**
     * 得到所有的学科
     */
    @SuppressWarnings("unchecked")
    public List<Grade> getGradeList() {
        List<Grade> grade_list = (List<Grade>) this.cacheService.get(CACHEKEY_ALL_GRADE);
        if (grade_list == null) {
            grade_list = subj_dao.getGradeList();
            if (grade_list != null) {
                this.cacheService.put(CACHEKEY_ALL_GRADE, grade_list);
            }
        }
        return grade_list;
    }

    public void saveOrUpdateSubject(Subject subject) {
        subj_dao.saveOrUpdateSubject(subject);
        refreshCache();
    }

    public void saveOrUpdateGrade(Grade grade) {
        subj_dao.saveOrUpdateGrade(grade);
        refreshCache();
    }

    public void updateGrade(Grade grade, int oldGradeId) {
        subj_dao.updateGrade(grade, oldGradeId);
        refreshCache();
    }

    public void saveOrUpdateMetaSubject(MetaSubject msubject) {
        subj_dao.saveOrUpdateSubject(msubject);
        refreshCache();
    }

    public void deleteSubject(Subject subject) {
        subj_dao.deleteSubject(subject);
        refreshCache();
    }

    public void deleteMetaSubject(MetaSubject msubject) {
        subj_dao.deleteMetaSubject(msubject);
        refreshCache();
    }

    public void deleteGrade(Grade grade) {
        subj_dao.deleteGrade(grade);
        refreshCache();
    }

    public int getMetaSubjectMaxOrderNum() {
        return subj_dao.getMetaSubjectMaxOrderNum();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.service.SubjectService#getSubjectMaxOrderNum()
     */
    public int getSubjectMaxOrderNum() {
        return subj_dao.getSubjectMaxOrderNum();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.service.SubjectService#getSiteNews(int)
     */
    public SiteNews getSiteNews(int newsId) {
        return subj_dao.getSiteNews(newsId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.service.SubjectService#saveOrUpateSiteNews(cn.edustar
     * .jitar.pojos.SiteNews)
     */
    public void saveOrUpateSiteNews(SiteNews news) {
        subj_dao.saveOrUpateSiteNews(news);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.service.SubjectService#deleteSiteNews(cn.edustar.jitar
     * .pojos.SiteNews)
     */
    public void deleteSiteNews(SiteNews news) {
        subj_dao.deleteSiteNews(news);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.edustar.jitar.service.SubjectService#auditSiteNews(cn.edustar.jitar
     * .pojos.SiteNews, boolean)
     */
    public void auditSiteNews(SiteNews news, boolean audit) {
        int status = audit ? SiteNews.NEWS_STATUS_NORMAL : SiteNews.NEWS_STATUS_WAIT_AUTID;
        subj_dao.updateSiteNewsStatus(news, status);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.service.SubjectService#incSiteNewsViewCount(int,
     * int)
     */
    public void incSiteNewsViewCount(int newsId, int incCount) {
        subj_dao.incSiteNewsViewCount(newsId, incCount);
    }

    public DataTable getSiteNewsDataTable(SiteNewsQueryParam param, Pager pager) {
        return subj_dao.getSiteNewsDataTable(param, pager);
    }

    public void subjectAutoStat() {
        subj_dao.subjectAutoStat();
        this.refreshCache();
    }

    public List<Grade> getGradeListOnlyIsGrade() {
        return this.subj_dao.getGradeListOnlyIsGrade();
    }

    /**
     * 得到学科的所有内容块
     * 
     * @param subjectId
     * @return
     */
    public List<SubjectWebpart> getSubjectWebpartList(int subjectId, Boolean isShow) {
        return this.subj_dao.getSubjectWebpartList(subjectId, isShow);
    }

    /**
     * 更新学科内容块
     * 
     * @param subjectWebpart
     */
    public void saveOrUpdateSubjectWebpart(SubjectWebpart subjectWebpart) {
        this.subj_dao.saveOrUpdateSubjectWebpart(subjectWebpart);
    }

    /**
     * 得到学科的全部自定义内容块列表
     * 
     * @param subjectId
     * @return
     */
    public List<SubjectWebpart> getWebpartList(int subjectId, Boolean isSystemModule) {
        return this.subj_dao.getWebpartList(subjectId, isSystemModule);
    }

    /**
     * 加载一个内容块
     * 
     * @param subjectWebpartId
     * @return
     */
    public SubjectWebpart getSubjectWebpartById(int subjectWebpartId) {
        return this.subj_dao.getSubjectWebpartById(subjectWebpartId);
    }

    /**
     * 删除一个内容块
     * 
     * @param subjectWebpart
     */
    public void deleteSubjectWebpart(SubjectWebpart subjectWebpart) {
        this.subj_dao.deleteSubjectWebpart(subjectWebpart);
    }

    /**
     * 移动内容块的位置
     * 
     * @param subjectWebpart
     * @param columnIndex
     * @param widgetBeforeId
     * @return
     */
    public int setSubjectWebpartPosition(SubjectWebpart subjectWebpart, int columnIndex, int widgetBeforeId) {
        return this.subj_dao.setSubjectWebpartPosition(subjectWebpart, columnIndex, widgetBeforeId);
    }

    /**
     * 根据GUID加载对象
     * 
     * @param objectGuid
     * @return
     */
    public Subject getSubjectByGuid(String objectGuid) {
        List<Subject> subject_list = this.getSubjectList();
        if(subject_list != null){
            for(Subject subject : subject_list){
                if(subject.getSubjectGuid().equalsIgnoreCase(objectGuid)){
                    return subject;
                }
            }
        }
        return null;
    }

    /**
     * 检查某用户是否属于某学科学段
     * 
     * @param userId
     * @param subjectId
     * @return
     */
    public boolean checkUserInSubject(User user, int subjectId) {
        return this.subj_dao.checkUserInSubject(user, subjectId);
    }

    /**
     * 检查用户的是否具有学科内容管理权限
     * 
     * @param user
     * @param subject
     * @return
     */
    public boolean checkSubjectContentManage(User user, Subject subject) {
        if (user == null || subject == null) {
            return false;
        }

        if (this.checkSubjectAdminManage(user, subject)) {
            return true;
        }

        return false;
    }

    /**
     * 检查用户的是否具有学科管理权限
     * 
     * @param user
     * @param subject
     * @return
     */
    public boolean checkSubjectAdminManage(User user, Subject subject) {
        if (user == null || subject == null) {
            return false;
        }
        
        if (accessControlService.isSystemAdmin(user)) {
            return true;
        }
        if (user.getSubjectId() == null) {
            return false;
        }
        if (user.getGradeId() == null) {
            if ((user.getSubjectId() == subject.getMetaSubject().getMsubjId())) {
                return true;
            }
        } else {
            if ((user.getSubjectId() == subject.getMetaSubject().getMsubjId()) && (String.valueOf(user.getGradeId()).substring(0, 1).equals(String.valueOf(subject.getMetaGrade().getGradeId()).substring(0, 1)))) {
                return true;
            }
        }
        return false;
    }

    /** 更新相关的显示问题 */
    public void updateAccessControlSubjectTitle(Subject subject) {
        this.subj_dao.updateAccessControlSubjectTitle(subject);
    }

    public List<Integer> getGradeIdList() {
        List<Integer> int_list = new ArrayList<Integer>();
        List<Subject> ls = this.getSubjectList();
        if (ls == null || ls.size() == 0)
            return null;
        Grade g = null;
        for (Subject s : ls) {
            g = s.getMetaGrade();
            if (g != null && g.getIsGrade()) {
                Integer gradeId = g.getGradeId();
                if (!int_list.contains(gradeId)) {
                    int_list.add(gradeId);
                }
            }

        }
        return int_list;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }
}
