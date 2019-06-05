    <#assign grpName="协作组">
    <#assign grpShowName="小组">
    <#if isKtGroup??>
        <#if isKtGroup=="1">
            <#assign grpName="课题组"> 
            <#assign grpShowName="课题">
        <#elseif isKtGroup=="2">
            <#assign grpName="备课组"> 
            <#assign grpShowName="小组">
        <#else>
            <#assign grpName="协作组">
            <#assign grpShowName="小组">
        </#if>
    </#if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>添加/修改${grpName}公告</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
     <!-- 配置上载路径 -->
    <script type="text/javascript">
        window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
        window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
    </script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>    
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <a href='groupPlacard.action?cmd=list&amp;groupId=${group.groupId}'>${grpName}公告管理</a>
  &gt;&gt; <span>${(placard.id == 0)?string('添加', '修改')}群组公告</span>
</div>

<form action='groupPlacard.action' method='post' name="theForm">
<#if __referer??>
  <input type='hidden' name='__referer' value='${__referer}' />
</#if>
  <table class='listTable' cellspacing='1' style="width:1024px">
    <tr>
      <td align="right" style='width:100px'><b>公告标题：</b></td>
      <td>
        <input type="text" name="title" id="placardtitle" size='75' value='${placard.title!?html}' />
        <font color='red'>*</font> 必须填写公告标题.
      </td>
    </tr>
</table>
  <div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;position:relative;height:560px;width:1022px">
    <div style="padding:8px;height:560px;width:92px;float:left"><b>公告内容：</b>(<font color='red'>*</font>):</div>
    <div style="position:absolute;top:0;left:107px">
      <script id="DHtml" name="content" type="text/plain" style="width:900px;height:480px;">
            ${placard.content!}
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('DHtml');
            </script>     
    </div>
</div>
  <table class='listTable' cellspacing='1' style="width:1024px">
    <tr>
      <td style='width:100px'></td>
      <td>
        <input type='hidden' name='cmd' value='save_placard' />
        <input type='hidden' name='groupId' value='${group.groupId}' />
        <input type='hidden' name='placardId' value='${placard.id}' />
        <input class="button" type="button" value="  ${(placard.id == 0)?string('添 加', '修 改')}  " onclick="saveData();"/>
        <input class="button" type="button" value=" 返 回 "
          onclick="window.history.back()" />
      </td>
    </tr>
  </table>
</form>
<script type="text/javascript">
function saveData(){
    var v=document.getElementById("placardtitle").value
    if(v=="")
    {
        alert("请输入标题");
        return;
    }
    document.theForm.submit();
}
</script>  
</body>
</html>
