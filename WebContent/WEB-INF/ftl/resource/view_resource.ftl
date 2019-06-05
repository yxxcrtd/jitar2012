<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>${resource.title!?html}</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
  <h2>资源属性</h2>
  <table class='listTable' cellspacing='1'>
    <tr>
      <td>资源标题</td>
      <td>${resource.title!?html}</td>
    </tr>
    <tr>
      <td>上传者</td>
      <td>${owner.loginName}</td>
    </tr>
    <tr>
      <td>发布方式</td>
      <td>
      <#if resource.shareMode == 1000>完全公开
      <#elseif resource.shareMode == 500>组内公开
      <#elseif resource.shareMode == 0>私有
      <#else>未知
      </#if>
      </td>
    </tr>
  </table>

<li><a href='${resource.href}'>点击下载</a></li>

<h2>DEBUG</h2>
<ul>
<li>resource = ${resource}
<li>owner = ${owner!}
</ul>

</body>
</html>
