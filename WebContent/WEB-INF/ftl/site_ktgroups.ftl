<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}groups.css" />
    <!--[if IE 6]>
    <style type='text/css'>
    .q_t_r {float:left;margin:0 10px;padding-top:2px;}
    </style>
 <![endif]-->  
  <script src='js/jitar/core.js'></script>
  <script src='js/jitar/dtree.js'></script>
 </head>
 <body>
 <#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>
<div id='main'>
    <div class='kt_main_left'>
        <div>
            <div class='q_t_l'>
            <!-- 搜索课题组表单 -->
            <form method='get' name="profile_form">
            <table align='center'>
            <tr valign='middle'>
            <td>
             搜索关键字：<input class='s_input' name='k' value="${k!?html}" />
      搜索类型：<select name='searchtype'>
            <option value="ktname" ${("ktname" == (searchtype!""))?string('selected', '')}>课题名称</option>
            <option value="ktperson" ${("ktperson" == (searchtype!""))?string('selected', '')}>负责人</option>
          </select>    
          <select name='gradeId' onchange='grade_changed(this)'>
          <option value="">全部学段</option>
        <#if grade_list?? >
        <#list grade_list as grade >
            <option value="${grade.gradeId}" ${(grade.gradeId == (gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
        </#list>
          
        </#if>
          </select>
             <select name='subjectId'>
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
                  </td>
                  <td><input type='image' src='${SiteThemeUrl}b_s.gif' /></td>
                  </tr>
               </table>
            </form>
            </div>
        </div>
        <div style="height:8px;clear:both;"></div>
        
        <!-- 课题组列表 -->
        <#include 'mengv1/groups/main_ktgroup_list.ftl' >

    </div>
    <div class='kt_main_right'>
        <!-- 课题公告 -->
        <#include 'mengv1/groups/group_placard_list.ftl' >
        <div style="height:8px"></div>
        
        <!-- 课题成果 文章 -->
        <#include 'mengv1/groups/group_article_list.ftl' >
        <div style="height:8px"></div>  

        <!-- 课题成果 资源-->
        <#include 'mengv1/groups/group_resource_list.ftl' >
        <div style="height:8px"></div>  

        <!-- 课题成果 图片-->
        <#include 'mengv1/groups/group_photo_list.ftl' >
        <div style="height:8px"></div>  

        <!-- 课题成果 视频-->
        <#include 'mengv1/groups/group_video_list.ftl' >
        <div style="height:8px"></div>  
            
    </div>    
</div>

<div style='clear:both;'></div>

<#include ('footer.ftl') >
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