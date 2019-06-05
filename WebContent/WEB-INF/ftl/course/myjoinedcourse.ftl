<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
     <script type="text/javascript">
    function checkData(frm)
    {
        var guids = document.getElementsByName("guid")
        if(confirm("确认退出所选择的备课?")==false){
            return false;
        }
        //判断是否是主备人发起人，主备人发起人不能退出！
        
         for(i = 0;i<guids.length;i++)
         {
            if(guids[i].checked){
                pcId = guids[i].value;
                pc_createrId = document.getElementById("pc_createrId_"+pcId).value;
                pc_leaderId = document.getElementById("pc_leaderId_"+pcId).value;
                //alert("pc_createrId="+pc_createrId+"  pc_leaderId="+pc_leaderId);
                if(frm.userid.value == pc_createrId || frm.userid.value == pc_leaderId){
                    alert("在选择的备课中，你是主备人或者发起人");
                    return false;
                }
            }
         }        
         return true;   
    }

    function replay(s)
        {
    
        var hasSelected = false;
        c = document.getElementsByName("guid")
         for(i = 0;i<c.length;i++)
         {
           if(c[i].checked) hasSelected = true
         }
         
        if(hasSelected == false)
        {
         alert("请选择一个活动。")
         return;
        }
        
        
     document.forms[0].cmd.value = s;
     document.forms[0].submit();
    }

  
  function selAll(obj)
  {
     c = document.getElementsByName("guid")
     for(i = 0;i<c.length;i++)
     {
      c[i].checked = obj.checked;
     }
  }
  
  function selAll(o)
{
 var guids = document.getElementsByName("guid")
 for(i = 0;i<guids.length;i++)
 {
  guids[i].checked = o.checked;
 }
}

  </script>
 </head> 
 <body>
 <div style='padding:2px'>
 <div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='${SiteUrl}manage/course/myjoinedcourse.py'>我参与的备课</a>
</div>


<#if preparecourse_list?? >
<form method='post' onsubmit='return checkData(this);'>
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='userid' value='${userid}' />
<table class="listTable">
<thead>
<tr>
<th width='1%'><input type='checkbox' onclick='selAll(this)' /></th>
<th>备课名称</th>
<th>个案类型</th>
<th>创建日期</th>
<th>学段</th>
<th>学科</th>
<th>个案操作</th>
</tr>
</thead>
<tbody>
<#list preparecourse_list as pc>
<tr>
<td align='center'>
<input type='checkbox' name='guid' value='${pc.prepareCourseId}' />
<input type='hidden' name='pc_createrId_${pc.prepareCourseId}' id='pc_createrId_${pc.prepareCourseId}' value='${pc.createUserId!}' />
<input type='hidden' name='pc_leaderId_${pc.prepareCourseId}'  id='pc_leaderId_${pc.prepareCourseId}' value='${pc.leaderId!}' />
</td>
<td>
<a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title}</a>
<#if userid==pc.createUserId>
    <img border="0" src="${SiteUrl}images/header.gif" width="10" height="10" title="您是发起人">
</#if>
<#if pc.leaderId??>
<#if userid==pc.leaderId>
    <img border="0" src="${SiteUrl}images/upd.gif" width="10" height="10" title="您是主备人">
</#if>
</#if>
</td>
<td><#if pc.contentType==0>未设置<#elseif pc.contentType==1>HTML在线编辑<#elseif pc.contentType ==2>直接从网站打开 Word 2003 进行编辑<#elseif pc.contentType == 3>直接从网站打开 Word 2007 进行编辑<#elseif pc.contentType == 4>直接从网站打开 Word 2010 进行编辑<#elseif pc.contentType == 100>先下载文件，编辑完成后上传 Word文档<#else>未知</#if></td>
<td>${pc.createDate?string("yyyy-MM-dd HH:mm")}</td>
<#assign grad = Util.gradeById(pc.gradeId) >
<td>${grad.gradeName}</td>
<td>${Util.subjectById(pc.metaSubjectId!).msubjName!?html}</td>
<td>
<a href='${SiteUrl}p/${pc.prepareCourseId}/0/py/show_preparecourse_user_content.py?userId=${loginUser.userId}' target='_blank'>查看个案</a> | 
<a href='${SiteUrl}p/${pc.prepareCourseId}/0/py/show_preparecourse_user_edit.py' target='_blank'>修改个案</a> | 
<a href='${SiteUrl}p/${pc.prepareCourseId}/0/py/show_preparecourse_user_content_type.py' target='_blank'>修改个案内容类型</a>
</td>
</tr>
</#list>
</tbody>
</table>
<div>
<input type='submit' class='button' value='退出所选择的备课' /> <span style='color:#F00'>注意：主备人(<img border="0" src="${SiteUrl}images/upd.gif" width="10" height="10">)和发起人(<img border="0" src="${SiteUrl}images/header.gif" width="10" height="10">)不能退出。</span>
</div>
</form>
</#if>
 <div style='text-align:center'>
 <#include "../action/pager.ftl">
 </div>
</body>
</html>