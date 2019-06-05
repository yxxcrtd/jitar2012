<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>${resource.title!?html}</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body style="margin-top: 20px;">
  <h2>资源属性</h2>

  <h3>${resource.title!?html}</h3>

<table class='listTable' cellspacing='1'>
  <tbody>
    <tr>
      <td width='50%'>资源类型: ${resource.resourceType!'TODO'}</td>
      <td width='50%'>扩展属性: ${resource.extendAttr!'TODO'}</td>
    </tr>
    <tr>
      <td>标签: ${resource.tags!?html}</td>
      <td>所属学科: ${resource.subject!'TODO'}</td>
    </tr>
    <tr>
      <td>所属年级: ${resource.grade!'TODO'}</td>
      <td>出版社: ${resource.publisher!?html}</td>
    </tr>
    <tr>
      <td>资源作者: ${resource.author!?html}</td>
      <td>资源定价: TODO</td>
    </tr>
    <tr>
      <td>是否原创: TODO</td>
      <td>发布方式: TODO</td>
    </tr>
    <tr>
      <td>资源版本: TODO</td>
      <td>资源大小: TODO</td>
    </tr>
    <tr>
      <td>上传日期: ${resource.createDate}</td>
      <td>上传用户: TODO</td>
    </tr>
  </tbody>
</table>

<input type='button' value='         点击下载保存资源          ' />

<div style='border: 1px solid gray; margin:10px;'>
  <h3>TODO: 资源评论 + 添加评论</h3>
</div>

<div style='border: 1px solid gray; margin:10px;'>
  <h3>TODO: 相关资源</h3>
</div>

<h2>DEBUG</h2>
<li>group = ${group}

</body>
</html>
