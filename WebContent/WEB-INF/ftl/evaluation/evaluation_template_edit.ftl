<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>列表</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <style>
  .outline{width:50%;}
  .outline:hover{outline:solid #196fc3 1px;}
  </style>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
<script type='text/javascript'>
function AddColumn()
{
 _container = $("container");
 _div = document.createElement("div");
 _div.style.padding = "4px"
 _txt = document.createTextNode("字段名称："); 
 _input = document.createElement("input");
 _input.name="fields";
 _input.setAttribute("maxlength","64");
 _input.className = "outline";
 _div.appendChild( _txt);
 _div.appendChild(_input);
 _container.appendChild(_div);
}

function checkTemplateName(ff)
{
 if(ff.templateName.value == "")
 {
  alert("请输入模板标题。");
  return false;
 }
 return true;
}
</script>
</head>
<body>
<h2><#if evaluationTemplate??>修改<#else>新建</#if>评课模板</h2>
<div style="padding:4px;color:#f00">说明：<br/>评课模板中，评课标题、授课人、评论人、学科/学段、评论时间这些字段已经默认存在，无需再增加，需要增加的是自己想要展示的字段，如：评课意见、改进措施等。</div>
<form method="post" action="${SiteUrl}manage/evaluation.action">
<input type="hidden" name="listtype" value="template"/>
<input type="hidden" name="cmd" value="save"/>
<#if evaluationTemplate??>
    <input type="hidden" name="evaluationTemplateId" value="${evaluationTemplate.evaluationTemplateId}"/>
<#else>
    <input type="hidden" name="evaluationTemplateId" value="0"/>
</#if>
    
<#if evaluationTemplate??>
<div style="padding:4px"><b>模板名称</b>：<input name="templateName" class="outline" style="width:80%;" maxlength="128" value="${evaluationTemplate.evaluationTemplateName?html}" /></div>
<div id="container">
<#if templateFieldList??>
<#list templateFieldList as x>
<div style="padding:4px">
字段名称：<input name="fields" maxlength="64" class="outline" value="${x.fieldsCaption?html}" />
</div>
</#list>
</#if>
</div>
<#else>
<div style="padding:4px"><b>模板名称</b>：<input name="templateName" class="outline" style="width:80%;" maxlength="128" /></div>
<div id="container"></div>
</#if>
<input type="button" value="添加评课字段" class="button" onclick="AddColumn()" />
<input type="submit" value="保存模板" class="button" onclick="return checkTemplateName(this.form)" />
<input type="button" value="返回列表" class="button" onclick="window.location.href='evaluation.action?listtype=template'" />
</form>
</body>
</html>