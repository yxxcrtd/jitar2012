<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>教研活动 <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />  
  <script src='js/jitar/core.js'></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div class='r_s'>
<!-- 文章搜索表单 -->
<form  method='get'>
<table align='center' border='0'>
<tr valign='top'>
<td>
 关键字：<input class='s_input' name='k' value="${k!?html}" style='width:200px;' />   
 搜索类别：<select name='filter'>
         <option value='title'<#if filter=="title"> selected="selected"</#if>>活动名称</option>
         <option value='description'<#if filter=="description"> selected="selected"</#if>>活动描述</option>
         <option value='place'<#if filter=="place"> selected="selected"</#if>>活动地点</option>
         <option value='loginName'<#if filter=="loginName"> selected="selected"</#if>>活动发起人登录名</option>
         <option value='trueName'<#if filter=="trueName"> selected="selected"</#if>>活动发起人真实姓名</option>
         </select>
   </td>
   <td><input type='image' src='${SiteThemeUrl}b_s.gif' /></td>
  </tr>
  </table>
</form>
</div>
<div style='height:8px;font-size:0'></div>
<div id='main'>
    <div class='main_left'>
        <div class='res1'>
            <div class='res1_c'>
            
              <script type="text/javascript">
              d = new dTree("d");
              d.add(0,-1,"<b>活动分类（全部活动）</b>","actions.action?type=${type!}");
	          d.add(1,0,"个人活动","actions.action?ownerType=user&type=${type!}");
	          d.add(2,0,"协作组活动","actions.action?ownerType=group&type=${type!}");
	          d.add(3,0,"集体备课活动","actions.action?ownerType=preparecourse&type=${type!}");   
	          d.add(4,0,"学科活动","actions.action?ownerType=subject&type=${type!}");   	          
              document.write(d);
              d.openAll();
            </script>
    <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
            </div>
        </div>
    </div>
    <div class='main_right'>
        <div class='tab_outer'>
        
            <div id="article_" class='tab2'>
            <#if !(type??)><#assign type="new" ></#if>
            <div class="${(type == 'new')?string('cur','') }"><a href='actions.action?type=new&ownerType=${ownerType!?url}'>正在报名的活动</a></div>
            <div class="${(type == 'running')?string('cur','') }"><a href='actions.action?type=running&ownerType=${ownerType!?url}'>正在进行的活动</a></div>
            <div class="${(type == 'finish')?string('cur','')} "><a href='actions.action?type=finish&ownerType=${ownerType!?url}'>已经结束的活动</a></div>
            </div>
    
            <div class='tab_content' style='padding:10px;'>
                <div id="article_0"  style="display: block;">
                    <table border="0" cellspacing='0' class='res_table'>
                    <thead>
                    <tr>
                    <td class='td_left' style='padding-left:10px'>活动名称</td>
                    <td class='td_middle'><nobr>发起人</nobr></td>                
                    <td class='td_middle'><nobr>报名截止日期</nobr></td>
                    <td class='td_middle'><nobr>活动类型</nobr></td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                    <td colspan='5' style='padding:4px;'></td>
                    </tr>
                <#list action_list! as a>
                     <tr>
                        <td style='padding-left:10px'>
                        <#if a.ownerType == 'course' >
                        <a target='_blank' href='${SiteUrl}p/${a.ownerId}/0/py/show_preparecourse_action_content.py?actionId=${a.actionId}'>${a.title!?html}</a>
                        <#else>
                        <a target='_blank' href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a>
                        </#if>                          
                        </td>
                        <td><nobr><a href='${SiteUrl}go.action?loginName=${a.loginName}' target='_blank'>${a.trueName!?html}</a></nobr></td>
                        <td><nobr>${a.attendLimitDateTime!?string('yyyy-MM-dd HH:mm')}</nobr></td>
                        <td><nobr>
						<#if a.ownerType == 'user' ><a target='_blank' href='${SiteUrl}go.action?userId=${a.ownerId}'>个人</a>
						<#elseif a.ownerType == 'group' ><a target='_blank' href='${SiteUrl}go.action?groupId=${a.ownerId}'>协作组</a>
						<#elseif a.ownerType == 'preparecourse' ><a target='_blank' href='${SiteUrl}go.action?courseId=${a.ownerId}'>集备</a>
						<#elseif a.ownerType == 'subject' ><a target='_blank' href='${SiteUrl}go.action?id=${a.ownerId}'>学科</a>
						<#else>未知
						</#if>
						</nobr>
						</td>
                        </tr>
                     </#list>
                    </tbody>
                  </table>
                <div class='pgr'><#include 'inc/pager.ftl' ></div>            
            </div> 
                                      
          </div>        
    </div>  
</div>

<div style='clear:both;'></div>
<#include 'footer.ftl' >
</body>
</html>