<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />  
</head>
<body>
<h2 style='padding-top:20px;'>
${subject.subjectName}
</h2>
<table class='listTable' cellspacing='1'>
<tr>
<td style='width:80px;font-weight:bold;text-align:right;'>文章数：</td>
<td>${subject.articleCount}</td>
</tr>
<tr>
<td style='font-weight:bold;text-align:right;'>资源数：</td>
<td>${subject.resourceCount}</td>
</tr>
<tr>
<td style='font-weight:bold;text-align:right;'>群组数：</td>
<td>${subject.groupCount}</td>
</tr>
<tr>
<td style='font-weight:bold;text-align:right;'>用户数：</td>
<td>${subject.userCount}</td>
</tr>
</table>	
<br /><br />
<hr/>
<div><strong>关于管理端的一些帮助：</strong></div>
<div>
<ol style='line-height:1.6em'>
<li>学科后台管理，可管理学科范围内相关的内容。</li>
<li>学科信息管理可以设置学科网站的Logo，页头信息，页脚信息。如果设置了页头信息，则Logo不再显示，此时请将Logo放在在自定义页头里面。页脚部分一般可以版权信息等。可以进行测试查看效果。</li>
<li>模板管理可以设置页面的页面布局，在进行页面布局转换时，<b>请先将页面上的模块调整到适当的位置</b>，比如：将中部为3列的布局换成中间为2列的布局，请先将3列布局中最右侧的模块移动到左边或者中间之后，再设置为2列布局。</li>
<li>网站样式管理可以设置页面上内容显示的样式，也就是CSS（CSS是网页制作中的一种技术）。你可以自己创建样式，然后再说明设置即可。</li>
<li>网站布局管理可以设置页面上模块的位置，最多可以分为五个区域，也就是上、下、左、中、右，拖动模块到相应的区域即可。</li>
<li>自定义模块管理可以创建自己的模块内容。这样可以实现个性化。</li>
<li>系统模块管理是系统提供的默认模块，如果不需要，可以设置为隐藏。</li>
<li>学科用户管理可以将不是本学科的用户从本学科删除。</li>
<li>新闻、公告、动态可以发布学科的信息内容。</li>
<li>友情链接可以设置一些友情上的网站链接。</li>
<li>学科内容管理可以对学科页面上显示的内容进行管理。</li>
<li><strong>关于管理权限：</strong>超级管理员、本学科系统管理员可以管理全部内容。</li>
<li><strong>关于内容管理权限：</strong>整站内容管理员、本学科内容管理员可以管理网站内容</li>
</ol>
</div>
</body>
</html>