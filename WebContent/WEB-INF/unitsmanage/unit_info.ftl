<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript'>
  //<![CDATA[  
  var doType=1;
  function openUpload(itype)
  {
    doType=itype;
    var url = "${SiteUrl}manage/userfm/index.jsp?Type=Image"
    window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
  }
  
  function SetUrl( url, width, height )
  {
    if(url)
    {
        if(doType==1){
            document.forms[0].unitlogo.value = url;
        }
        else if(doType==2){
            document.forms[0].unitPhoto.value = url;
        }
    }
  }
  //]]>
  </script>
</head>
<body>
<h2>机构信息管理</h2>
<form method='post'>
<table class='listTable' cellspacing='1'>
<tr>
<td style='width:70px'>机构名称：</td><td><input name='unitTitle' style='width:50%' value='${unit.unitTitle?html}' /><span style='color:#f00'>*</span></td>
</tr>
<tr>
<td>英文名称：</td><td><input name='unitName' style='width:50%' value='${unit.unitName?html}' /><span style='color:#f00'>*</span>（英文名称必须字母或数字且不能重复）</td>
</tr>
<tr>
<td>网站名称：</td><td><input name='siteTitle' style='width:50%' value='${unit.siteTitle?html}' /><span style='color:#f00'>*</span></td>
</tr>
<tr>
<td>机构属性：</td>
<td>
    <select name="unitType" style="width:150px">
    <#list unitTypeList as ut >
      <option value='${ut}' <#if unit??><#if ut?split("_")[1] ==unit.unitType>selected</#if></#if>>${ut?split("_")[1]!}</option>
    </#list>            
    </select>        
    <font color='red'>*</font>
</td>
</tr>
<tr>
<td>机构Logo：</td><td>
<input name='unitlogo' style='width:99%' value='${unit.unitLogo!?html}' /><br />
<input type='button' value='上传图片…' onclick='openUpload(1)' />
<input type='button' value='查看Logo' onclick='if(this.form.unitlogo.value!=""){window.open(this.form.unitlogo.value,"_blank");}' />
(Logo 标准宽度是1000像素)
</td>
</tr>

<tr>
<td>机构图片：</td><td>
<input name='unitPhoto' style='width:99%' value='${unit.unitPhoto!?html}' /><br />
<input type='button' value='上传图片…' onclick='openUpload(2)' />
<input type='button' value='查看机构图片' onclick='if(this.form.unitPhoto.value!=""){window.open(this.form.unitPhoto.value,"_blank");}' />
(机构图片标准宽度是400像素)
</td>
</tr>
<tr>
<td>机构简介：</td><td>
<textarea name='unitInfo' style='width:100%' rows = '12'>${unit.unitInfo!?html}</textarea>
<br/>页面机构简介区域的内容。
</td>
</tr>

<tr>
<td>自定页头：</td><td>
<textarea name='header' style='width:100%' rows = '12'>${unit.headerContent!?html}</textarea>
<br/>自己定义页面Logo区域的内容，支持HTML，完全自主定义，写什么内容都可以，比如邮件登陆等。如果设置此内容，页面上的Logo将不再显示，你可以将Logo设置在这里。
</td>
</tr>

<tr>
<td>自定页尾：</td><td>
<textarea name='footer' style='width:100%' rows = '12'>${unit.footerContent!?html}</textarea>
<br/>自己定义页面版权区域的内容，支持HTML，完全自主定义，写什么内容都可以，比如机构联系地址、版权声明、网上警察、网站备案等。
</td>
</tr>

<tr>
<td></td><td><input class='button' type='submit' value='保存修改' /></td>
</tr>
</table>
</form>
</body>
</html>
