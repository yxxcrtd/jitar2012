# coding=utf-8
from cn.edustar.jitar.util import ParamUtil
from cn.edustar.jitar.data import Command
from java.util import ArrayList
from subject_query import *

class SiteConfig:
    def __init__(self):
        self.cache = __jitar__.cacheProvider.getCache('main')
    def get_config(self):
        self.get_subject_list()
        
    def get_subject_list(self):
        
        #self.subjectService = __jitar__.subjectService
        # 另外一种写法，数据显示存在问题
        #cachekey_grade = "grade"        
        #GradeList = None#self.cache.get(cachekey_grade)
        #if GradeList == None:
        #    GradeList = self.subjectService.getGradeListOnlyIsGrade()
        #    self.cache.put(cachekey_grade, GradeList)
        
        #SubjectArray = ArrayList()
        #for g in GradeList:
        #    cachekey = "subject" + str(g.gradeId)
        #    SubjectOfGrade = self.subjectService.getSubjectByGradeId(g.gradeId)
        #    SubjectArray.add({"grade" : g, "subject": SubjectOfGrade})        
        #request.setAttribute("SubjectArray", SubjectArray)        
        
        cache_key1 = "metaSubject"
        cache_key2 = "metaGrade"
        metaSubject = self.cache.get(cache_key1)
        MetaGrade = self.cache.get(cache_key2)
        if metaSubject == None:
            #先得到年级
            subjectService = __spring__.getBean("subjectService")
            qry = Command(""" SELECT gradeId, gradeName FROM Grade Where isGrade = true Order By gradeId DESC""") 
            MetaGrade = qry.open()
            self.cache.put(cache_key2, MetaGrade)
            #print "meta_Grade:", MetaGrade
            metaSubject = ArrayList()
            for grade in MetaGrade:
                subj = subjectService.getSubjectByGradeId(int(grade[0]))
                m = ArrayList()
                if subj != None:
                    for sj in range(0,subj.size()):
                        m.add(subj[sj].metaSubject)
                    metaSubject.add({"gradeName" : grade[1], "gradeId" : grade[0], "metaSubject" : m })
            
            self.cache.put(cache_key1, metaSubject)

        request.setAttribute("metaGrade", MetaGrade)
        request.setAttribute("meta_Grade", MetaGrade)
        request.setAttribute("SubjectNav", metaSubject)