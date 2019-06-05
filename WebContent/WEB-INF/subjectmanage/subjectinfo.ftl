<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript'>
  //<![CDATA[  
  function openUpload()
  {
    var url = "${SiteUrl}manage/userfm/index.jsp?Type=Image"
    window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
  }
  
  function SetUrl( url, width, height )
  {
    if(url)
    {
        document.forms[0].subjectlogo.value = url;
    }
  }
  //]]>
  </script>
</head>
<body>
<h2>学科信息管理</h2>
<form method='post'>
<table class='listTable' cellspacing='1'>
<tr>
<td>学科Logo：</td><td>
<input name='subjectlogo' style='width:99%' value='${subject.logo!?html}' /><br />
<input type='button' value='上传图片…' onclick='openUpload()' />
<input type='button' value='查看Logo' onclick='window.open(this.form.subjectlogo.value,"_blank")' /> (Logo标准宽度为1000像素)
</td>
</tr>
<tr>
<tr>
<td>自定页头：</td><td>
<textarea name='header' style='width:100%' rows = '15'>${subject.headerContent!?html}</textarea>
<br/>自己定义页面Logo区域的内容，支持HTML，完全自主定义，写什么内容都可以，比如邮件登陆等。如果设置此内容，页面上的Logo将不再显示，你可以将Logo设置在这里。
</td>
</tr>
<tr>
<td>自定页尾：</td><td>
<textarea name='footer' style='width:100%' rows = '15'>${subject.footerContent!?html}</textarea>
<br/>自己定义页面版权区域的内容，支持HTML，完全自主定义，写什么内容都可以，比如单位联系地址、版权声明、网上警察、网站备案等。
</td>
</tr>
<tr>
<td></td><td><input class='button' type='submit' value='保存修改' /></td>
</tr>
</table>
<div style='padding:10px 0;color:#f00'>说明：自定义Logo和自定页头不可同时设置，如果设置了自定页头，则可以把Logo放置在自定页头里面。如果设置了自定页头，则自定义Logo不会显示。</div>
</form>
</body>
</html>