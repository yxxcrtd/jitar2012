<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" /> 
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
  <script src='js/jitar/core.js'></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div class='r_s'>
<form  method='get' name="preparecourse">
<table align='center' border='0'>
<tr valign='top'>
<td>
  关键字：<input class='s_input' name='k' value="${k!?html}" style='width:200px' />
  <input name='target' type='hidden' value='child' />
  选择学段：<select name='gradeId' onchange='grade_changed(this)'>
       <option value=''>所有学段</option>
        <#if grade_list?? >        
        <#list grade_list as grade>
        <#if grade.isGrade>
        <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected='selected'</#if>>
           <#if grade.isGrade>
              ${grade.gradeName!?html}
            <#else>
              &nbsp;&nbsp;${grade.gradeName!?html}
            </#if>
          </option>
        </#if>
        </#list>
        </#if>
       </select>
         选择学科：<select name='subjectId'>
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
          <img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
        </span>
   </td>
   <td>
  <input type='image' src='${SiteThemeUrl}b_s.gif' />
  </td>
  </tr>
  </table>
</form>
</div>
<div style='height:8px;font-size:0'></div>
<div id='main'>
  <div class='main_left' style='width:20%'>
  <div class="res1">
      <div class="res1_c">
        <script type="text/javascript">
		  d = new dTree("d");
		  d.add(0,-1,"<b>集体备课</b>","cocourses.action");
		  ${outHtml}
		  document.write(d);
		  //d.openAll();
		</script>
      </div>
    </div>
  </div>
  <div class='main_right' style='width:79%'>
  	<div class='tab_outer'>  	        
            <div class='tab2'>
            <#if !(type??)><#assign type="new" ></#if>
            <div class="${(type == 'recommend')?string('cur','')} "><a href='prepareCourse.action?type=recommend&subjectId=${subjectId!}&gradeId=${gradeId!}&target=${target!}'>推荐的集备</a></div>
            <div class="${(type == 'finished')?string('cur','')} "><a href='prepareCourse.action?type=finished&subjectId=${subjectId!}&gradeId=${gradeId!}&target=${target!}'>已经结束的集备</a></div>            
            <div class="${(type == 'running')?string('cur','') }"><a href='prepareCourse.action?type=running&subjectId=${subjectId!}&gradeId=${gradeId!}&target=${target!}'>正在进行的集备</a></div>
            <div class="${(type == 'new')?string('cur','') }"><a href='prepareCourse.action?type=new&subjectId=${subjectId!}&gradeId=${gradeId!}&target=${target!}'>计划进行的集备</a></div>           
            </div>
    
            <div class='tab_content' style='padding:10px;'>  
		        <#if course_list?? >
		        <table class='res_table' cellspacing='0'>
		        <head>
		        <tr>
		        <td class='fontbold td_left'>备课名称</td>
		        <!--
		        <td class="td_middle">发起人</td>
		        -->
		        <td class="td_middle">主备人</td>
		        <!--
		        <td class="td_middle">创建时间</td>
		        -->
		        <td class="td_middle">开始时间</td>
		        <td class="td_middle">结束时间</td>
		        <!--
		        <td class="td_middle">成员数</td>
		        <td class="td_middle">访问数</td>
		        -->
		        <td class="td_middle">个案数</td>
		        <td class="td_middle">共案编辑数</td>
		        <!--
		        <td class="td_middle">文章数</td>
		        <td class="td_middle">资源数</td>
		        -->
		        <td class="td_middle">讨论数</td>
		        <td class="td_middle">活动数</td>
		        </tr>
		        </head>    
				<#list course_list as c >
				<tr>
				<td><a href='${SiteUrl}p/${c.prepareCourseId}/0/'>${c.title}</a>
					<#if c.recommendState??>
					<#if c.recommendState>
					<img border="0" src="${SiteUrl}manage/images/ico_rcmd.gif">
					</#if>
					</#if>
				</td>
				<!--
				<td><a href='${SiteUrl}go.action?loginName=${c.loginName}'>${c.trueName}</a></td>
				-->
				<#assign u = Util.userById(c.leaderId)>
				<td><#if u??><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></#if></td>
				<!--<td>${c.createDate?string('yyyy-MM-dd')}</td>-->
				<td>${c.startDate?string('yyyy-MM-dd')}</td>
				<td>${c.endDate?string('yyyy-MM-dd')}</td>
				<!--<td>${c.memberCount}</td>-->
				<!--<td>${c.viewCount}</td>-->
				<!--<td>${c.articleCount}</td>-->
				<!--<td>${c.resourceCount}</td>-->
				<td>${privateCountList[c_index]}</td>
				<td>${editCountList[c_index]}</td>
				<td>${c.topicCount}</td>
				<td>${c.actionCount}</td>
				</tr>
				</#list>
				</table>
				</#if>
				<div class='pgr'><#include 'inc/pager.ftl' ></div>
		</div>
	</div>	
  </div>

<div style='clear:both;'></div>
<#include ('footer.ftl') >
<script>
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.preparecourse.subjectId;
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = 'manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
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