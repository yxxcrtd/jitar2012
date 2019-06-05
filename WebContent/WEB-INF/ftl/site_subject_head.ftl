<div class='head1'>
	<table border='0' cellspacing='0' cellpadding='0'>
		<tr>
			<td><img src='css/subject/sub1.gif' /></td>
			<td style='background:#ffbb9a'>
				<img src='css/subject/sub3.gif' align='absmiddle' class='img1' /> <a href='index.action'>回到首页</a>
				<img src='css/subject/sub4.gif' align='absmiddle' class='img1' /> <a href='manage/'>我的工作室</a> 
				<img src='css/subject/sub5.gif' align='absmiddle' class='img1' /> <a href='logout.jsp'>注销登录</a> 
			</td>
		  <td><img src='css/subject/sub2.gif' /></td>
		</tr>
	</table>
</div>

<#-- 这种方法也可以，但数据显示是不好看的
<#list SubjectArray as sg>
<div>
 ${sg.grade.gradeName}
 <#if sg.subject?? >
 <#list sg.subject as s>
 <span>${s.subjectName}</span>
 </#list>
 </#if>
</div>
</#list>
-->
<#if SubjectNav?? >
    <#list SubjectNav as m >
    <div class='head${(m_index % 3) + 2}'>
    <strong>${m.gradeName}</strong>&nbsp;&nbsp; 
        <#list m.metaSubject as ms >
            <a href='${SiteUrl}subject/index.py?subjectId=${ms.msubjId}&gradeId=${m.gradeId}'>${ms.msubjName}</a>
            <#if ms_has_next > | </#if>
            </#list>
    </div>
    </#list>
</#if>
<#if (subject??)>
	<#if grade??>
		<div class='logo'><span class='subject_name'>${grade.gradeName!?html}${subject.metaSubject.msubjName!?html}</span></div>
	<#else>
		<div class='logo'><span class='subject_name'>${subject.metaSubject.msubjName!?html}</span></div>
	</#if>	
<#else>
	<#if grade??>
		<div class='logo'><span class='subject_name'>${grade.gradeName!?html}</span></div>
	<#else>
		<div class='logo'><span class='subject_name'></span></div>
	</#if>	
</#if>
<div class='bar'>
  <#if !(head_nav??) ><#assign head_nav = "index" ></#if>
	<table class='bar_table' border='0' cellspacing='0' cellpadding='0' align='center'>
		<tr>
		<#if subject??>
			<td class='${(head_nav == "index")?string('cur_bar', '') }'><a href='${ContextPath}go.action?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}'>学科首页</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "blogs")?string('cur_bar', '') }'><a href='subjectBlogs.py?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}'>工作室</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "groups")?string('cur_bar', '') }'><a href='subjectGroups.py?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}'>协作组</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "articles")?string('cur_bar', '') }'><a href='subjectArticles.py?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}'>文章</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "preparecourse")?string('cur_bar', '') }'><a href='subjectPrepareCourses.py?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}&target=child'>集体备课</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
		<#else>
			<td class='${(head_nav == "index")?string('cur_bar', '') }'><a href='${ContextPath}go.action?gradeId=${gradeId!}'>学科首页</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "blogs")?string('cur_bar', '') }'><a href='subjectBlogs.py?gradeId=${gradeId!}'>工作室</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "groups")?string('cur_bar', '') }'><a href='subjectGroups.py?gradeId=${gradeId!}'>协作组</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "articles")?string('cur_bar', '') }'><a href='subjectArticles.py?gradeId=${gradeId!}'>文章</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
			<td class='${(head_nav == "preparecourse")?string('cur_bar', '') }'><a href='subjectPrepareCourses.py?gradeId=${gradeId!}'>集体备课</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
		</#if>
    <#if SiteConfig ??>
      <#if subject??>
	      <#if subject.reslibCId==0>
	      
	      <#else>
	      	<td><a href='${SiteConfig.site.resourceUrl!}subject.aspx?cid=${subject.reslibCId!}'>精品资源</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
	      </#if>
      <#else>
	      <td><a href='${SiteConfig.site.resourceUrl!}subject.aspx'>精品资源</a></td><td><img src='css/subject/sub7.gif' hspace='6' /></td>
      </#if>
    </#if>
    	<#if (subject??)>
			<td class='${(head_nav == "resources")?string('cur_bar', '') }'><a href='subjectResources.py?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}'>本站资源</a></td>
		<#else>
			<td class='${(head_nav == "resources")?string('cur_bar', '') }'><a href='subjectResources.py?gradeId=${gradeId!}'>本站资源</a></td>
		</#if>	
		</tr>
	</table>
</div>