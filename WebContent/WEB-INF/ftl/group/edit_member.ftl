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
<#assign unitName="">
<#if gm.teacherUnit??>
    <#if gm.teacherUnit!="">
        <#assign unitName=gm.teacherUnit>        
    <#elseif unit??>
        <#if unit.unitTitle!="">
            <#assign unitName=unit.unitTitle>
        </#if>
    </#if>
</#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理${grpName}</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}成员信息</span>
</div>
<br/>

<form name='theForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='savemember' />  
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <input type='hidden' name='gmId' value='${gm.id}' />
<table class='listTable' cellspacing='1'>
    <tbody>
        <tr>
            <td class='title' width='25%' align='right' valign='top'>
                <b>姓名：</b>
            </td>
            <td>
                ${user.trueName!?html}
            </td>
        </tr>    
        <tr>
            <td class='title' width='25%' align='right' valign='top'>
                <b>所在单位：</b>
            </td>
            <td>
                <input type="text" name="teacherUnit" value="${unitName!?html}">
            </td>
        </tr>
        <tr>
            <td class='title' width='25%' align='right' valign='top'>
                <b>专业职务：</b>
            </td>
            <td>
                <input type="text" name="teacherZYZW" value="${gm.teacherZYZW!?html}">
            </td>
        </tr> 
        <tr>
            <td class='title' width='25%' align='right' valign='top'>
                <b>学历：</b>
            </td>
            <td>
                <input type="text" name="teacherXL" value="${gm.teacherXL!?html}">
            </td>
        </tr> 
        <tr>
            <td class='title' width='25%' align='right' valign='top'>
                <b>学位：</b>
            </td>
            <td>
                <input type="text" name="teacherXW" value="${gm.teacherXW!?html}">
            </td>
        </tr> 
        <tr>
            <td class='title' width='25%' align='right' valign='top'>
                <b>研究专长：</b>
            </td>
            <td>
                <textarea style="width:500px;heitgh:100px" name="teacherYJZC">${gm.teacherYJZC!?html}
                </textarea>
            </td>
        </tr> 
     </tbody>
  </table>
    <div class='funcButton'>
    <input type='button' class='button' value=' 保  存 ' onclick='javascript:saveData();' />
    <input type='button' class='button' value=' 返 回 ' onclick='javascript:returnme();' />
    </div>
</form>

<div class='funcButton help'>

</div>
<script language='javascript'>
function saveData() {
      document.theForm.submit();
}

function returnme() {
  window.history.back(-1);
}

</script>
</body>
</html>
