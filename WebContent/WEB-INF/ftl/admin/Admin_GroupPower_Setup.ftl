<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>角色组权限管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript">
    function returnx()
    {
        self.document.location.href="?cmd=list";        
    }
    
    function Save()
    {
      var the_form = document.forms['listForm'];
      if (the_form == null) {
        alert('Can\'t find listForm form.');
        return;
      }
      the_form.submit();
    }

  </script>
  
</head>

<body>
<#assign typeTitle = '角色组权限管理'>
<h2>${typeTitle}</h2>

<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='savepower' />
  <input type='hidden' name='id' value='${id}' />
<table class='listTable' cellspacing='1'>
  <tbody>
    <tr>
      <td width="10%">角色组名称:</td>
      <td style="font-weight:bold">${groupName!}</td>
    </tr>
    <tr>  
      <td>权限设置:</td>
      <td valign="top">
        <table border=0 width="100%">
            <tr>
                <td>本组人员每天允许上载文章数量:<input type="text" name="uploadArticleNum" style="width:40px" Value="${uploadArticleNum!}">篇（-1则不限制）</td>
            </tr>
            <tr>
                <td>本组人员每天允许上载资源数量:<input type="text" name="uploadResourceNum" style="width:40px" Value="${uploadResourceNum!}">个（-1则不限制）</td>
            </tr>
            <tr>
                <td>本组人员上载空间大小限制为:<input type="text" name="uploadDiskNum" style="width:40px" Value="${uploadDiskNum!}">M</td>
            </tr>
            
            <#if ("0" != Util.isInfowarelabMeeting())>
            <tr>
                <td>本组人员是否允许创建视频会议:<input type="radio" name="videoConference" <#if videoConference??><#if videoConference == 0>checked</#if></#if> value="0"/>不允许&nbsp;&nbsp;&nbsp;<input type="radio" name="videoConference" <#if videoConference??><#if videoConference == 1>checked</#if></#if> value="1"/>允许</td>
            </tr>
            </#if>
        </table>
      </td>
    </tr>
  </tbody>
</table>

<div class='funcButton'>
    <input type='button'  class='button' value=' 保 存 ' onclick='Save();'/>
    <input type='button' class='button' value=' 返 回 ' onclick='returnx();' />
</div>
</form>
</body>
</html>
