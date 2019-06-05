<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />  
</head>
<body>
<table class='listTable' cellspacing='1'>
<tr>
<td style='font-weight:bold;text-align:right;'>本机构用户数：</td>
<td><#if unitDayCount??>${unitDayCount.userCount}</#if></td>
<td style='font-weight:bold;text-align:right;'>下级机构用户数：</td>
<td>${unitOnlyChildUserCount!0}</td>
</tr>
<tr>
<td style='width:120px;font-weight:bold;text-align:right;'>本机构文章数：</td>
<td><#if unitDayCount??>${unitDayCount.articleCount}</#if></td>
<td style='width:120px;font-weight:bold;text-align:right;'>下级机构文章数：</td>
<td>${unitOnlyChildArticleCount!0}</td>
</tr>
<tr>
<td style='font-weight:bold;text-align:right;'>本机构资源数：</td>
<td><#if unitDayCount??>${unitDayCount.resourceCount}</#if></td>
<td style='font-weight:bold;text-align:right;'>下级机构资源数：</td>
<td>${unitOnlyChildResourceCount!0}</td>
</tr>
<tr>
<td style='font-weight:bold;text-align:right;'>本机构视频数：</td>
<td><#if unitDayCount??>${unitDayCount.videoCount}</#if></td>
<td style='font-weight:bold;text-align:right;'>下级机构视频数：</td>
<td>${unitOnlyChildVideoCount!0}</td>
</tr>
<tr>
<td style='font-weight:bold;text-align:right;'>本机构相册数：</td>
<td><#if unitDayCount??>${unitDayCount.photoCount}</#if></td>
<td style='font-weight:bold;text-align:right;'>下级机构相册数：</td>
<td>${unitOnlyChildPhotoCount!0}</td>
</tr>
</table>	
<br /><br />
<hr/>
<div><strong>关于管理端的一些帮助：</strong></div>
<div>
<ol style='line-height:1.6em'>
<li>机构后台管理，只管理本机构范围内相关的操作。</li>
<li>机构信息管理可以设置机构的名称，机构页面的Logo，页头信息，页脚信息。如果设置了页头信息，则Logo不再显示，此时请将Logo放在在自定义页头里面。页脚部分一般可以版权信息等。可以进行测试查看效果。</li>
<li>模板管理可以设置机构页面的页面布局，在进行页面布局转换时，<b>请先将页面上的模块调整到适当的位置</b>，比如：将中部为3列的布局换成中间为2列的布局，请先将3列布局中最右侧的模块移动到左边或者中间之后，再设置为2列布局。</li>
<li>网站样式管理可以设置页面上内容显示的样式，也就是CSS（CSS是网页制作中的一种技术）。你可以自己创建样式，然后再说明设置即可。</li>
<li>网站布局管理可以设置页面上模块的位置，最多可以分为五个区域，也就是上、下、左、中、右，拖动模块到相应的区域即可。</li>
<li>自定义模块管理可以创建自己的模块内容。这样可以实现个性化。</li>
<li>系统模块是系统提供的默认模块，如果不需要，可以设置为隐藏。</li>
<li>机构用户管理可以管理本机构的用户，包括删除、审核等。</li>
<li>新闻、公告、动态可以发布机构的信息内容。</li>
<li>友情链接可以设置一些友情上的网站链接。</li>
<li>文章管理、资源管理、图片管理、视频管理可以管理本机构用户发布的内容。</li>
<li>评论管理可以对显示在本机构上的用户评论进行管理。</li>
</ol>
</div>
</body>
</html>