<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${unit.siteTitle?html}</title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/index.css" />  
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/units/${unit.themeName?default('theme1')}/dtree.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/changeurlyear.js"></script>
 </head>
<body>
<#include "/WEB-INF/unitspage/unit_total_header.ftl">

<div id='container'>
<table border='0' style='width:100%' cellpadding='0' cellspacing='0'>
<tr style='vertical-align:top;'>
<td style='width:20%;'>
  <div class='tree1'>
  <div class='tree1_c'>
	<script type="text/javascript">
	  d = new dTree("d");
	  d.add(0,-1,"<b>文章分类</b>","${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=${type!}<#if year??>&year=${year!}</#if>");
	  <#if blog_cates??>
		  <#list blog_cates.all as c>
		  <#if c.parentId??>
		  	d.add(${c.id},${c.parentId},"${c.name}","${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=${type}&categoryId=${c.id}<#if year??>&year=${year!}</#if>");
		  <#else>
		  	d.add(${c.id},0,"${c.name}","${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=${type}&categoryId=${c.id}<#if year??>&year=${year!}</#if>");
		  </#if>
		  </#list>
	  </#if>
	  document.write(d);
	  d.openAll();
	</script>
  </div>
  </div>
</td><td style='padding-left:10px'>

<div class='r_s'>
<!-- 文章搜索表单 -->
<form  method='get' name="profile_form">
<input type="hidden" name="unitId" value=${unit.unitId} /> 
<input type="hidden" name="type" value=${type!?html} />
<table align='center' border='0'>
<tr valign='top'>
<td>
  关键字：<input class='s_input' name='k' value="${k!?html}" />   
    学段：<select name='gradeId' onchange='grade_changed(this)' style="width:80px">
          <option value="">全部学段</option>
        <#if grade_list?? >
        <#list grade_list as grade >
            <option value="${grade.gradeId}" ${(grade.gradeId == (gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
        </#list>
        
        </#if>
          </select>
  学科：<select name='subjectId' style="width:80px">
            <option value=''>所有学科</option>
        <#if subject_list?? >
          <#list subject_list as subj >
            <#if subjectId?? >
            <#if subj.msubjId == subjectId >
            <option selected value='${subj.msubjId}'>${subj.msubjName}</option>
            <#else >
            <option value='${subj.msubjId}'>${subj.msubjName}</option>
            </#if>
            <#else >
            <option value='${subj.msubjId}'>${subj.msubjName}</option>
            </#if>
          </#list>
        </#if>
          </select>
            <span id='subject_loading' style='display:none'>
              <img src='${SiteUrl}images/loading.gif' align='absmiddle' 
                hsapce='3' />正在加载学科信息...
            </span>
  分类：<select name='categoryId' style="width:160px">
            <option value=''>所有分类</option>
        <#if blog_cates?? >
          <#list blog_cates.all as c >
            <#if categoryId??>
            <#if c.categoryId==categoryId>
            <option selected value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            <#else>
            <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            </#if>
            <#else>
            <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            </#if>
          </#list>
        </#if>
          </select> 
   </td>
   <td>
  <input type='image' src='${SiteThemeUrl}b_s.gif' />
  </td>
  </tr>
  </table>
</form>
</div>
<div class='tab_outer'>
    
      <div id="article_" class='tab'>
      <#if !(type??)><#assign type="new" ></#if>
      <label class='tab_label_1'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/j.gif' />&nbsp;本校文章</label>
      <div class="${(type == 'rcmd')?string('cur','') }"><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=rcmd<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>推荐文章</a></div>
      <div class="${(type == 'famous')?string('cur','') }"><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=famous<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>名师文章</a></div>
          <div class="${(type == 'new')?string('cur','')} "><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=new<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>最新发布</a></div>
          <div class="${(type == 'hot')?string('cur','') }"><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=hot<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>点击率</a></div>
          <div class="${(type == 'digg')?string('cur','') }"><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=digg<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>按顶排序</a></div>
          <div class="${(type == 'trample')?string('cur','') }"><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=trample<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>按踩排序</a></div>
          <div class="${(type == 'star')?string('cur','') }"><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=star<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>按星排序</a></div>
          <div class="${(type == 'cmt')?string('cur','') }"><a href='${UnitRootUrl}article/unit_article.py?unitId=${unit.unitId}&type=cmt<#if categoryId??>&categoryId=${categoryId!}</#if><#if year??>&year=${year!}</#if>'>评论最多</a></div>
          <div class="" style='font-size:0;'></div>
          <#if backYearList??>
          <span style="float:right;padding:1px 10px 0 0">
          <select onchange="getHistoryArticle(this.value)" style="height:17px;">
          <option value="">历史文章</option>
          <#list backYearList as y>
          <option value="${y.backYear}"<#if year??><#if year==y.backYear> selected="selected"</#if></#if>>${y.backYear}年</option>
          </#list>
          </select>
          </span>
          </#if>
      </div>
  
        <div class='tab_content' style='padding:10px;'>
          <div style="display: block;">
            <!-- 编辑推荐 -->
            <table border="0" cellspacing='0' class='content_table'>
            <thead>
            <tr>
            <td class='td_left' style='padding-left:10px'>标题</td>
            <td class='td_middle'>作者</td>
            <td class='td_middle'>发表日期</td>
            <td class='td_middle'>点击率</td>
            <td class='td_middle'>顶数</td>
            <td class='td_middle'>踩数</td>
            <#if type == 'star' >
            <td class='td_middle'>星级数</td>
            </#if>
            <td class='td_middle'>评论数</td>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='7' style='padding:4px;'></td>
            </tr>
          <#list article_list! as a>
          <#if a_index % 2 ==0 >
             <tr>
          <#else>
          	<tr class='odd_row'>
          </#if>
              <td style='padding-left:10px'>
              	<#if a.typeState == false>[原创]<#else>[转载]</#if>
                <a href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${Util.getCountedWords(a.title!?html,26)}</a>
                <#if a.rcmdPathInfo?? && a.rcmdPathInfo?index_of("/"+unit.unitId+"/") &gt;-1 ><img src='${SiteUrl}manage/images/ico_rcmd.gif' align='absbottom' /></#if>
              </td>
              <td><a href='${SiteUrl}go.action?loginName=${a.loginName}' target='_blank'>${a.userTrueName!?html}</a></td>
              <td>${a.createDate?string('yyyy-MM-dd') }</td>
              <td>${a.viewCount}</td>
              <td>${a.digg}</td>
              <td>${a.trample}</td>
              <#if type == 'star' >              
              <td><#if a.commentCount == 0>0<#else>${(a.starCount/a.commentCount)?int}</#if></td>
              </#if>
              <td>${a.commentCount}</td>
              </tr>
             </#list>
            </tbody>
            </table>
            <div class='pgr'>
			<#if pager??>
				<#include "/WEB-INF/ftl/pager.ftl">		
			</#if>
			</div>
        </div>
</td>
</tr>
</table>

</div>
<#include "/WEB-INF/unitspage/unit_footer.ftl">

<script>
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.profile_form.subjectId;
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = '${UnitRootUrl}py/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + (new Date()).valueOf();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '所有学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}

function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  opt = document.createElement("OPTION");
  opt.value = val;
  opt.text = text;
  sel.options.add(opt);
}
</script>
</body>
</html>

