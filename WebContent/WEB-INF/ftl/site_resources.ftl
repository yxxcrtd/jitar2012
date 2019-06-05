<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}resources.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />
  <!--[if IE 6]>
  <style type='text/css'>
.main_middle{float:left;width:608px;overflow:hidden;}
  </style>
 <![endif]-->
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
  <script src='js/jitar/core.js'></script>  
 </head>
 <body>
 <#include 'site_head.ftl'>
<div class='r_s'>
<!-- 资源搜索表单 -->
<form method='get' name="profile_form">
  <input type='hidden' name='type' value="${type!?html}" />
  关键字：<input class='s_input' name='k' value="${k!?html}" /> 
 选择学段：<select name='gradeId' onchange='grade_changed(this)'>
          <option value="">全部学段</option>
        <#if grade_list?? >
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
<div style='height:8px;font-size:0;'></div>
<div id='main'>
  <div class='main_left'>
  <div class="res1">
  <div class="res1_resx">
    <script type="text/javascript">
	  d = new dTree("d");
	  d.add(0,-1,"<b>本站资源</b>","resources.action");
	  ${outHtml}
	  document.write(d);
	  //d.openAll();
	</script>
  </div>
  </div>
     
  </div>
  <div class='main_middle'>

      <div id="jitar_res_" class='tab2' style='position:relative;z-index:90;'>
        <label></label>
        <#if !(type??) ><#assign type="new" ></#if>
        <div class="${(type == 'rcmd')?string('cur','') }"><a href='resources.action?type=rcmd&amp;categoryId=${(category!).categoryId!}'>编辑推荐</a></div>
        <div class="${(type == 'new')?string('cur','') }"><a href='resources.action?type=new&amp;categoryId=${(category!).categoryId!}'>最新发布</a></div>
        <div class="${(type == 'hot')?string('cur','') }"><a href='resources.action?type=hot&amp;categoryId=${(category!).categoryId!}'>最高人气</a></div>
        <div class="${(type == 'cmt')?string('cur','') }"><a href='resources.action?type=cmt&amp;categoryId=${(category!).categoryId!}'>评论最多</a></div>
        <div id="x" class='tab2_border'></div>
      </div>      
      <div class='tab_content'>
        <div id="jitar_res_4"  style="display: none;"></div>
        <div id="jitar_res_0"  style="display: block;">       
          <table border="0" cellspacing='0' class='res_table'>
          <thead>
          <tr>
          <td class='td_left'><nobr>标题</nobr></td>
          <td class='td_middle'><nobr>学科</nobr></td>
          <td class='td_middle'><nobr>年级</nobr></td>
          <td class='td_middle'><nobr>类型</nobr></td>
          <td class='td_middle'><nobr>大小</nobr></td>
          <td class='td_middle'><nobr>上传者</nobr></td>
          <td class='td_right'><nobr>上传日期</nobr></td>
          </tr>
          </thead>
          <tbody>
          <!-- 资源列表 -->
          <#if resource_list??>
              <#list resource_list as r>
              <tr>
              <td title='${r.title!?html}'><a href='showResource.action?resourceId=${r.resourceId}'><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> ${Util.getCountedWords(r.title!?html,16)}</a></td>
              <td><nobr><#if r.subjectId??>${Util.getCountedWords(Util.subjectById(r.subjectId!).msubjName!?html,5)}</#if></nobr></td>
              <td><nobr>${Util.getCountedWords(r.gradeName!?html,3)}</nobr></td>
              <td><nobr>${Util.getCountedWords(r.scName!?html,5)}</nobr></td>
              <td align='right'><nobr>${Util.fsize(r.fsize!)}</nobr></td>
              <td title='${r.nickName!?html}'><nobr><a href='${SiteUrl}go.action?loginName=${r.loginName}' target='_blank'>${Util.getCountedWords(r.nickName,4)}</a></nobr></td>
              <td><nobr>${r.createDate?string('yyyy-MM-dd')}</nobr></td>
              </tr>
              </#list>
          </#if>
          </tbody>
          </table>
          <div class='pgr'><#include 'inc/pager.ftl' ></div>
        </div>

        
      </div>
  </div>
  
  <div class='main_right'>
    
    <div class='b1'>
        
        <div id="rank_" class='rank_tab2'>
              <label style='width:46px;'>排行榜</label>            
              <div class="cur" onmouseover="TabUtil.changeTab('rank_',0)" style='padding-left:4px;padding-right:3px;'>资源上载排行</div>
              <div class="" onmouseover="TabUtil.changeTab('rank_',1)" style='padding-left:4px;padding-right:3px;'>资源下载排行</div>
        </div>    
        
        
        <div class='rank_tab_content'>
          <div id='rank_0' style='display:block'>
           <!-- 资源上载排行 -->
          <#if upload_user_list??>
          <table border='0' cellpadding='1' cellpadding='1' width='210'>
                <#list upload_user_list as user>
                  <tr valign='top'>
                  <td class="rank_left">${user_index + 1}</td>
                  <td class="rank_right">
                    <a href="${SiteUrl}go.action?loginName=${user.loginName}" target="_blank">${user.nickName}</a>
                    </td>
                    <td align='right'>${user.resourceCount!}</td>
                  </tr>
                </#list>
            </table>
          </#if>        
        </div>
          <div id='rank_1' style='display:none'>
           <!-- 资源下载排行 -->
          <#if download_resource_list??>
          <table border='0' cellpadding='1' cellpadding='1' width='210'>
                <#list download_resource_list as r>
                  <tr valign='top'>
                  <td class="rank_left">${r_index + 1}</td>
                  <td class="rank_right">
                  <a title="${r.title!?html}" href='showResource.action?resourceId=${r.resourceId}'>${Util.getCountedWords(r.title!?html,12)}</a>
                  </td>
                  <td align='right'>${r.downloadCount!}</td>
                  </tr>
                </#list>
                 </table>
                <#else>
                 暂无排行
          </#if>
          </div>
        </div>
    </div>
  </div>  
</div>

<div style='clear:both;'></div>
<#include 'footer.ftl' >
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