<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>评课 <#include '/WEB-INF/ftl/webtitle.ftl'></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
  <script type="text/javascript">
    function deleteEvaluation(id)
    {
        if (confirm("确认要删除该评课吗?")==false){return false;}
        document.profile_form.act.value="delete";
        document.profile_form.actId.value=id;
        document.profile_form.submit();
    }
  </script>
 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div class='r_s'>
<!-- 评课搜索表单 -->
<form method='get' name="profile_form">
  <input type='hidden' name='type' value="${type!?html}" />
  <input type='hidden' name='act' value="list" />
  <input type='hidden' name='actId' value="" />
  课题：<input class='s_input' name='k' value="${k!?html}" /> 
 授课人：<input class='s_input' name='kperson' value="${kperson!?html}" />
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
              <img src='images/loading.gif' align='absmiddle' 
                hsapce='3' />正在加载学科信息...
            </span>
  <input type='image' src='${SiteThemeUrl}b_s.gif' align='absmiddle' />
</form>
</div>
<div style='height:8px;font-size:0;'></div>
<div id='main'>
  <div class='main_left'>
    <div class='res1'>
      <div class='res1_c'>
        <script type="text/javascript">
          d = new dTree("d");
          //function Node(id, pid, name, url, title, target, icon, iconOpen, open) 
          d.add(0,-1,"<b>全部评课</b>","evaluations.action?show=all","","_self");
          ${outHtml}
          document.write(d);
          //d.openAll();
        </script>
      </div>
    </div>
  </div>
  <div class='main_right'>
    <div class='tab_outer'>
      <div id="evaluations_" class='tab2'>
      <#if !(type??)><#assign type="doing" ></#if>
          <div class="${(type == 'finished')?string('cur','') }"><a href='evaluations.action?type=finished&categoryId=${categoryId!?default('')}&k=${k!?url}&gradeId=${gradeId!?default('')}&subjectId=${subjectId!?default('')}'>已完成的评课</a></div>
          <div class="${(type == 'doing')?string('cur','') }"><a href='evaluations.action?type=doing&categoryId=${categoryId!?default('')}&k=${k!?url}&gradeId=${gradeId!?default('')}&subjectId=${subjectId!?default('')}'>进行中的评课</a></div>
          <div class="${(type == 'mine')?string('cur','')} "><a href='evaluations.action?type=mine&categoryId=${categoryId!?default('')}&k=${k!?url}&gradeId=${gradeId!?default('')}&subjectId=${subjectId!?default('')}'>我发起的评课</a></div>
          <div class="${(type == 'done')?string('cur','') }"><a href='evaluations.action?type=done&categoryId=${categoryId!?default('')}&k=${k!?url}&gradeId=${gradeId!?default('')}&subjectId=${subjectId!?default('')}'>我参与的评课</a></div>
          <span style="float:right;padding:2px 10px 0 0">
            <input type="button" name="createBtn" value="发起评课" onClick="window.document.location.href='evaluations.action?cmd=add&evaluationPlanId=0';"/>
          </span>
      </div> 
      <div class='tab_content' style='padding:10px;'>
          <div id="article_0"  style="display: block;">
            <table border="0" cellspacing='0' class='res_table'>
            <thead>
            <tr>
            <td class='td_left' style='padding-left:10px'>课题</td>
            <td class='td_middle' nowrap='nowrap'>授课人</td>
            <td class='td_middle' nowrap='nowrap'>学科/学段</td>
            <td class='td_middle' nowrap='nowrap'>授课时间</td>
            <#if type == "mine">
                <td class='td_middle' style="width:80px">修改</td>
                <td class='td_middle' style="width:80px">删除</td>
            </#if>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='4' style='padding:4px;'></td>
            </tr>
          <#if plan_list??>
	            <#list plan_list as c>
	             <tr>
	              <td style='padding-left:10px'>
	                <a target="_blank" href='${SiteUrl}evaluations.action?cmd=content&evaluationPlanId=${c.evaluationPlanId}'>${c.evaluationCaption!?html}</a>
	              </td>
	              <td nowrap='nowrap'><a href='${SiteUrl}go.action?userId=${c.teacherId!?html}' target='_blank'>${c.teacherName!?html}</a></td>
	              <td>${c.msubjName!}/${c.gradeName!}</td>
	              <td>${c.teachDate?string("yyyy-MM-dd")}</td>
	                <#if type == "mine">
	                    <td style="width:80px"><a href="evaluation_edit.py?evaluationPlanId=${c.evaluationPlanId}">修改</a></td>
	                    <td style="width:80px"><a href="#" onClick="deleteEvaluation(${c.evaluationPlanId})">删除</a></td>
	                </#if>
	              </tr>
	             </#list>
            <#else>
            <tr style="text-align:center;font-weight:bold"><td colspan="10">没有查询到数据</td></tr>
            </#if>
            </tbody>
            </table>
            <div class='pgr'><#include "/WEB-INF/ftl/inc/pager.ftl" ></div>        
        </div>                           
      </div>
  </div>    
</div>
</div>
<div style='clear:both;'></div>
<#include '/WEB-INF/ftl/footer.ftl' >
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