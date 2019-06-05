package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SiteNavigationService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.SubjectNav;

public class SiteNavigationServiceImpl implements SiteNavigationService, ServletContextAware {

    private SubjectService subjectService;
    private ServletContext servlet_ctxt;


    /**
     * 将导航数据加载到servlet对象属性里面
     */

    public void renderSiteNavition() {
        ArrayList<SubjectNav> metaSubject;
        List<Grade> MetaGrade = new ArrayList<Grade>();
        List<Integer> gradeIdList = subjectService.getGradeIdList();
        metaSubject = new ArrayList<SubjectNav>();
        if (gradeIdList != null) {
            for (int i = 0; i < gradeIdList.size(); i++) {
                Grade grade = (Grade) subjectService.getGrade(gradeIdList.get(i));
                MetaGrade.add(grade);
                List<Subject> subj = subjectService.getSubjectByGradeId(grade.getGradeId());
                List<Object> ms = new ArrayList<Object>();
                if (subj != null) {
                    for (int j = 0; j < subj.size(); j++) {
                        ms.add(((Subject) subj.get(j)).getMetaSubject());
                    }
                    SubjectNav sn = new SubjectNav(grade.getGradeName(), grade.getGradeId(), ms);
                    metaSubject.add(sn);
                }
            }
        }
        
        servlet_ctxt.setAttribute("metaGrade", MetaGrade);
        servlet_ctxt.setAttribute("meta_Grade", MetaGrade);
        servlet_ctxt.setAttribute("SubjectNav", metaSubject);
    }

    public void setServletContext(ServletContext servletContext) {
        this.servlet_ctxt = servletContext;
    }

    public ServletContext getServletContext() {
        return this.servlet_ctxt;
    }
    
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
