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
	  d.add(0,-1,"<b>资源分类</b>","unit_resource.py?unitId=${unit.unitId}&type=${type!}");
	  ${outHtml}
	  document.write(d);
	  //d.openAll();
	</script>
  </div>
  </div>
</td><td style='padding-left:10px'>
<div class='r_s'>
<!-- 资源搜索表单 -->
<form method='get' name="profile_form">
<input type='hidden' name='unitId' value='${unit.unitId}' />	
  <input type='hidden' name='type' value='${type!?html}' />
  关键字：<input class='s_input' name='k' value="${k!?html}" /> 
 选择学段：<select name='gradeId' onchange='grade_changed(this)'>
          <option value="">全部学段</option>
        <#if grade_list?? >
        <#--
        <#list grade_list as grade>
        <#if grade.isGrade>
            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                <#if grade.isGrade>
                  ${grade.gradeName!?html}
                <#else>
                  &nbsp;&nbsp;${grade.gradeName!?html}
                </#if>
              </option>
        </#if>
        </#list>
        -->
        <#list grade_list as grade >
            <option value="${grade.gradeId}" ${(grade.gradeId == (gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
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
              <img src='${SiteUrl}images/loading.gif' align='absmiddle' 
                hsapce='3' />正在加载学科信息...
            </span>
  选择类型：<select name='categoryId'>
            <option value=''>所有类型</option>
        <#if res_cate?? >
          <#list res_cate.all as c >
            <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
          </#list>
        </#if>
          </select> 
  <input type='image' src='${SiteThemeUrl}b_s.gif' align='absmiddle' />
</form>
</div>
<div class='tab_outer'>
    
      <div id="article_" class='tab'>
      <#if !(type??)><#assign type="new" ></#if>
      <label class='tab_label_1'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/j.gif' />&nbsp;本校资源</label>
        <div class="${(type == 'rcmd')?string('cur','') }"><a href='unit_resource.py?unitId=${unit.unitId}&type=rcmd&amp;categoryId=${(category!).categoryId!}'>编辑推荐</a></div>
        <div class="${(type == 'new')?string('cur','') }"><a href='unit_resource.py?unitId=${unit.unitId}&type=new&amp;categoryId=${(category!).categoryId!}'>最新发布</a></div>
        <div class="${(type == 'hot')?string('cur','') }"><a href='unit_resource.py?unitId=${unit.unitId}&type=hot&amp;categoryId=${(category!).categoryId!}'>最高人气</a></div>
        <div class="${(type == 'cmt')?string('cur','') }"><a href='unit_resource.py?unitId=${unit.unitId}&type=cmt&amp;categoryId=${(category!).categoryId!}'>评论最多</a></div>
          <div class="" style='font-size:0;'></div>
      </div>
  
        <div class='tab_content' style='padding:10px;'>
          <div style="display: block;">
          <table border="0" cellspacing='0' class='content_table'>
          <thead>
          <tr>
          <td class='td_left'>标题</td>
          <td class='td_middle'>学科</td>
          <td class='td_middle'>年级</td>
          <td class='td_middle'>类型</td>
          <td class='td_middle'>大小</td>
          <td class='td_middle'>上传者</td>
          <td class='td_right'>上传日期</td>
          </tr>
          </thead>
          <tbody>
          <!-- 资源列表 -->
          <#if resource_list??>
              <#list resource_list as r>
                <tr>
              <td>
              <a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}'><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> ${r.title!?html}</a>
              <#if r.recommendState><img src='${SiteUrl}manage/images/ico_rcmd.gif' border='0' align='absmiddle' /></#if>
              </td>
              <td><#if r.subjectId??>${Util.subjectById(r.subjectId!).msubjName!?html}</#if></td>
              <td>${r.gradeName!?html}</td>
              <td>${r.scName!?html}</td>
              <td align='right'>${Util.fsize(r.fsize!)}</td>
              <td><a href='${SiteUrl}go.action?loginName=${r.loginName}' target='_blank'>${r.nickName}</a></td>
              <td>${r.createDate?string('yyyy-MM-dd')}</td>
              </tr>
              </#list>
          </#if>
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
  url = '${SiteUrl}manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
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

